package my.edu.tarc.smartb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingList extends AppCompatActivity {
    public static final String TAG = "my.edu.tarc.smartb";
    ListView listViewBooking;
    List<my.edu.tarc.smartb.BookingList> bookList;
    private ProgressDialog pDialog;

    private static String GET_URL = "https://yapsm-wa16.000webhostapp.com/SelectBooking.php";
    RequestQueue queue;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "my.edu.tarc.smartb";
    String getID;

    private Intent intent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(ViewBookingList.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_booking:
                    intent = new Intent(ViewBookingList.this,BookingActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profile:
                    intent = new Intent(ViewBookingList.this,ProfileActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_booking_list);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        getID = mPreferences.getString("ID_KEY","");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        listViewBooking = findViewById(R.id.listViewBooking);
        pDialog = new ProgressDialog(this);
        bookList = new ArrayList<>();

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

        downloadBooking(getApplicationContext(), GET_URL);


    }


    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void downloadBooking(Context context, String url) {
        // Instantiate the RequestQueue
        queue = Volley.newRequestQueue(context);

        if (!pDialog.isShowing())
            pDialog.setMessage("Sync with server...");
        pDialog.show();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            bookList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject bookingResponse = (JSONObject) response.get(i);
                                String studID = bookingResponse.getString("StudID");
                                String date = bookingResponse.getString("Date");
                                String startTime = bookingResponse.getString("StartTime");
                                String endTime = bookingResponse.getString("EndTime");
                                String courtNo = bookingResponse.getString("Court");
                                String sportType = bookingResponse.getString("Name");
                                String venue = bookingResponse.getString("Location");

                                BookingList bookingList = new BookingList(studID, venue, date, startTime, endTime, sportType, courtNo);

                                if(studID.equals(getID)) {
                                    bookList.add(bookingList);
                                }
                            }
                            loadBooking();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error" + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    }
                });

        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadBooking() {
        final BookingAdapter adapter = new BookingAdapter(this, R.layout.adapter_view_bookinglist, bookList);
        listViewBooking.setAdapter(adapter);
        if(bookList != null){
            int size = bookList.size();
            if(size > 0)
                Toast.makeText(getApplicationContext(), "No. of record : " + size + ".", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "No record found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //
        navigation.getMenu().findItem(R.id.navigation_booking).setChecked(true);
    }
}