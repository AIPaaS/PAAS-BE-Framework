package com.binary.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.binary.core.exception.BinaryException;
import com.binary.core.lang.Conver;

public abstract class Command {

	
	public static String exec(String cmd) {
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			return readProcessOutput(process);
		}catch(Exception e) {
			throw new BinaryException(" exec command '"+cmd+"' error! ", e);
		}
	}
	
	
	
	public static String exec(String[] arr) {
		try {
			Process process = Runtime.getRuntime().exec(arr);
			return readProcessOutput(process);
		}catch(Exception e) {
			throw new BinaryException(" exec command '"+Conver.toString(arr)+"' error! ", e);
		}
	}
	
	
	private static String readProcessOutput(Process process) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			InputStream is = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String s = null;
			while((s=br.readLine()) != null) {
				if(sb.length() > 0) sb.append("\n");
				sb.append(s);
			}
		}finally {
			if(br != null) br.close();
		}
		return sb.toString();
	}
	
	
	
	
	
}
