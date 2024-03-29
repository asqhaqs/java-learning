package com.brianway.learning.java.base.constants;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2022-07-05
 */
public interface Numeral {

    int ONE = 1;

    int TWO = 2;

    int THREE = 3;

    int FOUR = 4;

    int FIVE = 5;

    int SIX = 6;

    int SEVEN = 7;

    int EIGHT = 8;

    int NINE = 9;

    int TEN = 10;

    int ELEVEN = 11;

    int TWELVE = 12;

    int THIRTEEN = 13;

    int FOURTEEN = 14;

    int FIFTEEN = 15;

    int SIXTEEN = 16;

    int SEVENTEEN = 17;

    int EIGHTEEN = 18;

    int NINETEEN = 19;

    int TWENTY = 20;

    int THIRTY = 30;

    int N32 = 32;

    int FIFTY = 50;

    int SIXTY = 60;

    int HUNDRED = 100;

    int FIVE_HUNDREDS = 500;

    int N128 = 128;

    int N256 = 256;

    int N512 = 512;

    int THOUSAND = 1000;

    int N1024 = 1024;

    int N4096 = 4096;

    int N8192 = 8192;

    int TWO_THOUSAND = 2000;

    int THREE_THOUSAND = 3000;

    int FIVE_THOUSAND = 5000;

    int TEN_THOUSAND = 10000;

    int TWENTY_THOUSAND = 20000;

    int HUNDRED_THOUSAND = 100000;

    long ONE_HOUR_MS = 3600 * 1000;

    int DAYS_ONE_YEAR = 365;

    int MILLISECONDS_PER_SECOND = 1000;

    int SECONDS_PER_MINUTE = 60;

    int MINUTES_PER_HOUR = 60;

    int HOURS_PER_DAY = 24;

    int DAYS_PER_WEEK = 7;

    int DAYS_PER_MONTH = 30;

    int DAYS_YEAR = 365;

    int MINUTE_MILLISECONDS = 60000;

    int FIVE_MINUTES_MILLISECONDS = 5 * 60000;

    int THREE_MINUTE_MILLISECONDS = 180000;

    int MINITE_SECONDS = 60;

    int FIVE_MINITES_SECONDS = 300;

    int TEN_MINITES_SECONDS = 600;

    int ONE_DAY_SECONDS = 86400;

    int ONE_DAY_MILLIS = ONE_DAY_SECONDS * 1000;

    int HOUR_SECONDS = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    int HOUR_MILLISECONDS = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    int DAY_SECONDS = SECONDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY;

    int THREE_DAY_SECONDS = DAY_SECONDS * 3;

    int TEN_DAY_SECONDS = DAY_SECONDS * 10;

    int WEEK_SECONDS = DAYS_PER_WEEK * HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;

    int WEEK_MILLISECONDS =
            DAYS_PER_WEEK * HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;

    int MONTH_SECONDS = SECONDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_MONTH;

    long MONTH_MILLISECONDS = MONTH_SECONDS * 1000L;

    int YEAR_SECONDS = SECONDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_ONE_YEAR;

    long YEAR_MILLISECONDS = YEAR_SECONDS * 1000L;

    int DAY_MILLISECONDS = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE * MINUTES_PER_HOUR
            * HOURS_PER_DAY;

    int MILLION = 1000000;

    long HUNDRED_MILLION = 100000000L;

    long BILLION = 1000000000L;

    double HUNDRED_D = 100.0d;
}
