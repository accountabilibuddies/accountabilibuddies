package com.accountabilibuddies.accountabilibuddies.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtils {

    public static String IMAGE_FORMAT = ".jpg";

    /**
     * Function to compress the image as a high resolution camera
     * image size is large.
     * @param bmp
     * @return byte array of the compressed bitmap
     */
    public static byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Function to create an image file in the storage directory
     * @param storageDir
     * @return
     * @throws IOException
     */
    public static File createImageFile(File storageDir) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return File.createTempFile(
                imageFileName,
                IMAGE_FORMAT,
                storageDir);
    }

    /**
     * Function used to scale the bitmap keeping the resolution
     * @param b
     * @param width
     * @param height
     * @return
     */
    public static Bitmap scaleToFill(Bitmap b, int width, int height)
    {
        float factorH = height / (float) b.getWidth();
        float factorW = width / (float) b.getWidth();
        float factorToUse = (factorH > factorW) ? factorW : factorH;
        return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factorToUse),
                (int) (b.getHeight() * factorToUse), true);
    }

    public static boolean cameraPermissionsGranted(Context context) {

        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED;
    }

}
