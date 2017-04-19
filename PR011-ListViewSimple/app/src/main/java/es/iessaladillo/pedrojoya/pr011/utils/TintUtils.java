package es.iessaladillo.pedrojoya.pr011.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

public class TintUtils {

    private TintUtils() {
    }

    public static void tintViewWithColorStateList(@NonNull ImageView view,
            @NonNull ColorStateList colores) {
        Drawable d = DrawableCompat.wrap(view.getDrawable());
        DrawableCompat.setTintList(d, colores);
        view.setImageDrawable(d);
    }

}
