package my.edu.tarc.smartb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView name,programme,faculty;
    private EditText email,contactNo,nric,studID;
    private Button btn_logout,btn_upload;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "my.edu.tarc.smartb";
    private static String URL_EDIT = "https://yapsm-wa16.000webhostapp.com/StudentEdit.php";
    private static String URL_UPLOAD = "https://yapsm-wa16.000webhostapp.com/upload.php";
    private Menu action;
    private Bitmap bitmap;
    CircleImageView profileview;
    String getID;

    private Intent intent;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    intent = new Intent(ProfileActivity.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_booking:
                    intent = new Intent(ProfileActivity.this,BookingActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_profile:
                    intent = new Intent(ProfileActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        getID = mPreferences.getString("ID_KEY","");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //
        navigation.getMenu().findItem(R.id.navigation_profile).setChecked(true);

        name =  findViewById(R.id.tvName);
        email =  findViewById(R.id.tvEmail);
        studID =  findViewById(R.id.tvID);
        nric =  findViewById(R.id.tvNric);
        faculty =  findViewById(R.id.tvFaculty);
        programme =  findViewById(R.id.tvCourse);
        contactNo = findViewById(R.id.tvContact);
        btn_logout = findViewById(R.id.btnSignout);
        btn_upload = findViewById(R.id.profileimage);
        profileview = findViewById(R.id.profileview);

        btn_upload.setVisibility(View.INVISIBLE);

        nric.setFocusableInTouchMode(false);
        studID.setFocusableInTouchMode(false);
        email.setFocusableInTouchMode(false);
        contactNo.setFocusableInTouchMode(false);


        // Retrieve data from shared preferences file
        String extraName = mPreferences.getString("NAME_KEY","");
        String extraEmail = mPreferences.getString("EMAIL_KEY","");
        String extraContactNo = mPreferences.getString("CONTACT_KEY","");
        String extranric = mPreferences.getString("NRIC_KEY","");
        String extraStudID = mPreferences.getString("ID_KEY","");
        String extraProgramme = mPreferences.getString("PROGRAMME_KEY","");
        String extraFaculty = mPreferences.getString("FACULTY_KEY","");
        String extraimage = mPreferences.getString("IMAGE_KEY","");

        //Set all the details in the profile page from shared preferences file
        name.setText(extraName);
        email.setText(extraEmail);
        studID.setText(extraStudID);
        nric.setText(extranric);
        faculty.setText(extraFaculty);
        programme.setText(extraProgramme);
        contactNo.setText(extraContactNo);
        if(!extraimage.equals("")) {
            Picasso.get().load(extraimage).into(profileview);
        }

        //clear all the shared preference file's contents and activity state to avoid user back to the previous screen after log out
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.clear();
                preferencesEditor.commit();
                Intent intent = new Intent(ProfileActivity.this,LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action,menu);

        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        return true;
    }

    //Menu action for edit/save
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                btn_upload.setVisibility(View.VISIBLE);
                email.setFocusableInTouchMode(true);
                contactNo.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(email,InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                SaveEditDetail();
                btn_upload.setVisibility(View.INVISIBLE);


                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                //
                email.setFocusableInTouchMode(false);
                contactNo.setFocusableInTouchMode(false);
                email.setFocusable(false);
                contactNo.setFocusable(false);


                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
    //Save the details into web server database after editing and replace the old shared preference file's contents
    private void SaveEditDetail(){
        final String email = this.email.getText().toString().trim();
        final String contactNo = this.contactNo.getText().toString().trim();
        final String studID = getID;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(ProfileActivity.this, "Success!",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                        preferencesEditor.putString("ID_KEY",studID);
                        preferencesEditor.putString("EMAIL_KEY",email);
                        preferencesEditor.putString("CONTACT_KEY",contactNo);
                        preferencesEditor.apply();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this,"JSONError" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this,"VolleyError" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("contactNo",contactNo);
                params.put("studID",studID) ;
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //method to choose image from your device
    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture(getID,getStringImage(bitmap));
        }
    }


    //upload image and update the web server database with php request
    private void UploadPicture(final String studID, final String photo){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(ProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("studID",studID);
                params.put("photo",photo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    //encode uploaded image into string
    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageByteArray, android.util.Base64.DEFAULT);

        return encodedImage;
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //
        navigation.getMenu().findItem(R.id.navigation_profile).setChecked(true);
    }
}