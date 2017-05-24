package es.iessaladillo.pedrojoya.pr209.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    private BindingAdapters() {
    }

    @BindingAdapter(value = {"imageUrl", "error", "placeholder"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable error, Drawable
            placeholder) {
        Picasso.with(imageView.getContext()).load(url).error(error).into(imageView);
        Picasso.with(imageView.getContext()).load(url).placeholder(
                placeholder).error(error).fit().noFade().centerCrop().into(imageView);
    }

}
