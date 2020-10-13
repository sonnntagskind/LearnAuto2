package battlemetrics_rust.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public Date parseStringToDate(String stringTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy.MM.dd HH:mm:ss");
        return simpleDateFormat.parse(stringTime);
    }

    public Long parseStringTimeToLongTime(String stringTime) throws ParseException {
        return (parseStringToDate(stringTime)).getTime();
    }
}
