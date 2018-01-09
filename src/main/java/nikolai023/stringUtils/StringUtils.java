package nikolai023.stringUtils;

public class StringUtils {
    private StringUtils() {
    }

    public static String getLeftPaddedString(int size, String text) {
        return String.format(String.format("%%1$-%ds", size), text);
    }

    public static String getRightPaddedString(int size, String text) {
        return String.format(String.format("%%1$%ds", size), text);
    }
}
