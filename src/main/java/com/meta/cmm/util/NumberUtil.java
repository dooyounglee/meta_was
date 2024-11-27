package com.meta.cmm.util;

public class NumberUtil {
    
    public static double round(double num, int digit) {
		return Math.round(num * Math.pow(10, digit)) / Math.pow(10, digit);
	}
	
	public static double ceil(double num, int digit) {
		return Math.ceil(num * Math.pow(10, digit)) / Math.pow(10, digit);
	}
	
	public static double floor(double num, int digit) {
		return Math.floor(num * Math.pow(10, digit)) / Math.pow(10, digit);
	}
	
	public static String lpad(int num, int length) {
		return StringUtil.lpad(String.valueOf(num), length, "0");
	}
}
