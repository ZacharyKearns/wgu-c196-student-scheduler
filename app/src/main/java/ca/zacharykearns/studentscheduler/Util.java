package ca.zacharykearns.studentscheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static boolean checkDate(String value) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null;
    }

    public static String formatAlert(int value) {
        String alert = value == 0 ? "OFF" : "ON";
        return alert;
    }

}
