package com.binary.core.util;

import org.w3c.dom.Element;

public class XMLUtils {
	
	
	
	/**
	 * 取element属性
	 * @param el
	 * @param name
	 * @param trim
	 * @param variableMap
	 * @return
	 */
	public static String getAttribute(Element el, String name, boolean trim, Object paramsObj) {
		String v = el.getAttribute(name);
		if(v!=null && trim) v = v.trim();
		if(v!=null&&v.length()>0&&v.indexOf('$')>-1) {
			v = BinaryUtils.parseExpression(v).link(paramsObj).toString();
		}
		return v;
	}
	
	
	
	/**
	 * 先取element-value属性, 再取element-textcontent
	 * @param el
	 * @param trim
	 * @param variableMap
	 * @return
	 */
	public static String getElementValue(Element el, boolean trim, Object paramsObj) {
		String v = el.getAttribute("value");
		if(v!=null && trim) v = v.trim();
		if(v==null || v.length()==0) {
			v = el.getTextContent();
			if(v!=null && trim) v = v.trim();
		}
		if(v!=null&&v.length()>0&&v.indexOf('$')>-1) {
			v = BinaryUtils.parseExpression(v).link(paramsObj).toString();
		}
		return v;
	}
	
	
	
	
	
	
	
	
}
