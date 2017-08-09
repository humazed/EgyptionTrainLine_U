package humazed.github.com.egyptiontrainline_u.util;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;

public final class TextUtil {
    public static Spanned fromHtml(String html) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return Html.fromHtml(html, 0);
        } else {
            //noinspection deprecation
            return Html.fromHtml(html);
        }
    }
}
