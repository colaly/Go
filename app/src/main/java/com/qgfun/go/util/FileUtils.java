package com.qgfun.go.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    public static void savePhoto(final Context context, final Bitmap bmp, final SaveResultCallback saveResultCallback) {
        final File sdDir = getSDPath();
        if (sdDir == null) {
            Toast.makeText(context, "设备自带的存储不可用", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(() -> {
            File appDir = new File(sdDir, "out_photo");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置以当前时间格式为图片名称
            String fileName = df.format(new Date()) + ".png";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                saveResultCallback.onSavedSuccess();
            } catch (FileNotFoundException e) {
                saveResultCallback.onSavedFailed();
                e.printStackTrace();
            } catch (IOException e) {
                saveResultCallback.onSavedFailed();
                e.printStackTrace();
            }

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        }).start();
    }


    public static File getSDPath() {
        File sdDir = null;
        //判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            //获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    private static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                // 删除所有文件
                file.delete();
            } else if (file.isDirectory()) {
                // 递规的方式删除文件夹
                deleteDirWihtFile(file);
            }
        }
        dir.delete();// 删除目录本身
    }
    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }
}
