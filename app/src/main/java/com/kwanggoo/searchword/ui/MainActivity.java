package com.kwanggoo.searchword.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.kwanggoo.searchword.R;
import com.kwanggoo.searchword.UserInfo;
import com.kwanggoo.searchword.bus.BusProvider;
import com.kwanggoo.searchword.bus.event.GetUserInfo;
import com.kwanggoo.searchword.bus.event.LoadUserInfo;
import com.kwanggoo.searchword.util.UserManager;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private HashMap<Integer, SearchWordFragment> mFragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentMap = new HashMap<>();
        mFragmentMap.put(MainFragment.SECTION_NUMBER, MainFragment.newInstance());
        mFragmentMap.put(KnownWordsFragment.SECTION_NUMBER, KnownWordsFragment.newInstance());

        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Subscribe
    public void onLoadUserInfo(LoadUserInfo loadUserInfo){
        Log.i(TAG, "onLoadUserInfo >> ");
        UserInfo userInfo = UserInfo.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getBus().register(this);
        String email = UserManager.getInstance(this).getUserEmail();
        BusProvider.getBus().post(new GetUserInfo(email));
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getBus().unregister(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if(position == 2){
            UserManager.getInstance(this).logout();
            startSign();
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchWordFragment fragment = mFragmentMap.get(position);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void startSign() {
        Intent intent = new Intent(this, SignActivity.class);
        Toast.makeText(this, "로그아웃 완료.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case MainFragment.SECTION_NUMBER:
                mTitle = getString(R.string.title_section1);
                break;
            case KnownWordsFragment.SECTION_NUMBER:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
