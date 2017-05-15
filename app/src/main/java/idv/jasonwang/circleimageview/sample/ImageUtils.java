package idv.jasonwang.circleimageview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * 參考文獻:
 * http://blog.csdn.net/wurensen/article/details/11787307
 *
 * Created by jason on 2017/5/15.
 */
public class ImageUtils {

    public static Bitmap compressBySize(Context context, int res, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // 不設置圖片的縮放(如果自動進行縮放，最後壓縮的大小會被裝置的解析度影響)
        // http://stackoverflow.com/questions/5128185/bitmap-width-height-different-after-loading-from-resource
        options.inScaled = false;

        // 不載入圖片，僅讀取圖片資訊(寬、高)
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res, options);

        // 取得原始圖片大小
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        Log.d("TAG", String.format("Bitmap originalWidth:%d originalHeight:%d", originalWidth, originalHeight));

        // 計算縮比例
        int scaleWidth = targetWidth > 0 ? (int) Math.ceil(originalWidth / (float) targetWidth) : 0;
        int scaleHeight = targetHeight > 0 ? (int) Math.ceil(originalHeight / (float) targetHeight) : 0;

        // 取得最大縮放比
        int maxScale = Math.max(scaleWidth, scaleHeight);

        Log.d("TAG", "maxScale : " + maxScale);

        // 設定圖片縮放比例
        options.inSampleSize = maxScale;

        // 開始載入圖片
        options.inJustDecodeBounds = false;

        // 釋放資源
        recycleBitmap(bitmap);

        // 回傳壓縮後的 Bitmap 物件
        return BitmapFactory.decodeResource(context.getResources(), res, options);
    }

    public static Bitmap compressBySize(Bitmap bitmap, int targetWidth, int targetHeight) {
        // 宣告一個空的 byte 陣列數據通道(用來存放圖片)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Bitmap to ByteArrayOutputStream (不失真)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        BitmapFactory.Options options = new BitmapFactory.Options();

        // 不載入圖片，僅讀取圖片資訊(寬、高)
        options.inJustDecodeBounds = true;

        bitmap = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.toByteArray().length, options);

        // 取得原始圖片大小
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        // 計算縮比例
        int scaleWidth = (int) Math.ceil(originalWidth / (float) targetWidth);
        int scaleHeight = (int) Math.ceil(originalHeight / (float) targetHeight);

        // 取得最大縮放比
        int maxScale = Math.max(scaleWidth, scaleHeight);

        // 設定圖片縮放比例
        options.inSampleSize = maxScale;

        // 開始載入圖片
        options.inJustDecodeBounds = false;

        // 釋放資源
        recycleBitmap(bitmap);

        // 回傳壓縮後的 Bitmap 物件
        return BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.toByteArray().length, options);
    }

    public static Bitmap compressBySize(InputStream inputStream, int targetWidth, int targetHeight) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try
        {
            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(buffer)) != -1)
                outputStream.write(buffer, 0, len);
        }
        catch (IOException e)
        {
            return null;
        }

        byte[] data = outputStream.toByteArray();

        BitmapFactory.Options options = new BitmapFactory.Options();

        // 不載入圖片，僅讀取圖片資訊(寬、高)
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // 取得原始圖片大小
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        // 計算縮比例
        int scaleWidth = (int) Math.ceil(originalWidth / (float) targetWidth);
        int scaleHeight = (int) Math.ceil(originalHeight / (float) targetHeight);

        // 取得最大縮放比
        int maxScale = Math.max(scaleWidth, scaleHeight);

        // 設定圖片縮放比例
        options.inSampleSize = maxScale;

        // 開始載入圖片
        options.inJustDecodeBounds = false;

        // 釋放資源
        recycleBitmap(bitmap);

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static Bitmap compressBySize(String filePath, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inScaled = false;
        // 不載入圖片，僅讀取圖片資訊(寬、高)
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        // 取得原始圖片大小
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        // 計算縮比例
        int scaleWidth = targetWidth > 0 ? (int) Math.ceil(originalWidth / (float) targetWidth) : 0;
        int scaleHeight = targetHeight > 0 ? (int) Math.ceil(originalHeight / (float) targetHeight) : 0;

        // 取得最大縮放比
        int maxScale = Math.max(scaleWidth, scaleHeight);

        // 設定圖片縮放比例
        options.inSampleSize = maxScale;

        // 開始載入圖片
        options.inJustDecodeBounds = false;

        // 釋放資源
        recycleBitmap(bitmap);

        // 回傳壓縮後的 Bitmap 物件
        return BitmapFactory.decodeFile(filePath, options);
    }


    private static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
            System.gc();

            bitmap = null;
        }
    }

}
