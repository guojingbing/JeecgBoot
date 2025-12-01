package org.jeecg.modules.zxecg.util;

import org.apache.commons.lang3.BooleanUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */


public class ZxecgDateUtil {
    public static int getReportAge(Date reportDate, Date birthDay) {
        LocalDate localBirthDate = DateToLocaleDate(birthDay);
        LocalDate localReportDate = DateToLocaleDate(reportDate);
        Period period = Period.between(localBirthDate, localReportDate);
        int age = period.getYears();
        return age;
    }

    public static LocalDate DateToLocaleDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

}
