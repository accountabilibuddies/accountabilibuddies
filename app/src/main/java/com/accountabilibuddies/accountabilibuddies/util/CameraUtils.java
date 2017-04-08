package com.accountabilibuddies.accountabilibuddies.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class CameraUtils {

    /**
     * Function to compress the image as a high resolution camera
     * image size is large.
     * @param bmp
     * @return byte array of the compressed bitmap
     */
    public static byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
