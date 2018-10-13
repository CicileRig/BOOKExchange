package activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcs.tpdev_project.R;
import com.google.firebase.auth.FirebaseAuth;

import classes.User;
import controllers.DataBaseManager;
import fragments.OneFragment;
import fragments.TwoFragment;

public class ProfilActivity extends AppCompatActivity {

    private TextView userTextView = null;
    private FirebaseAuth mAuth;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private DataBaseManager dataBaseManager = new DataBaseManager();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        /****************************** Display name surname of user ******************************************************/

        userTextView = findViewById(R.id.username);
        dataBaseManager.getUserById(new DataBaseManager.ResultGetter<User>() {
            @Override
            public void onResult(User user) {
                userTextView.setText(user.getName().toString()+" "+user.getSurname().toString());
            }
        });

        /****************************** Toolbar ***********************************************************************/
        //getting the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        //placing toolbar in place of actionbar
        toolbar.inflateMenu(R.menu.search_bar_menu);
        setSupportActionBar(toolbar);
        //set navigation icon in the toolbar
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu));

        /************************** Navigation drawer menu ***************************************************************/

        dl = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);

        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.Open, R.string.Close);
        t.syncState();


        nv = findViewById(R.id.nv);
        configureNavigationDrawer(nv);

        /******************************************** Buttom menu bar  ***************************************************/

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        configureBottomNavigationBar(bottomNavigationView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_bar_menu, menu);

        //getting the search view from the menu
        MenuItem searchViewItem = menu.findItem(R.id.menuSearch);

        //getting search manager from systemservice
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //getting the search view
        final SearchView searchView = (SearchView) searchViewItem.getActionView();

        //put a hint for the search input field
        searchView.setQueryHint("Trouver un livre ...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(true);

        //here we will get the search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //do the search here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return  true;
    }

    public void configureNavigationDrawer(NavigationView nv)
    {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.bibliotheque:
                        Toast.makeText(ProfilActivity.this, "Biblio",Toast.LENGTH_SHORT).show();
                    case R.id.livres:
                        Toast.makeText(ProfilActivity.this, "livres",Toast.LENGTH_SHORT).show();
                    case R.id.evenement:
                        Toast.makeText(ProfilActivity.this, "Evenement",Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }

            }
        });

    }

    public void configureBottomNavigationBar(BottomNavigationView bottomNavigationView)
    {
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
