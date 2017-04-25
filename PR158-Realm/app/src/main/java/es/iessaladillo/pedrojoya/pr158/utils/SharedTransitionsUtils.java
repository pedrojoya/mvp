package es.iessaladillo.pedrojoya.pr158.utils;

import android.os.Build;
import android.view.Window;

public class SharedTransitionsUtils {

    private SharedTransitionsUtils() {
    }

    public static void requestContentTransitionsFeature(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
    }

}
