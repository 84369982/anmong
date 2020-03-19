package com.anmong.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class Jdk8TimeUtils {
    public Jdk8TimeUtils() {
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        } else {
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        } else {
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime.toLocalDate();
        }
    }

    public static LocalTime toLocalTime(Date date) {
        if (date == null) {
            return null;
        } else {
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime.toLocalTime();
        }
    }

    public static Date toUtilDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        } else {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return Date.from(instant);
        }
    }

    public static Date toUtilDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        } else {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
            return Date.from(instant);
        }
    }

    public static Date toUtilDate(LocalTime localTime) {
        if (localTime == null) {
            return null;
        } else {
            LocalDate localDate = LocalDate.now();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return Date.from(instant);
        }
    }

}
