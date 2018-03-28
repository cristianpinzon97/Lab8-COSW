package com.eci.cosw.example.loadpicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    TextView message;
    ImageView image;

    Post post;

    Bitmap bmp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_post, container, false);
        message = (TextView)view.findViewById(R.id.textViewPost);
        image = (ImageView)view.findViewById(R.id.imageViewPost);
        bmp = BitmapFactory.decodeByteArray(post.getSelectedImage(), 0, post.getSelectedImage().length);
        message.setText(post.getMessage());
        image.setImageBitmap(bmp);
        return view;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }

}
