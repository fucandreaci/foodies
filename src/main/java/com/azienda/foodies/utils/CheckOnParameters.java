package com.azienda.foodies.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CheckOnParameters {
	public static Boolean areNotEmpty(String...args) {
		if(args != null) {
			boolean campoVuoto = false;
			for(String arg : args) {
				if (arg == null) {
					campoVuoto = true;
					continue;
				}
				arg = arg.replace(" ", "");
				if(arg.isEmpty()) {
					campoVuoto = true;
				}
			}
			return campoVuoto ? false : true;
		}else {
			return false;
		}
	}
	
	public static LocalDateTime stringToDate(String data) {
		LocalDateTime date = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		return date;
	}
	
	// CONTROLLO CHE LA DATA IN INGRESSO NON SIA DOPO OGGI, IN QUESTO CASO E' VALIDA
		public static boolean dateValid(LocalDateTime data) {
			if (data.isAfter(LocalDateTime.now())) {
				return false;
			} else {
				return true;
			}
		}

		public static Date convertToDate(LocalDate dateToConvert) {
			return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		}
		
		// CONTROLLO CHE LA STRINGA IN INGRESSO SIA UNA URL VALIDA
		public static boolean hasPattern(String s, String pattern) {
//			"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			try {
				Pattern patt = Pattern.compile(pattern);
				Matcher matcher = patt.matcher(s);
				return matcher.matches();
			} catch (RuntimeException e) {
				return false;
			}
		}
}
