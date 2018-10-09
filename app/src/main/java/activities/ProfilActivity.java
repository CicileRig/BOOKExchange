package activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.example.bcs.tpdev_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import classes.User;
import controllers.DataBaseManager;
import fragments.OneFragment;
import fragments.TwoFragment;

public class ProfilActivity extends AppCompatActivity {

    private TextView userTextView = null;
    private FirebaseAuth mAuth;

    private DataBaseManager dataBaseManager = new DataBaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        userTextView = findViewById(R.id.username);

        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean trouv = false;
                HashMap value = (HashMap)dataSnapshot.getValue();
                Set cles = value.keySet();
                Iterator it = cles.iterator();
                User user = new User("","","","","","","","");

                while (it.hasNext() & !trouv){
                    String key = (String)it.next();
                    Map<String, Object> postValues = (Map)value.get(key);

                    if(key.equals(firebaseUser.getUid().toString())){

                        user.setName( postValues.get("name").toString());
                        user.setSurname(postValues.get("surname").toString());
                        user.setAge(postValues.get("age").toString());
                        user.setMailAdress(postValues.get("mailAdress").toString());
                        user.setPassword(postValues.get("password").toString());
                        Log.d("TAG clé", key.toString());
                        userTextView.setText(user.getName().toString()+" "+user.getSurname().toString());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        // gestion des contenus du menu mes livres et mes events
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_recent:
                        // do this event
                        return true;
                    case R.id.item_favorite:
                        // do this event
                        return true;
                }
                return false;
            }
        });


    }

}
