package com.binary.tools.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;






/**
 * 扩展JavaMailSenderImpl
 * @author wanwb
 *
 */
public class JavaMailSenderExt extends JavaMailSenderImpl {
	
	
	
	public void setPassword(String password, Boolean encrypted) {
//		if(Boolean.TRUE.equals(encrypted)) {
//			password = Encrypt.decrypt(password);
//		}
		super.setPassword(password);
	}
	
	
	

}
