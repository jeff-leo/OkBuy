package com.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	public static String getStackTrace(Exception e){
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		
		try {
			e.printStackTrace(printWriter);
			return writer.toString();
		} finally{
			printWriter.close();
		}
	}
}
