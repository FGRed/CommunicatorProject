package com.example.communicatorproject.util;

import java.util.Date;

public class TimeUtil {


    public static Long SECOND = 100L;
    public static Long MINUTE = 1000L;
    public static Long HOUR = 3600000L;
    public static Long DAY = HOUR * 24;
    public static Long WEEK = DAY * 7;
    public static Long MONTH = WEEK * 4;
    public static Long YEAR = MONTH * 12;
    public static Long DECADE = YEAR * 10;

    public static String calculateTimeAgoByTimeGranularity(Date pastTime) {
        TimeGranularity granularity;
        long timeDifferenceInMillis = new Date().getTime() - pastTime.getTime();
        if(timeDifferenceInMillis < MINUTE){
            granularity = TimeGranularity.SECONDS;
        } else if(timeDifferenceInMillis < HOUR){
            granularity = TimeGranularity.MINUTES;
        }else if(timeDifferenceInMillis < DAY){
            granularity = TimeGranularity.HOURS;
        }else if(timeDifferenceInMillis < WEEK){
            granularity = TimeGranularity.DAYS;
        }else if(timeDifferenceInMillis < MONTH) {
            granularity = TimeGranularity.WEEKS;
        }else if(timeDifferenceInMillis < YEAR){
            granularity = TimeGranularity.MONTHS;
        }else if(timeDifferenceInMillis < DECADE){
            granularity = TimeGranularity.YEARS;
        }else {
            granularity = TimeGranularity.DECADES;
        }
        return timeDifferenceInMillis / granularity.toMillis() + " " +
                granularity.name().toLowerCase() + " ago";
    }

}
