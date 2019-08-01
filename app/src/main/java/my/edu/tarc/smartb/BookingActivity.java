package my.edu.tarc.smartb;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

public class BookingActivity extends AppCompatActivity {

    private Intent intent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    intent = new Intent(BookingActivity.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_booking:
                    //mTextMessage.setText("Booking");
//                    intent = new Intent(BookingActivity.this,BookingActivity.class);
//                    startActivity(intent);
                    return true;
                case R.id.navigation_profile:
                    //mTextMessage.setText("Profile");
                    intent = new Intent(BookingActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        ImageButton btnBadminton = findViewById(R.id.btnBadminton);
        ImageButton btnTableTennis = findViewById(R.id.btnTableTennis);
        ImageButton btnSquash = findViewById(R.id.btnSquash);
        ImageButton btnTennis = findViewById(R.id.btnTennis);
        ImageButton btnSnooker = findViewById(R.id.btnSnooker);
        ImageButton btnVolleyball = findViewById(R.id.btnVolleyBall);
        ImageButton btnBasketball = findViewById(R.id.btnBasketBall);


        btnBadminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookingActivity.this, DateActivity.class);
                intent1.putExtra("FacID", "SBC1");
                startActivity(intent1);
            }
        });

        btnTableTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(BookingActivity.this, DateActivity.class);
                intent2.putExtra("FacID", "SBC2");
                startActivity(intent2);
            }
        });

        btnBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(BookingActivity.this, DateActivity.class);
                intent3.putExtra("FacID", "COD2");
                startActivity(intent3);
            }
        });

        btnVolleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(BookingActivity.this, DateActivity.class);
                intent4.putExtra("FacID", "COD1");
                startActivity(intent4);
            }
        });

        btnSquash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(BookingActivity.this, DateActivity.class);
                intent5.putExtra("FacID", "CSC1");
                startActivity(intent5);
            }
        });

        btnTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(BookingActivity.this, DateActivity.class);
                intent6.putExtra("FacID", "SBC3");
                startActivity(intent6);
            }
        });

        btnSnooker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(BookingActivity.this, DateActivity.class);
                intent7.putExtra("FacID", "CSC2");
                startActivity(intent7);
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
