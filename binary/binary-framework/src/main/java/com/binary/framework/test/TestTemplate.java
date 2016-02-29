package com.binary.framework.test;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.binary.core.exception.MultipleException;
import com.binary.framework.Application;
import com.binary.framework.Local;
import com.binary.framework.bean.User;
import com.binary.framework.exception.FrameworkException;
import com.binary.framework.exception.TestException;
import com.binary.jdbc.Page;
import com.binary.json.JSON;



public abstract class TestTemplate {
	
	
	private static ApplicationContext context;
	
	
	
	@BeforeClass
	public static final void beforeClass() {
		if(context == null) {
			context = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
			try {
				Local.open((User)null);
				Application.afterInitialization(context);
				Local.commit();
			}catch(Exception e) {
				Local.rollback();
				throw new FrameworkException(e);
			}finally {
				Local.close();
			}
		}
	}
	
	
	
	@Before
	public final void before() {
		if(!Local.isOpen()) {
			Local.open(getLoginCode());
		}
	}
	
	
	
	
	@AfterClass
	public final static void after() {
		MultipleException me = new MultipleException("test-after-error...");
		try {
			Local.commit();
		}catch(Throwable t1) {
			me.add(new TestException(" local-commit-error! ", t1));
		}
		
		try {
			Local.close();
		}catch(Throwable t1) {
			me.add(new TestException(" local-close-error! ", t1));
		}
		
		if(me.size() > 0) me.printStackTrace();
	}
	
	
	protected static final <T> T execute(Class<?> svcClass, String methodName, Object... params) {
		return execute(svcClass, null, methodName, params);
	}
	
	
	
	protected static final ApplicationContext getSpringContext() {
		return context;
	}
	
	protected static final Object getBean(String name) {
		return context.getBean(name);
	}
	
	protected static final <T> T getBean(Class<T> requiredType ) {
		return context.getBean(requiredType);
	}
	
	protected static final <T> T getBean(String name, Class<T> requiredType) {
		return context.getBean(name, requiredType);
	}
	
	
	
	
	protected static <T> void printPage(Page<T> page) {
		System.out.println("pageNum: "+page.getPageNum());
		System.out.println("pageSize: "+page.getPageSize());
		System.out.println("totalRows: "+page.getTotalRows());
		System.out.println("totalPages: "+page.getTotalPages());
		
		List<T> list = page.getData();
		printList(list);
	}
	
	
	protected static <T> void printList(List<T> list) {
		if(list == null) {
			System.out.println("data: null");
		}else {
			System.out.println("data-count: "+list.size());
			for(int i=0; i<list.size(); i++) {
				System.out.println(JSON.toString(list.get(i)));
			}
		}
	}
	
	
	
	
	
	/**
	 * 获取登录代码
	 * @return
	 */
	protected String getLoginCode() {
		return null;
	}
	
	
	
	
}
