package com.accountabilibuddies.accountabilibuddies.util;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoUtils {

    public static File createVideoFile(File storageDir) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VIDEO_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,
                ".mp4",
                storageDir
        );

        return image;
    }
}
