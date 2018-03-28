package com.eci.cosw.example.loadpicture;

import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment {

    Button addPhoto, save;
    EditText input;
    ImageView image;

    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_TAKE_IMG = 0;

    CharSequence selected;
    final CharSequence[] items = { "Take a picture", "Internal Storage"};

    Boolean ImageSelected =false;

    byte[] byteArray;

    Uri selectedImage;
    Bitmap selectedImg;

    String imgPath;


    String imgDecodableString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_new_post, container, false);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        alertDialog.setTitle("Select Photo");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                // will toast your selection
                showToast("" + items[item]);
                dialog.dismiss();
                selected = items[item];

            }
        }).show();


        addPhoto = (Button) view.findViewById(R.id.addPhoto);
        input = (EditText) view.findViewById(R.id.editText1);
        save = (Button) view.findViewById(R.id.savePhoto) ;
        image = (ImageView) view.findViewById(R.id.imageView);


        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhoto(v);
            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonSaveClicked(v);
            }

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                selectedImage = data.getData();
                getBytes(getActivity().getContentResolver().openInputStream(selectedImage));
                selectedImg = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                image.setImageBitmap(selectedImg);
                ImageSelected=true;
            } else if (requestCode == RESULT_TAKE_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from camera
                selectedImage = data.getData();
                selectedImg = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                selectedImg.compress(Bitmap.CompressFormat.JPEG, 100 , bytes);


                byteArray = bytes.toByteArray();


                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                File destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                image.setImageBitmap(selectedImg);
                ImageSelected=true;
            } else {
                Toast.makeText(getActivity(), "You don't take or haven't Image", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            showToast("Something went wrong");
        }
    }

    public void SelectPhoto(View v) {
        if (selected.equals("Internal Storage")){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

        }if(selected.equals("Take a picture")){
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture,RESULT_TAKE_IMG);
        }
    }

    private void ButtonSaveClicked(View v) {
        if (!ImageSelected || ( input.getText().toString().trim().equals("")) ) {
            showToast("Please enter either a message or select an image");
        }else{
            if (input.getText().length()<50){
                input.setError("Length longer than 50 characters.");
            }else{

                Post post = new Post(input.getText().toString(),byteArray);

                PostFragment postfragment= new PostFragment();
                postfragment.setPost(post);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                String tag =postfragment.getClass().getSimpleName();
                fragmentTransaction.replace(R.id.fragment_container, postfragment,tag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        }
    }

    private void showToast(String fromwhere) {
        Toast toast1 =
                Toast.makeText(getActivity(),
                        fromwhere, Toast.LENGTH_SHORT);

        toast1.show();
    }

    public void getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            byteArray = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ /* do nothing */ }
        }
    }

}
