package com.binary.tools.mail;

import org.springframework.mail.javamail.JavaMailSender;

import com.binary.core.thread.BinaryThreadPool;





public class BinaryEmailSender {
	
	
	/**
	 * spring邮件发送对象
	 */
	private JavaMailSenderExt mailSender;
	
	
	/**
	 * 发件人邮箱地址
	 */
	private String senderMailAddr;
	
	/**
	 * 发件人邮箱密码
	 */
	private String senderMailPassword;
	
	/**
	 * 发件人邮箱密码是否加密
	 */
	private Boolean senderMailPwdEncrypted;
	
	/**
	 * 发件人名称
	 */
	private String senderMailName;
	
	
	/**
	 * 线程池
	 */
	private BinaryThreadPool threadPool;
	
	
	
	public BinaryEmailSender(JavaMailSenderExt mailSender) {
		this.mailSender = mailSender;
	}
	
	
	public void setSenderMailPwdEncrypted(Boolean senderMailPwdEncrypted) {
		this.senderMailPwdEncrypted = senderMailPwdEncrypted;
		this.mailSender.setPassword(this.senderMailPassword, this.senderMailPwdEncrypted);
	}
	public void setSenderMailPassword(String senderMailPassword) {
		this.senderMailPassword = senderMailPassword;
		this.mailSender.setPassword(this.senderMailPassword, this.senderMailPwdEncrypted);
	}
	public void setSenderMailAddr(String senderMailAddr) {
		this.senderMailAddr = senderMailAddr;
		this.mailSender.setUsername(this.senderMailAddr);
	}
	public void setThreadPool(BinaryThreadPool threadPool) {
		this.threadPool = threadPool;
	}


	public Boolean getSenderMailPwdEncrypted() {
		return senderMailPwdEncrypted;
	}
	public String getSenderMailAddr() {
		return senderMailAddr;
	}
	public String getSenderMailPassword() {
		return senderMailPassword;
	}
	public String getSenderMailName() {
		return senderMailName;
	}
	public void setSenderMailName(String senderMailName) {
		this.senderMailName = senderMailName;
	}



	public JavaMailSender getMailSender() {
		return mailSender;
	}
	
	
	
	
	
	/**
	 * 发送邮件
	 * @param title: 邮件标题
	 * @param receiverMailAddr: 接收方地址
	 * @param content: 邮件内容
	 */
	public void send(String title, String receiverMailAddr, String content) {
		send(title, new String[]{receiverMailAddr}, null, null, content, null);
	}
	
	
	/**
	 * 发送邮件
	 * @param title: 邮件标题
	 * @param receiverMailAddr: 接收方地址
	 * @param ccMailAddrs: 抄送人地址列表
	 * @param content: 邮件内容
	 */
	public void send(String title, String receiverMailAddr, String[] ccMailAddrs, String content) {
		send(title, new String[]{receiverMailAddr}, ccMailAddrs, null, content, null);
	}
	
	
	/**
	 * 发送邮件
	 * @param title: 邮件标题
	 * @param receiverMailAddrs: 接收方地址列表
	 * @param ccMailAddrs: 抄送人地址列表
	 * @param content: 邮件内容
	 */
	public void send(String title, String[] receiverMailAddrs, String[] ccMailAddrs, String content) {
		send(title, receiverMailAddrs, ccMailAddrs, null, content, null);
	}
	
	/**
	 * 发送邮件
	 * @param title: 邮件标题
	 * @param receiverMailAddrs: 接收方地址表列
	 * @param ccMailAddrs: 抄送人地址列表
	 * @param bccMailAddrs: 密送人地址列表
	 * @param content: 邮件内容
	 * @param contentType: 邮件内容格式
	 */
	public void send(String title, String[] receiverMailAddrs, String[] ccMailAddrs, String[] bccMailAddrs, String content, String contentType) {
		send(title, receiverMailAddrs, ccMailAddrs, bccMailAddrs, content, contentType, false);
	}
	
	
	/**
	 * 发送邮件
	 * @param title: 邮件标题
	 * @param receiverMailAddrs: 接收方地址表列
	 * @param ccMailAddrs: 抄送人地址列表
	 * @param bccMailAddrs: 密送人地址列表
	 * @param content: 邮件内容
	 * @param contentType: 邮件内容格式
	 * @param sync: 是否同步
	 */
	public void send(String title, String[] receiverMailAddrs, String[] ccMailAddrs, String[] bccMailAddrs, String content, String contentType, boolean sync) {
		EmailSenderLaunch launch = new EmailSenderLaunch(mailSender, senderMailAddr, senderMailName, title, receiverMailAddrs, ccMailAddrs, bccMailAddrs, content, contentType);
		if(sync) {
			launch.run();
		}else {
			threadPool.pushTask(launch);
		}
	}
	
	
	
	
	
	
	
	
	
	
}
