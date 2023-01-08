package keretrendszer.beadando;

import org.apache.tomcat.websocket.AuthenticationException;

import java.util.regex.Pattern;

//Validates user inputs
public class UserValidator {
    private static final String EMAIL_REGEX_PATTERN = "^[-a-z0-9~!$%^&*_=+}{'?]+(\\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    public boolean validateEmail(String value) throws AuthenticationException {
        if(value == null || value.length() == 0)
            throw new AuthenticationException("Nem adtál meg e-mail címet!");
        if(value.length() > 255)
            throw new AuthenticationException("A megadott e-mail cím túl hosszú!");
        if(!Pattern.compile(EMAIL_REGEX_PATTERN)
                .matcher(value)
                .matches())
            throw new AuthenticationException("A megadott adat nem e-mail cím!");
        return true;
    }
    public void validatePassword(String value) throws AuthenticationException {
        if(value == null || value.length() == 0)
            throw new AuthenticationException("Add meg a jelszavadat!");
        if(value.length() < 4)
            throw new AuthenticationException("A megadott jelszó túl rövid!");
        if(value.length() > 40)
            throw new AuthenticationException("A megadott jelszó túl hosszú!");
        if(Pattern.compile("[ _.;\n]")
                .matcher(value)
                .find())
            throw new AuthenticationException("A jelszó nem tartalmazhatja a következő karaktereket: ['szóköz', '_', '.', ';', 'sortörés']!");
    }
    public void validateUsername(String value) throws AuthenticationException {
        if(value == null || value.equals(""))
            throw new AuthenticationException("Kérlek, add meg a felhasználónevedet vagy az e-mail címedet!");
        //username must be between 4 and 40 characters
        if(value.length() < 4)
            throw new AuthenticationException("Túl rövid felhasználónév!");
        if(value.length() > 40)
            throw new AuthenticationException("Túl hosszú felhasználónév!");
        // Matches anything OTHER than an alphanumeric character including underscore. Equivalent to [^A-Za-z0-9_].
        if(Pattern.compile("\\W")
                .matcher(value)
                .find())
            throw new AuthenticationException("A felhasználónév nem tartalmazhat speciális karaktereket!");
    }
}