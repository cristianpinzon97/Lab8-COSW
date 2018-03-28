package com.eci.cosw.example.loadpicture;
import android.graphics.Bitmap;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Post implements Serializable {
    private String message;
    private byte[] selectedImage;

    public Post(String message, byte[] selectedImage) {
        this.message = message;
        this.selectedImage = selectedImage;
    }

    public String getMessage() {
        return message;
    }


    public byte[] getSelectedImage() {
        return selectedImage;
    }


}