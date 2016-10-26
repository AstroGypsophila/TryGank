package com.gypsophila.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.gypsophila.commonlib.R;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

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

    public static void downloadImage(final Context context, final String imageUrl, final String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    if (!TextUtils.isEmpty(imageUrl)) {
                        bitmap = Glide.with(context)
                                .load(imageUrl)
                                .asBitmap()
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    if (bitmap == null) {
                        Logger.t("cj_data").w("bitmap == null");
                    } else {
                        try {
                            File appDir = new File(Environment.getExternalStorageDirectory(),
                                    context.getString(R.string.app_name));
                            if (!appDir.exists()) {
                                appDir.mkdir();
                            }
                            String fileName = imgName + ".jpg";
                            File file = new File(appDir, fileName);

                            FileOutputStream fos = new FileOutputStream(file);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                            Logger.t("cj_data").w("file path = " + file.getAbsolutePath());
                            Uri uri = Uri.fromFile(file);
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
            }
        }).start();
    }
}
