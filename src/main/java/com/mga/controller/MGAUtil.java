package com.mga.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * to define constants and utilities
 * @author subha
 *
 */
public class MGAUtil {
	
	public static final String sole_file = "SOLE";
	public static final String dicom_file = "DICOM";
	public static final String otherdocs_file = "OTHERS";
	
	
	public static String birthDateDateToString(Date dob) {
		String dateString = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			dateString = formatter.format(dob);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	public static Date birthDateStringToDate(String dob, String method) {
		SimpleDateFormat formatter = null;
		if(method.equals("Insert")){
			formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		}else if(method.equals("Update")){
			formatter = new SimpleDateFormat("MM/dd/yyyy");
		}else if(method.equals("Misc")){
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		}
		Date date = null;
		try {
			date = formatter.parse(dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	
	public static Integer calculateAge(String dob) {
		
		if(dob == null || dob.isEmpty())
			return 0;
		Integer patientAge = 0;
		Integer factor = 0; 
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		Date currentDate = new Date();
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("MM/dd/yyyy").parse(dob);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal1.setTime(date1);
		cal2.setTime(currentDate);

		if (cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
			factor = -1;
		}
		patientAge = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
		
		return patientAge;

	}
	
	
	public static String contactNoPlusConversion(String contactNumber) {
		String contactNo = null;
		if (contactNumber != null && !contactNumber.isEmpty()
				&& contactNumber.subSequence(0, 4).equals("#43#")) {
			contactNo = contactNumber.replaceFirst("#43#", "+");
		} else {
			contactNo = contactNumber;
		}
		return contactNo;
	}

}
