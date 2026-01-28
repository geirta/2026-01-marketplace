package util;

import ee.geir.marketplace.entity.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{12,}$");

    public static boolean validateEmail(String email){
        if (email.isBlank()){
            return false;
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password){
        if (password.isBlank()){
            return false;
        }
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }



}
