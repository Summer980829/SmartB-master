package my.edu.tarc.smartb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    private Intent intent;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "my.edu.tarc.smartb";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_booking:
                    intent = new Intent(MainActivity.this,BookingActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profile:
                    intent = new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    public void callBookingFac(View view){
        intent = new Intent(MainActivity.this, BookingActivity.class);
        startActivity(intent);
    }

    public void callBookingList(View view){
        intent = new Intent(MainActivity.this, ViewBookingList.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imgbtnFac = findViewById(R.id.imgBtnFac);
        ImageButton imgbtnBook = findViewById(R.id.imgBtnBook);



        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        String studName = mPreferences.getString("NAME_KEY","");

        TextView txtWelcome = findViewById(R.id.txtWelcome);
        txtWelcome.setText("Welcome, " + studName);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //
        navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //
        navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
    }
}
