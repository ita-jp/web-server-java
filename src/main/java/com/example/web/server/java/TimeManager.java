package com.example.web.server.java;

import lombok.val;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeManager {

	public String nowAsRFC7231() {
		val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		val df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
		df.setTimeZone(calendar.getTimeZone());
		return df.format(calendar.getTime()) + " GMT";
	}
}
