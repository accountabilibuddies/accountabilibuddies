package com.accountabilibuddies.accountabilibuddies.util;

import android.view.View;
import android.view.Window;

public class ViewUtils {

    public static void makeViewFullScreen(Window window) {

        window.getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}
