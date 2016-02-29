package com.binary.framework;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.binary.dubbo.intercept.DubboAspect;
import com.binary.dubbo.intercept.DubboAspectType;
import com.binary.dubbo.intercept.impl.DubboConsumerInterceptor;
import com.binary.dubbo.intercept.impl.DubboProviderInterceptor;
import com.binary.framework.exception.DubboException;
import com.binary.framework.exception.FrameworkException;

public class Application {

	
	private static final List<ApplicationListener> listeners = new ArrayList<ApplicationListener>();
	
	private static final List<DubboAspect> dubboAspects = new ArrayList<DubboAspect>();
	
	
	
	public Application() {
	}
	public Application(List<ApplicationListener> listeners) {
		setApplicationListeners(listeners);
	}
	
	
	
	public void setApplicationListeners(List<ApplicationListener> listeners) {
		if(listeners!=null && listeners.size()>0) {
			for(int i=0; i<listeners.size(); i++) {
				ApplicationListener listener = listeners.get(i);
				if(Application.listeners.contains(listener)) {
					throw new FrameworkException(" the application-listener '"+listener.toString()+"' is exists! ");
				}
				Application.listeners.add(listener);
			}
		}
	}
	
	
	public void setDubboAspects(List<DubboAspect> dubboAspects) {
		if(dubboAspects!=null && dubboAspects.size()>0) {
			for(int i=0; i<dubboAspects.size(); i++) {
				DubboAspect aspect = dubboAspects.get(i);
				if(Application.dubboAspects.contains(aspect)) {
					throw new FrameworkException(" the dubbo-aspects '"+aspect.toString()+"' is exists! ");
				}
				Application.dubboAspects.add(aspect);
			}
		}
	}
	
	
	
	
	public static boolean containsListener(ApplicationListener listener) {
		return listeners.contains(listener);
	}
	
	
	public static void addListener(ApplicationListener listener) {
		if(listeners.contains(listener)) {
			throw new FrameworkException(" the ApplicationListener '"+listener.getClass().getName()+"' is exists! ");
		}
		listeners.add(listener);
	}
	
	
	public static boolean removeListener(ApplicationListener listener) {
		return listeners.remove(listener);
	}
	
	
	
	
	
	
	public static void afterInitialization(ApplicationContext context) {
		if(dubboAspects!=null && dubboAspects.size()>0) {
			DubboProviderInterceptor provider = DubboProviderInterceptor.getInstance();
			DubboConsumerInterceptor consumer = DubboConsumerInterceptor.getInstance();
			
			for(int i=0; i<dubboAspects.size(); i++) {
				DubboAspect aspect = dubboAspects.get(i);
				DubboAspectType type = aspect.getDubboAspectType();
				if(type == DubboAspectType.PROVIDER) {
					if(provider == null) throw new DubboException(" not setting dubbo.provider.filter! ");
					provider.addAspect(aspect);
				}else if(type == DubboAspectType.CONSUMER) {
					if(consumer == null) throw new DubboException(" not setting dubbo.consumer.filter! ");
					consumer.addAspect(aspect);
				}
			}
		}
		for(int i=0; i<listeners.size(); i++) {
			listeners.get(i).afterInitialization(context);
		}
	}
	
	
	
	
	
	
	
	
}
