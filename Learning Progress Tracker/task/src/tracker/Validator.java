package tracker;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator {

    public static boolean validEmail(String email) {
        //Jean-Claude O'Connor jcda123@google.net
        //Pattern pattern = Pattern.compile("^[a-zA-Zd_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Zd.-]+$");
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static  boolean validName(String name) {

        Pattern pattern = Pattern.compile("([a-zA-Z]+['-]?)+[a-zA-Z]+");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validPoints(String[] points) {
        if (points.length != 4) {
            return false;
        }
        boolean validDigit = Arrays.stream(points)
                .map(s -> s.matches("\\d+"))
                .reduce((s, s2) -> s && s2)
                .get();
        return validDigit;
    }
}
