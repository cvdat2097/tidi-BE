package tuan.tidi.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public abstract class FormatDate {
	public static String formatDate(Date date) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			return f.format(date);
		}
		catch(Exception e){
			return null;
		}
	}
	public static Date parseDate(String date) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			return f.parse(date);
		}
		catch(Exception e){
			return null;
		}
	}
	public static String formatDateTime(Date date) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return f.format(date);
		}
		catch(Exception e){
			return null;
		}
	}
	public static Date parseDateTime(String date) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return f.parse(date);
		}
		catch(Exception e){
			return null;
		}
	}
	public static String formatDateZP(Date date) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
			return f.format(date);
		}
		catch(Exception e){
			return null;
		}
	}
	public static Date parseDateZP(String date) {
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
			return f.parse(date);
		}
		catch(Exception e){
			return null;
		}
	}
}
