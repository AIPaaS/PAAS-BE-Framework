package com.binary.tools.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.tools.exception.EmailException;




public class EmailSenderLaunch implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(EmailSenderLaunch.class);
	
	
	
	/** 邮件发送者 **/
	JavaMailSenderExt mailSender;
	
	/** 邮件发送人地址 **/
	String senderMailAddr;
	
	/** 邮件发送人姓名 **/
	String senderMailName;
	
	
	/** 邮件标题 **/
	String title;
	
	/** 接收人地址 **/
	String[] receiverMailAddrs;
	
	/** 抄送人地址 **/
	String[] ccMailAddrs;
	
	/** 密送人地址 **/
	String[] bccMailAddrs;
	
	/** 邮件内容 **/
	String content;
	
	/** 邮件内容类型 **/
	String contentType;
	
	
	
	
	public EmailSenderLaunch(JavaMailSenderExt mailSender, String senderMailAddr, String senderMailName,
			String title, String[] receiverMailAddrs, String[] ccMailAddrs, String[] bccMailAddrs, String content, String contentType) {
		this.mailSender = mailSender;
		this.senderMailAddr = senderMailAddr;
		this.senderMailName = senderMailName;
		this.title = title;
		this.receiverMailAddrs = receiverMailAddrs;
		this.ccMailAddrs = ccMailAddrs;
		this.bccMailAddrs = bccMailAddrs;
		this.content = content;
		this.contentType = contentType;
	}
	
	
	
	
	
	private Address[] change2Address(String[] str_addres) throws AddressException {
		if(str_addres == null) return null;
		Address[] addres = new Address[str_addres.length];
		for(int i=0; i<str_addres.length; i++) {
			addres[i] = new InternetAddress(str_addres[i]);
		}
		return addres;
	}
	
	public void run() {
		try {
			log.info("开始发送邮件["+this.title+"]......");
			StringBuilder sb = new StringBuilder();
			if(this.receiverMailAddrs!=null && this.receiverMailAddrs.length>0) {
				sb.append("收件人: ").append(Conver.toString(this.receiverMailAddrs));
			}
			if(this.ccMailAddrs!=null && this.ccMailAddrs.length>0) {
				sb.append("抄送: ").append(Conver.toString(this.ccMailAddrs));
			}
			if(this.bccMailAddrs!=null && this.bccMailAddrs.length>0) {
				sb.append("密送: ").append(Conver.toString(this.bccMailAddrs));
			}
			
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
	            public void prepare(MimeMessage message) throws MessagingException {
	            	String charset = mailSender.getDefaultEncoding();
	            	if(charset==null || (charset=charset.trim()).length()==0) charset = "UTF-8";
	            	try {
						message.setFrom(new InternetAddress(senderMailAddr, senderMailName, charset));
					} catch (UnsupportedEncodingException e) {
						throw new MessagingException("charset is wrong:'"+charset+"'!", e);
					}
	    			message.setSubject(title, charset);
	    			
	    			boolean hasrecs = false;
	    			if(receiverMailAddrs!=null && receiverMailAddrs.length>0) {
	    				message.setRecipients(RecipientType.TO, change2Address(receiverMailAddrs));
	    				hasrecs = true;
	    			}
	    			if(ccMailAddrs!=null && ccMailAddrs.length>0) {
	    				if(hasrecs) {
	    					message.addRecipients(RecipientType.CC, change2Address(ccMailAddrs));
	    				}else {
	    					message.setRecipients(RecipientType.CC, change2Address(ccMailAddrs));
	    				}
	    				hasrecs = true;
	    			}
	    			if(bccMailAddrs!=null && bccMailAddrs.length>0) {
	    				if(hasrecs) {
	    					message.addRecipients(RecipientType.BCC, change2Address(bccMailAddrs));
	    				}else {
	    					message.setRecipients(RecipientType.BCC, change2Address(bccMailAddrs));
	    				}
	    				hasrecs = true;
	    			}
	    			if(!hasrecs) {
	    				throw new EmailException("is not found recipients!");
	    			}
	    			
	    			message.setSentDate(new Date());
	    			
	    			String type = contentType;
	    			if(type==null || (type=type.trim()).length()==0) {
	    				type = "text/html;charset="+charset;
	    			}
	    			message.setContent(content, type);
	            }
	        };
			this.mailSender.send(preparator);
			
			log.info("邮件["+this.title+"]发送成功!");
		}catch(Exception e) {
			log.error("邮件["+this.title+"]发送失败!", e);
			throw BinaryUtils.transException(e, EmailException.class, "send email is failed:'"+title+"'!");
		}
	}
	
	
	

}
