package com.gypsophila.trygank.business.gank.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.gypsophila.commonlib.utils.ImageLoaderUtils;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppSwipeBackActivitiy;

import java.io.File;


/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/10/25
 */
public class GankPictureActivity extends AppSwipeBackActivitiy {

    public static final String IMG_URL = "IMG_URL";
    public static final String IMG_TITLE = "IMG_TITLE";

    private String mImgUrl;
    private String mImgTitle;
    private ImageView mImageView;
    private Toolbar mToolbar;

    private Context mContext;


    public static void openPictureActivity(Context ctx, String imgUrl, String imgTitle) {
        Intent intent = new Intent(ctx, GankPictureActivity.class);
        intent.putExtra(IMG_URL, imgUrl);
        intent.putExtra(IMG_TITLE, imgTitle);
        ctx.startActivity(intent);
    }

    private void parseIntent() {
        mImgUrl = getIntent().getStringExtra(IMG_URL);
        mImgTitle = getIntent().getStringExtra(IMG_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        mContext = this;
        parseIntent();
        initViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.picture_menu);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.picture_save) {
                    ImageLoaderUtils.downloadImage(mContext, mImgUrl, mImgTitle);
                } else if (id == R.id.picture_share) {
                    File appDir = new File(Environment.getExternalStorageDirectory(),
                            getString(R.string.app_name));
                    if (appDir.exists()) {
                        String fileName = mImgTitle + ".jpg";
                        File file = new File(appDir, fileName);
                        if (file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            sharePicture(uri);
                        } else {
                            downloadImage();
                        }
                    } else {
                        downloadImage();
                    }
                }

                return true;
            }
        });
        mImageView = (ImageView) findViewById(R.id.picture);
        ImageLoaderUtils.loadImage(this, mImageView, mImgUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picture_menu, menu);
        return true;
    }

    private void sharePicture(Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_send_to)));
    }

    private void downloadImage() {
        ImageLoaderUtils.downloadImage(mContext, mImgUrl, mImgTitle, new ImageLoaderUtils.DownloadImageListener() {
            @Override
            public void loadSuccess(Uri uri) {
                sharePicture(uri);
            }

            @Override
            public void loadFailed(Exception e) {

            }
        });
    }

}
