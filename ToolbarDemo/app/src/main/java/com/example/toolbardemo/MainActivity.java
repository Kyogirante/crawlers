package com.example.toolbardemo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout left;
    private LinearLayout right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        left = (LinearLayout) findViewById(R.id.drawer_left);
        right = (LinearLayout) findViewById(R.id.drawer_right);

        toolbar.setTitle("Demo");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.abc_action_bar_home_description,R.string.app_name);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                drawerLayout.closeDrawer(right);
                if(drawerLayout.isDrawerOpen(left)){
                    drawerLayout.closeDrawer(left);
                } else {
                    drawerLayout.openDrawer(left);
                }
                return true;
            case R.id.action_demo:
                drawerLayout.closeDrawer(left);
                if(drawerLayout.isDrawerOpen(right)){
                    drawerLayout.closeDrawer(right);
                } else {
                    drawerLayout.openDrawer(right);
                }
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
