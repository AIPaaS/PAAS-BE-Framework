package com.binary.framework.dubbo;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.Application;
import com.binary.framework.Local;
import com.binary.framework.bean.User;
import com.binary.framework.exception.DubboException;
import com.binary.framework.exception.FrameworkException;



public class DubboMain {
	private static final Logger log = Logger.getLogger(DubboMain.class);
	
	
	public static void main(String[] args) {
		String prefix = "BINARY-FRAMEWORK";
		
		log.info(prefix+": 由DubboMain为启动入口, 正在启动......");
		try {
			ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
			try {
				Local.open((User)null);
				Application.afterInitialization(context);
				
				try {
					Local.commit();
				}catch(Exception ex) {
					log.error(prefix+": Local.commit()出现错误!", ex);
				}
			}catch(Exception e) {
				try {
					Local.rollback();
				}catch(Exception ex) {
					log.error(prefix+": Local.rollback出现错误!", ex);
				}
				
				throw new FrameworkException(e);
			}finally {
				try {
					Local.close();
				}catch(Exception ex) {
					log.error(prefix+": Local.close()出现错误!", ex);
				}
			}
		}catch(Exception e) {
			log.error(prefix+": 启动DubboMain出现错误!", e);
			throw BinaryUtils.transException(e, DubboException.class, "Error start DubboMain.");
		}
		
		log.info(prefix+": 由DubboMain为启动入口, 启动成功!");
		
		
		com.alibaba.dubbo.container.Main.main(args);
	}
	
	
	
}
