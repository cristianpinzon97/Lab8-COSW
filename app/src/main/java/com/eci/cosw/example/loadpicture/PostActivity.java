package com.eci.cosw.example.loadpicture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity {

    TextView message;
    ImageView image;

    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        message = (TextView)findViewById(R.id.textViewPost);
        image = (ImageView)findViewById(R.id.imageViewPost);

        Post post = (Post) getIntent().getSerializableExtra("Editing");
        bmp = BitmapFactory.decodeByteArray(post.getSelectedImage(), 0, post.getSelectedImage().length);
        message.setText(post.getMessage());
        image.setImageBitmap(bmp);

    }
}
