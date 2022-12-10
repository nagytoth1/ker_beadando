package keretrendszer.beadando;

import java.util.regex.Pattern;

public class EmailValidationHelper {
    private static final String regexPattern = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    public static boolean isEmail(String value){
        return Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }
}
