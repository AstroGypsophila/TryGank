package com.gypsophila.trygank.business.main.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Toast;

import com.gypsophila.commonlib.system.SystemConst;
import com.gypsophila.commonlib.utils.PreferenceUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;
import com.gypsophila.trygank.business.gank.ColorsListAdapter;
import com.gypsophila.trygank.business.gank.view.GankFragment;
import com.gypsophila.trygank.business.gank.view.GankListFragment;
import com.gypsophila.trygank.business.gank.view.SearchActivity;
import com.gypsophila.trygank.business.gank.view.TodayFragment;
import com.gypsophila.trygank.business.gank.view.UserInfoActivity;
import com.gypsophila.trygank.business.main.presenter.IMainPresenter;
import com.gypsophila.trygank.business.main.presenter.MainPresenterImpl;
import com.gypsophila.trygank.business.news.view.NewsFragment;
import com.gypsophila.trygank.engine.AppConstants;
import com.gypsophila.trygank.systemevent.ChangeTheme;
import com.gypsophila.trygank.utils.ThemeUtil;
import com.orhanobut.logger.Logger;

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
    private IMainPresenter mMainPresenter;
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
        mMainPresenter = new MainPresenterImpl(this);
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
        mToolbar.inflateMenu(R.menu.base_toolbar_menu); //设置右上角的填充菜单
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //启用导航菜单
//        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.palette) {
                    changTheme();
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                mMainPresenter.switchNavigation(item.getItemId());
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

    private void changTheme() {
        showThemeDialog();
//        setTheme(R.style.RedTheme);
//        PreferenceUtils.putInteger(mContext, "theme_id", 1);
//        mToolbar.setBackgroundColor(getResources().getColor(R.color.common_s));
//        EventBus.getDefault().post(new ChangeTheme());
//        setStatusColor();
    }

    int currenttheme =0;
    int selectedThmem = 0;

    private void showThemeDialog() {
        currenttheme = PreferenceUtils.getInteger(mContext, "theme_id",0);
        selectedThmem = PreferenceUtils.getInteger(mContext, "theme_id",0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("更换主题");
        Integer[] res = new Integer[]{R.drawable.theme_s_circle, R.drawable.theme_red_circle, R.drawable.theme_indigo_circle,
                R.drawable.theme_s_circle, R.drawable.theme_s_circle, R.drawable.theme_s_circle,
                R.drawable.theme_s_circle, R.drawable.theme_s_circle, R.drawable.theme_s_circle,
                R.drawable.theme_s_circle, R.drawable.theme_s_circle,R.drawable.theme_s_circle};
        List<Integer> list = Arrays.asList(res);
        ColorsListAdapter adapter = new ColorsListAdapter(this, list);
//        adapter.setCheckItem(ThemeUtils.getCurrentTheme(getActivity()).getIntValue());
        GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.colors_panel_layout, null);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);
        builder.setView(gridView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PreferenceUtils.putInteger(mContext, AppConstants.THEME_VALUE, selectedThmem);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.show();
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ThemeUtil.changTheme(MainActivity.this, ThemeUtil.Theme.mapValueToTheme(position));
                        Resources.Theme theme = getTheme();
                        TypedValue colorPrimary = new TypedValue();
                        TypedValue colorPrimaryDark = new TypedValue();
                        theme.resolveAttribute(R.attr.colorPrimary, colorPrimary, true);
                        theme.resolveAttribute(R.attr.colorPrimaryDark, colorPrimaryDark, true);

                        mToolbar.setBackgroundColor(getResources().getColor(colorPrimary.resourceId));
                        EventBus.getDefault().post(new ChangeTheme(colorPrimary.resourceId));
                        setStatusColor(colorPrimaryDark.resourceId);
                        selectedThmem = position;
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
                String toastTip = getString(R.string.quit_toast);
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
}
