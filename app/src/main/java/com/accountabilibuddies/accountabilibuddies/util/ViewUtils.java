package com.accountabilibuddies.accountabilibuddies.util;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

public class ViewUtils {

    public static void makeViewFullScreen(Window window) {

        window.getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    public static void startIntroAnimation(Toolbar toolbar) {

        int actionbarSize = GenericUtils.dpToPx(50);
        toolbar.setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(Constants.ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
    }
}
