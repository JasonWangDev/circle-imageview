package idv.jasonwang.circleimageview.sample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = ImageCompressUtils.compressBySize(this, R.drawable.test, 1000, 1000);

        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(getCroppedBitmap(bitmap));
    }


    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Log.d("TAG", String.format("Bitmap Width:%d Height:%d", bitmap.getWidth(), bitmap.getHeight()));

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        int centX = bitmap.getWidth() / 2;
        int centY = bitmap.getHeight() / 2;

        int radio = Math.min(centX, centY);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(centX, centY, radio, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(5f);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        canvas.drawCircle(centX, centY, radio - 2.5f, paint);

        return output;
    }

}
