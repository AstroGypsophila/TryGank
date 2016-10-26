package com.gypsophila.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.gypsophila.commonlib.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description :
 * Author : AstroGypsophila
 * Github  : https://github.com/AstroGypsophila
 * Date   : 2016/9/8
 */
public class ImageLoaderUtils {

    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        if (imageView != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }

    /**
     * download image
     *
     * @param context
     * @param imageUrl
     * @param imgName
     */
    public static void downloadImage(final Context context, final String imageUrl, final String imgName) {
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    File appDir = new File(Environment.getExternalStorageDirectory(),
                            context.getString(R.string.app_name));
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    String fileName = imgName + ".jpg";
                    File file = new File(appDir, fileName);
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        Uri uri = Uri.fromFile(file);
                        //通知图库更新
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(intent);
                        Toast.makeText(context, (context.getString(R.string.download_picture_success)
                                + file.getAbsolutePath()), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(context, context.getString(R.string.download_picture_fail),
                        Toast.LENGTH_SHORT).show();
            }
        };
        Glide.with(context).load(imageUrl).asBitmap().into(target);
    }

    public interface DownloadImageListener {
        void loadSuccess(Uri uri);

        void loadFailed(Exception e);
    }
    public static void downloadImage(final Context context, final String imageUrl, final String imgName, final DownloadImageListener listener) {
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    File appDir = new File(Environment.getExternalStorageDirectory(),
                            context.getString(R.string.app_name));
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    String fileName = imgName + ".jpg";
                    File file = new File(appDir, fileName);
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        Uri uri = Uri.fromFile(file);
                        listener.loadSuccess(uri);
                        //通知图库更新
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.loadFailed(e);
                Toast.makeText(context, context.getString(R.string.download_picture_fail),
                        Toast.LENGTH_SHORT).show();
            }
        };
        Glide.with(context).load(imageUrl).asBitmap().into(target);
    }


}
