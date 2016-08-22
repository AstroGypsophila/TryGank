package com.gypsophila.trygank.activity.toolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;

/**
 * Created by Gypsophila on 2016/8/16.
 */
public class MainActivity extends AppBaseActivity {

    private Toolbar mToolbar;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initToolbar();
        initView();
    }

    private void initView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle("Title");
        mToolbar.setSubtitle("SubTitle");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //启用导航菜单
//        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_search) {
                    Toast.makeText(mContext, R.string.menu_search, Toast.LENGTH_SHORT).show();
                } else if (id == R.id.action_notification) {
                    Toast.makeText(mContext , R.string.menu_notifications , Toast.LENGTH_SHORT).show();

                } else if (id == R.id.action_item1) {
                    Toast.makeText(mContext, R.string.item_01 , Toast.LENGTH_SHORT).show();

                } else if (id == R.id.action_item2) {
                    Toast.makeText(mContext , R.string.item_02 , Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu,menu);
        return true;
    }
}
