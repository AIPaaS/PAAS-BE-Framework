package com.binary.application.java;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binary.application.exception.JavaException;
import com.binary.core.io.Resource;
import com.binary.core.io.ResourceResolver;
import com.binary.core.lang.ClassUtils;
import com.binary.core.util.BinaryUtils;

public class JavaApplication {
	private static Logger log = LoggerFactory.getLogger(JavaApplication.class);

	
	/** 服务器配置信息 **/
	private JavaConfiguration configuration;
	
	
	
	public JavaApplication(String configuration) {
		JavaConfiguration conf = new JavaConfiguration();
		
		Resource rs = ResourceResolver.getResource(configuration);
		if(rs!=null && rs.exists()) {
			conf.loadResource(rs);
		}else {
			throw new JavaException(" is not found configuration java.properties:'"+configuration+"'! ");
		}
		
		this.configuration = conf;
	}
	
	public JavaApplication(JavaConfiguration configuration) {
		BinaryUtils.checkEmpty(configuration, "configuration");
		this.configuration = configuration;
	}
	
	
	
	public void start() {
		log.info("start java-application ......");
		
		log.info("begin initialization main-class ......");
		
		String mainClass = configuration.getMainClass();
		if(mainClass==null || (mainClass=mainClass.trim()).length()==0) {
			throw new JavaException(" the mainClass is empty in configuration! ");
		}
		
		try {
			Class<?> main = ClassUtils.forName(mainClass);
			Method m = main.getMethod("main", String[].class);
			if(m == null) {
				throw new JavaException(" is not found main-method in mainClas '"+mainClass+"'! ");
			}
			if(!Modifier.isStatic(m.getModifiers())) {
				throw new JavaException(" the main method is not static in mainClas '"+mainClass+"'!! ");
			}
			
			log.info("begin start java-application ......");
			
			String mainArgs = configuration.getMainArgs();
			String[] args = null;
			if(mainArgs!=null && (mainArgs=mainArgs.trim()).length()>0) {
				args = mainArgs.split("[;]");
			}
			
			m.invoke(main, new Object[]{args});
		}catch(Throwable t) {
			throw BinaryUtils.transException(t, JavaException.class);
		}
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		
	}
	
	
	
}
