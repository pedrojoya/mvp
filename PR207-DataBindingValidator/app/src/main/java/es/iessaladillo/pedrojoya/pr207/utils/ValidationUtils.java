package es.iessaladillo.pedrojoya.pr207.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isValidPhoneNumber(String cadena) {
        return cadena.length() == 9 && (cadena.startsWith("6") || cadena.startsWith("7")
                || cadena.startsWith("8") || cadena.startsWith("9"));
    }

    public static boolean isValidEmail(String cadena) {
        return !TextUtils.isEmpty(cadena) && !Patterns.EMAIL_ADDRESS.matcher(cadena).matches();
    }

}
