package me.shouheng.utils.data;

import java.util.regex.Pattern;

import me.shouheng.utils.constant.RegexConstants;

/**
 * @author WngShhng 2019-05-08 21:30
 */
public final class RegexUtils {

    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
    }

    public static boolean isEmail(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
