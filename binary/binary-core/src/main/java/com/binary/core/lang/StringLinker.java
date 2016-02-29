package com.binary.core.lang;

import java.io.Serializable;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.CoreException;

public class StringLinker implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String[] array;
	private String[] keys;
	
	
	public StringLinker() {
	}
	public StringLinker(String[] array, String[] keys) {
		this.array = array;
		this.keys = keys;
	}
	public String[] getArray() {
		return array;
	}
	public void setArray(String[] array) {
		this.array = array;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
	public StringBuilder link(Object paramsInstance) {
		return link(paramsInstance, false, null);
	}
	public StringBuilder link(Object paramsInstance, StringBuilder sb) {
		return link(paramsInstance, false, sb);
	}
	
	public StringBuilder link(Object paramsInstance, boolean ignoreNull, StringBuilder sb) {
		return link(paramsInstance, ignoreNull, false, sb);
	}
	
	/**
	 * 连接当前字段串
	 * @param paramsInstance: 参数对象Map/Bean
	 * @param ignoreNull: 是否忽略null值, 忽略表示动态值在参数对象中没有找到, 则以空字段串代替, 否则拼入null
	 * @param validKeyExists: 验证键名是否存在
	 * @param sb
	 * @return
	 */
	public StringBuilder link(Object paramsInstance, boolean ignoreNull, boolean validKeyExists, StringBuilder sb) {
		if(sb == null) sb = new StringBuilder();
		if(this.array==null || this.array.length==0) return sb;
		BMProxy<?> proxy = paramsInstance==null ? null : BMProxy.getInstance(paramsInstance);
		for(int i=0; i<array.length; i++) {
			sb.append(array[i]);
			
			if(validKeyExists && keys.length>i && (proxy==null||(proxy!=null&&!proxy.containsKey(keys[i])))) {
				throw new CoreException(" the parameter:'"+keys[i]+"' is not defined! ");
			}
			
			if(keys.length>i && proxy!=null) {
				Object v = proxy.get(keys[i]);
				sb.append(v==null?(ignoreNull?"":null):Conver.to(v, String.class));
			}
		}
		return sb;
	}
	
	
	
}
