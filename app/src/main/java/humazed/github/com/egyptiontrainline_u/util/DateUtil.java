package humazed.github.com.egyptiontrainline_u.util;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public final class DateUtil {
    public static String prettyString(Duration duration) {
        return duration.toHours() + "hrs " + duration.getSeconds() % 3600L / 60L + "mins";
    }


    private static DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static LocalDateTime fromTimestamp(String value) {
        return LocalDateTime.parse(value, pattern);
    }

    public static String dateToTimestamp(LocalDateTime date) {
        return date != null ? date.format(pattern) : null;
    }
}
