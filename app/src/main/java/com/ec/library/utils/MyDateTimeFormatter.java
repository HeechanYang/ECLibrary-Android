package com.ec.library.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyDateTimeFormatter {
    public static SimpleDateFormat yMdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA);
    public static SimpleDateFormat yMdhm = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.KOREA);
}
