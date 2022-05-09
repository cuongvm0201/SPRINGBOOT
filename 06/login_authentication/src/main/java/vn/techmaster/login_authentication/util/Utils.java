package vn.techmaster.login_authentication.util;
import org.apache.commons.lang3.RandomStringUtils;
public class Utils {
    public static String generatePassword(int count){
        return RandomStringUtils.random(count,false,true);
    }
}
