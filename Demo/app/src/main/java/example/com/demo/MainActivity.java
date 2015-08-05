package example.com.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.com.demo.Adapter.TabFragmentAdapter;
import example.com.demo.Fragment.TabFragment;
import example.com.demo.Utils.AppConfig;
import example.com.demo.View.PopView;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btn;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> tabListTitle = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private ImageView tabImage;

    private PopView popView;

    private String[] strings = new String[]{"a","b","c","d","e","f","g","h","i","j","a",
            "b","c","d","e","f","g","h","i","j","a","b","c","d","e","f","g","h","i","j",
            "a","b","c","d","e","f","g","h","i","j"};

    private ArrayList<String> data = new ArrayList<>(Arrays.asList(strings));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
    }

    private void getViews() {
        btn = (FloatingActionButton) findViewById(R.id.btn);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabImage = (ImageView) findViewById(R.id.tab_image);
        popView = (PopView) findViewById(R.id.pop_view);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        popView.setTabDataChangeListener(new PopView.TabDataChangeListener() {
            @Override
            public void initTab() {
                initViews();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();


        if(mFragments.size() == 0){
            initViews();
        }
    }

    private void initViews(){
        clearData();

        tabListTitle = AppConfig.getTabData();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(String str:tabListTitle){
            tabLayout.addTab(tabLayout.newTab().setText(str));

            Fragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("content", data);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(),
                mFragments,
                tabListTitle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        " TabLayout:" + tabLayout.getHeight() +
                        " Toolbar:" + toolbar.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });

        tabImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopView();
            }
        });
    }

    private void clearData(){
        tabListTitle.clear();
        mFragments.clear();
    }

    private void showPopView(){
        popView.startShowAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
