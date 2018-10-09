package controllers;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import activities.ProfilActivity;
import classes.User;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DataBaseManager {

    private FirebaseAuth mAuth;


    public DataBaseManager(){
    }

    public void writeNewUser(User user) {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userFirebase = mAuth.getCurrentUser();

        String  userId =  userFirebase.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        String key = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(userId).setValue(user);

        Map<String, Object> postValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + userId, postValues);
        mDatabase.updateChildren(childUpdates);

    }

    public User getUserById()
    {
        final ArrayList<User> listIUsers = new ArrayList<User>();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        final User user = new User("","","","","","","","");
        myRef.addValueEventListener(new ValueEventListener() {
            public static final String TAG = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean trouv = false;
                HashMap value = (HashMap)dataSnapshot.getValue();
                Set cles = value.keySet();
                Iterator it = cles.iterator();

                while (it.hasNext() & !trouv){
                    String key = (String)it.next();
                    Map<String, Object> postValues = (Map)value.get(key);

                    if(key.equals(firebaseUser.getUid().toString())){

                        user.setName( postValues.get("name").toString());
                        user.setSurname(postValues.get("surname").toString());
                        user.setAge(postValues.get("age").toString());
                        user.setMailAdress(postValues.get("mailAdress").toString());
                        user.setPassword(postValues.get("password").toString());
                        listIUsers.add(user);
                        Log.d("TAG clé", key.toString());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return user;
    }
}
