package com.gypsophila.trygank.business.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.gypsophila.commonlib.system.SystemConst;
import com.gypsophila.commonlib.utils.PreferenceUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;
import com.gypsophila.trygank.business.AppConstants;
import com.gypsophila.trygank.business.gank.ColorsListAdapter;
import com.gypsophila.trygank.business.gank.view.GankFragment;
import com.gypsophila.trygank.business.gank.view.GankListFragment;
import com.gypsophila.trygank.business.gank.view.SearchActivity;
import com.gypsophila.trygank.business.gank.view.TodayFragment;
import com.gypsophila.trygank.business.gank.view.UserInfoActivity;
import com.gypsophila.trygank.business.news.view.NewsFragment;
import com.gypsophila.trygank.systemevent.ChangeTheme;
import com.gypsophila.trygank.utils.ThemeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Gypsophila on 2016/8/16.
 */
public class MainActivity extends AppBaseActivity implements IMainView {

    /**
     * 再按一次状态等待时间（ms）
     */
    private static final long QUIT_AWAIT_THRESHOLD = 2 * SystemConst.SECOND;
    private long mQuitPressedTime = 0;

    private Toolbar mToolbar;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);
        mContext = this;
        initToolbar();
        initView();
        switchToGank();
    }

    private void initView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.inflateMenu(R.menu.base_toolbar_menu); //设置右上角的填充菜单
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //启用导航菜单
//        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        //取消选中后的颜色变化
        mNavigationView.setItemTextColor(null);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.menu_palette) {
                    showThemeDialog();
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                switchNavigation(item.getItemId());
                mToolbar.setTitle(item.getTitle());
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        View headerLayout = mNavigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView) headerLayout.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserInfoActivity();
                mDrawerLayout.closeDrawers();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_search) {
                    Toast.makeText(mContext, R.string.menu_search, Toast.LENGTH_SHORT).show();
                    switchToSearch();
                } else if (id == R.id.action_notification) {
                    Toast.makeText(mContext, R.string.menu_notifications, Toast.LENGTH_SHORT).show();

                } else if (id == R.id.action_item1) {
                    Toast.makeText(mContext, R.string.item_01, Toast.LENGTH_SHORT).show();

                } else if (id == R.id.action_item2) {
                    Toast.makeText(mContext, R.string.item_02, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    private void changeTheme(int position) {
        ThemeUtil.changTheme(MainActivity.this, ThemeUtil.Theme.mapValueToTheme(position));
        Resources.Theme theme = getTheme();
        TypedValue colorPrimary = new TypedValue();
        TypedValue colorPrimaryDark = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimary, colorPrimary, true);
        theme.resolveAttribute(R.attr.colorPrimaryDark, colorPrimaryDark, true);

        mToolbar.setBackgroundColor(getResources().getColor(colorPrimary.resourceId));
        EventBus.getDefault().post(new ChangeTheme(colorPrimary.resourceId));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusColor(colorPrimaryDark.resourceId);
        }
    }

    int currentTheme = 0;
    int selectedTheme = 0;

    private void showThemeDialog() {
        currentTheme = PreferenceUtils.getInteger(mContext, AppConstants.THEME_VALUE, 0);
        selectedTheme = PreferenceUtils.getInteger(mContext, AppConstants.THEME_VALUE, 0);

        Integer[] res = new Integer[]{
                R.drawable.theme_amber_circle, R.drawable.theme_blue_circle,
                R.drawable.theme_bluegrey_circle, R.drawable.theme_brown_circle,
                R.drawable.theme_cyan_circle, R.drawable.theme_grey_circle,
                R.drawable.theme_indigo_circle, R.drawable.theme_green_circle,
                R.drawable.theme_pink_circle, R.drawable.theme_purple_circle,
                R.drawable.theme_red_circle, R.drawable.theme_lime_circle};
        List<Integer> list = Arrays.asList(res);
        final ColorsListAdapter adapter = new ColorsListAdapter(this, list, currentTheme);
        GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.colors_panel_layout, null);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_change_theme_title));
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //未点
                if (selectedTheme != currentTheme) {
                    changeTheme(currentTheme);
                }
            }
        });
        builder.setView(gridView);
        builder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PreferenceUtils.putInteger(mContext, AppConstants.THEME_VALUE, selectedTheme);
                    }
                });
        builder.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeTheme(currentTheme);
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.show();

        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (selectedTheme != position) {
                            changeTheme(position);

                            selectedTheme = position;
                            adapter.setmSelectPos(selectedTheme);
                            adapter.notifyDataSetChanged();
                            ImageView iv = (ImageView) view.findViewById(R.id.image_done);
                            iv.setVisibility(View.VISIBLE);
                            Drawable drawable = iv.getDrawable();
                            if (drawable instanceof Animatable) {
                                ((Animatable) drawable).start();
                            }
                        }

                    }
                }
        );
    }

    private void setStatusColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(mContext.getResources().getColor(color));
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //设置toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.action_filter);
        item.setVisible(false);
        return true;
    }

    @Override
    public void switchToNews() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_content_container, NewsFragment.newInstance(), NewsFragment.TAG)
                .commit();
    }

    @Override
    public void switchToGank() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_content_container, GankFragment.newInstance(), GankFragment.TAG)
                .commit();
    }

    @Override
    public void switchToSearch() {
        Intent intent = new Intent(mContext, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void switchToNotification() {

    }

    @Override
    public void switchToFavorite() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_content_container,
                        GankListFragment.newInstance(GankListFragment.TYPE_FAVORITE))
                .commit();
    }

    @Override
    public void switchToToday() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_content_container, TodayFragment.newIntance(), TodayFragment.TAG)
                .commit();
    }

    private void startUserInfoActivity() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuitAwait()) {
                quit();
            } else {
                mQuitPressedTime = System.currentTimeMillis();
                String toastTip = getString(R.string.quit_toast) + getString(R.string.app_name);
                Toast.makeText(mContext, toastTip, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private boolean isQuitAwait() {
        return (System.currentTimeMillis() - mQuitPressedTime) < QUIT_AWAIT_THRESHOLD;
    }

    private void quit() {
        finish();
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.menu_gank:
                switchToGank();
                break;
            case R.id.menu_today:
                switchToToday();
                break;
            case R.id.menu_news:
                switchToNews();
                break;
            case R.id.menu_favorite:
                switchToFavorite();
                break;
        }
    }
}
