package idv.jasonwang.imageutils.sample;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import idv.jasonwang.imageutils.ImageCompressUtils;
import idv.jasonwang.imageutils.ImageShapeUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = ImageCompressUtils.compressBySize(this, R.drawable.test_large, 500, 500);

        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(ImageShapeUtils.clipToCircle(bitmap, 5f, Color.WHITE));
    }

}
