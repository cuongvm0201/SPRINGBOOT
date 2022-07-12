package vn.techmaster.finalproject.ulties;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
@Service
public class RandomUtils {
    public static String generatePassword(int count){
        return RandomStringUtils.random(count,true,true);
    }
}
