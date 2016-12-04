package com.gypsophila.trygank.business.gank.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppSwipeBackActivity;
import com.gypsophila.trygank.business.AppConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Description :
 * Author : AstroGypsophila
 * GitHub  : https://github.com/AstroGypsophila
 * Date   : 2016/10/28
 */
public class UserInfoActivity extends AppSwipeBackActivity {

    /**
     * 头像大小
     */
    public static final int HEADER_HEIGHT = 144;
    public static final int HEADER_WIDTH = 144;
    private final int PHOTO_REQUEST_GALLERY = 10;
    private final int PHOTO_REQUEST_CAREMA = 12;
    private final int PHOTO_REQUEST_CROP = 11;
    private ImageView blurImg;
    private CircleImageView avatarImg;
    private Context mContext;
    private String headerImgPath;
    private Toolbar mToolbar;
    private SwipeBackLayout mSwipeBackLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_user_info);
        mContext = this;
        initToolbar();
        initViews();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initViews() {
//        mSwipeBackLayout = getSwipeBackLayout();
//        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
//        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(getString(R.string.user_nick));
        blurImg = (ImageView) findViewById(R.id.blur_img_bg);
        avatarImg = (CircleImageView) findViewById(R.id.user_avatar);
        Glide.with(mContext)
                .load(R.mipmap.deal)
                .bitmapTransform(new BlurTransformation(mContext, 5))
                .into(blurImg);
        avatarImg.setImageResource(R.mipmap.deal);

        avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserInfoActivity.this);
                View view = View.inflate(mContext, R.layout.user_header_item, null);
                dialog.setContentView(view);
                RelativeLayout cameraLayout = (RelativeLayout) view.findViewById(R.id.user_header_camaralayout);
                RelativeLayout galleryLayout = (RelativeLayout) view.findViewById(R.id.user_header_gallerylayout);
                TextView title = (TextView) view.findViewById(R.id.dialog_title);
                title.setText(getString(R.string.change_user_avatar));
                cameraLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        takePhoto();
                    }
                });
                galleryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        takePic();
                    }
                });
                dialog.show();
            }
        });

    }

    //选择相册照片
    private void takePic() {
        headerImgPath = AppConstants.BASE_DIR + "/temp.jpg";
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    //照相
    private void takePhoto() {
        headerImgPath = AppConstants.BASE_DIR + "/temp.jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(headerImgPath)));
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    private void cropPhoto(Uri uri, File file) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null && data.getData() != null) {
                cropPhoto(data.getData(), null);
            }

        } else if (requestCode == PHOTO_REQUEST_CROP) {
            if (data != null) {
                setPicToView(data);
                //保存在本地
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            if (!TextUtils.isEmpty(headerImgPath)) {
                File temp = new File(headerImgPath);
                if (temp.exists()) {
                    cropPhoto(Uri.fromFile(temp), temp);
                }
            }
        }
    }

    private Bitmap scaleBitmap;

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            if (bitmap != null) {
                scaleBitmap = Bitmap.createScaledBitmap(bitmap, HEADER_WIDTH, HEADER_HEIGHT, true);
                saveBitmap(scaleBitmap, headerImgPath, null);
                avatarImg.setImageBitmap(scaleBitmap);
            }
        }
    }

    private boolean saveBitmap(Bitmap bitmap, String absFile, Bitmap.CompressFormat format) {
        boolean result = false;
        if (!TextUtils.isEmpty(absFile)) {
            result = save(bitmap, new File(absFile), format);
        }
        return result;
    }

    private boolean save(Bitmap bitmap, File file, Bitmap.CompressFormat format) {
        boolean result = false;
        if (bitmap != null && !bitmap.isRecycled() && file != null) {
            BufferedOutputStream bos = null;
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdir();
                    file.createNewFile();
                }
                bos = new BufferedOutputStream(new FileOutputStream(file));
                result = bitmap.compress(format == null ? Bitmap.CompressFormat.JPEG : format, 100, bos);
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return result;
    }
}
