package aline.a2101036808.helper;

import android.text.TextUtils;

/**
 * Created by aline on 2017-11-07.
 */

public class ValidateHelper {
    public static boolean isValidInteger(String entry){
        try{
            Integer.parseInt(entry);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static boolean isValidDouble(String entry){
        try{
            Double.parseDouble(entry);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
