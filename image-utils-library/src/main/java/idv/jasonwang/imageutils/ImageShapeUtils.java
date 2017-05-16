package idv.jasonwang.imageutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/**
 * 圖片剪裁工具類
 *
 *參考文獻
 *．Android 颜色渲染(九) PorterDuff及Xfermode详解
 *    http://blog.csdn.net/t12x3456/article/details/10432935
 *
 *．Android图像处理——Paint之Xfermode
 *    http://blog.csdn.net/allen315410/article/details/45077165
 *
 * Created by jason on 2017/5/16.
 */
public class ImageShapeUtils {

    /**
     *
     * @param bitmap 要剪裁的圖片來源
     * @param borderWidth 圓環的寬度，小於等於 0 表示不繪製圓環，也將忽略 borderColor 的設置
     * @param borderColor 圓環的顏色
     * @return
     */
    public static Bitmap clipToCircle(Bitmap bitmap, float borderWidth, int borderColor) {
        Bitmap outputBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        int radius = Math.min(rect.centerX(), rect.centerY());

        Canvas canvas = new Canvas(outputBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        if (borderWidth <= 0)
            return outputBitmap;

        // 設置圓環畫筆
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        // 繪製外圓環(中心點 X, 中心點 Y, 半徑, 畫筆)
        // 半徑的部分要扣除圓環寬度的一半，避免造成有一半的圓環被繪製到畫布外，再加 0.5 是避免些許誤
        // 差導致圓環外還有部分圖片沒有被覆蓋到
        canvas.drawCircle(rect.centerX(), rect.centerY(), radius - (borderWidth / 2) + 0.5f, paint);

        return outputBitmap;
    }

}
