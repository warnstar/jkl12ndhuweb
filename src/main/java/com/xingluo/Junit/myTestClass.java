package com.xingluo.Junit;

import com.xingluo.util.alidayuText;
import org.jeecgframework.core.util.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by fy on 2016/1/20.
 */
public class myTestClass {
    @Test
    public void Test1(){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println(DateUtils.getDate(cal.getTimeInMillis()+18*3600*1000+24*3600*1000*1));
    }

}
