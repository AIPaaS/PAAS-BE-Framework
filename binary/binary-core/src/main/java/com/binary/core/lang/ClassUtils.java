package com.binary.core.lang;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.binary.core.exception.CoreException;
import com.binary.core.util.BinaryUtils;

public abstract class ClassUtils {
	
	
	private static final Map<Class<?>, Class<?>> primitiveTypes = new HashMap<Class<?>, Class<?>>(8);
	private static final Map<String, Class<?>> commonTypes = new HashMap<String, Class<?>>(48);
	
	
	static {
		primitiveTypes.put(Boolean.class, boolean.class);
		primitiveTypes.put(Byte.class, byte.class);
		primitiveTypes.put(Character.class, char.class);
		primitiveTypes.put(Double.class, double.class);
		primitiveTypes.put(Float.class, float.class);
		primitiveTypes.put(Integer.class, int.class);
		primitiveTypes.put(Long.class, long.class);
		primitiveTypes.put(Short.class, short.class);
		
		putCommonTypes(primitiveTypes.keySet().iterator());
		putCommonTypes(primitiveTypes.values().iterator());
		putCommonTypes(boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class);
		putCommonTypes(Boolean[].class, Byte[].class, Character[].class, Double[].class, Float[].class, Integer[].class, Long[].class, Short[].class);
		putCommonTypes(Number.class, Number[].class, String.class, String[].class, Object.class, Object[].class, Class.class, Class[].class);
		putCommonTypes(Throwable.class, Exception.class, RuntimeException.class, Error.class, StackTraceElement.class, StackTraceElement[].class);
	}
	
	
	private static void putCommonTypes(Class<?>... c) {
		for(int i=0; i<c.length; i++) {
			commonTypes.put(c[i].getName(), c[i]);
		}
	}
	private static void putCommonTypes(Iterator<Class<?>> itor) {
		while(itor.hasNext()) {
			Class<?> c = itor.next();
			commonTypes.put(c.getName(), c);
		}
	}
	
	
	
	/**
	 * 获取ClassLoader
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader loader = null;
		try {
			loader = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
		}
		if (loader == null) {
			loader = ClassUtils.class.getClassLoader();
		}
		return loader;
	}
	
	
	
	/**
	 * 重置当前线程环境classLoader
	 * @param classLoader
	 * @return
	 */
	public static ClassLoader setThreadContextClassLoader(ClassLoader classLoader) {
		Thread t = Thread.currentThread();
		ClassLoader oldClassLoader = t.getContextClassLoader();
		if (classLoader!=null && !classLoader.equals(oldClassLoader)) {
			t.setContextClassLoader(classLoader);
			return oldClassLoader;
		}else {
			return null;
		}
	}
	
	
	public static Class<?> forName(String className) {
		return forName(className, null);
	}
	
	
	public static Class<?> forName(String className, ClassLoader loader) {
		if(className==null || (className=className.trim()).length()==0) throw new CoreException(" the className is empty argument! ");
		Class<?> c = commonTypes.get(className);
		if (c != null) return c;
		
		//typeof java.lang.String[]
		if (className.endsWith("[]")) {	
			String componentClassName = className.substring(0, className.length()-2);
			Class<?> componentClass = forName(componentClassName, loader);
			return Array.newInstance(componentClass, 0).getClass();
		}

		//typeof [Ljava.lang.String;
		if (className.startsWith("[L") && className.endsWith(";")) {
			String componentClassName = className.substring(2, className.length()-1);
			Class<?> componentClass = forName(componentClassName, loader);
			return Array.newInstance(componentClass, 0).getClass();
		}

		//typeof [[I or [[Ljava.lang.String;
		if (className.charAt(0) == '[') {
			String componentClassName = className.substring(1);
			Class<?> componentClass = forName(componentClassName, loader);
			return Array.newInstance(componentClass, 0).getClass();
		}
		
		if(loader == null) loader = getClassLoader();
		
		try {
			return Class.forName(className, true, loader);
		}catch (ClassNotFoundException e) {
			int index = className.lastIndexOf('.');
			if (index > 0) {
				String innerClassName = className.substring(0, index) + '$' + className.substring(index+1);
				try {
					return Class.forName(innerClassName, true, loader);
				} catch (ClassNotFoundException e2) {
				}
			}
			throw new CoreException(e);
		}
	}
	
	
	public static <T> T newInstance(Class<T> c) {
		return newInstance(c, null, null);
	}
	
	
	public static <T> T newInstance(Class<T> c, Class<?>[] paramTypes, Object[] paramValues) {
		try {
			Constructor<T> cus = null;
			if(paramTypes == null) {
				if(paramValues!=null&&paramValues.length>0) throw new CoreException(" newInstance paramTypes, paramValues is different! ");
				paramTypes = Types.EMPTY;
			}else {
				if(paramTypes.length>0&&(paramValues==null||paramTypes.length!=paramValues.length)) {
					throw new CoreException(" newInstance paramTypes, paramValues is different! ");
				}
			}
			try {
				cus = c.getConstructor(paramTypes);
			}catch (NoSuchMethodException nsme) {
				String types = "";
				if(paramTypes!=null && paramTypes.length>0) {
					for(int i=0; i<paramTypes.length; i++) {
						if(i > 0) types += ", ";
						types += paramTypes[i].getSimpleName();
					}
				}
				throw new CoreException("is not found Constructor:'"+c.getName()+"("+types+")'!", nsme);
			}
			return cus.newInstance(paramValues);
		} catch (Exception e) {
			throw BinaryUtils.transException(e, CoreException.class);
		}
	}
	
}
