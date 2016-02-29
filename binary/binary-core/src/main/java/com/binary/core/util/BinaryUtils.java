package com.binary.core.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.binary.core.bean.BMProxy;
import com.binary.core.exception.BinaryException;
import com.binary.core.exception.CoreException;
import com.binary.core.lang.ClassUtils;
import com.binary.core.lang.StringLinker;
import com.binary.core.lang.StringUtils;

@SuppressWarnings({"unchecked","rawtypes"})
public class BinaryUtils {
	
	
	private static final Random RND = new Random();
	private static final SimpleDateFormat DF_DATE_NUM = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat DF_DATETIME_NUM = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final Object DF_DATE_NUM_SYNC = new Object();
	private static final Object DF_DATETIME_NUM_SYNC = new Object();
	
	
	private static class RandomSortObject<E> {
		int id;
		E e;
		RandomSortObject(E e) {
			id = RND.nextInt();
			this.e = e;
		}
	}
	private static Comparator RandomSortComparator = new Comparator<RandomSortObject>() {
		@Override
		public int compare(RandomSortObject o1, RandomSortObject o2) {
			return o1.id>o2.id ? 1 : (o1.id==o2.id?0:-1);
		}
	};
	
	
	private BinaryUtils() {
	}
	
	
	/**
	 * 是否是数组或java.util.List
	 * @param c
	 */
	public static boolean isArray(Class<?> c) {
		return c.isArray() || List.class.isAssignableFrom(c);
	}
	
	/**
	 * 获取数组或List长度
	 * @param array
	 * @return
	 */
	public static int getArrayLength(Object array) {
		return array==null ? 0 : (array instanceof List) ? ((List)array).size() : Array.getLength(array);
	}
	
	/**
	 * 获取数组或List元素类型
	 * @param array
	 * @return 如果数组为空或数组每一个元素都为null, 则返回null
	 */
	public static Class<?> getComponentType(Object array) {
		if(array == null) return null;
		int length = getArrayLength(array);
		Class<?> type = null;
		for(int i=0; i<length; i++) {
			Object v = getArrayValue(array, i);
			if(v != null) {
				type = v.getClass();
				break;
			}
		}
		return type;
	}
	
	/**
	 * 获取数组元素
	 * @param array
	 * @param index
	 * @return
	 */
	public static Object getArrayValue(Object array, int index) {
		return array instanceof List ? ((List)array).get(index) : Array.get(array, index);
	}
	
	
	/**
	 * 设置数组元素
	 * @param array
	 * @param index
	 * @param value
	 */
	public static void setArrayValue(Object array, int index, Object value) {
		if(array instanceof List) {
			List list = (List)array;
			if(list.size() <= index) {
				list.add(value);
			}else {
				list.remove(index);
				list.add(index, value);
			}
		}else {
			Array.set(array, index, value);
		}
	}
	
	
	/**
	 * 创建数组对象
	 * @param componentType
	 * @param length
	 * @return
	 */
	public static <T> T[] newArrayInstance(Class<T> componentType, int length) {
		return (T[])Array.newInstance(componentType, length);
	}
	
	
	
	
	public static boolean is(Boolean b) {
		return b!=null && b.booleanValue();
	}
	
	public static boolean isEmpty(Object v) {
		return isEmpty(v, true);
	}
	
	
	
	/**
	 * 判断对象是否为空, 其中String、Collection、Map、Array会判断是否为空内容, 其他对象都为判断是否为null
	 * @param v : 判断对象
	 * @param trim : 如果判断对象类型为String类型时, 设置是否先trim()再判断
	 * @return
	 */
	public static boolean isEmpty(Object v, boolean trim) {
		if(v == null) return true;
		if(v instanceof String) {
			String sv = (String) v;
			return trim ? sv.trim().length()==0 : sv.length()==0;
		}else if(v instanceof Collection) {
			Collection<?> c = (Collection<?>)v;
			return c.size() == 0;
		}else if(v instanceof Map) {
			Map<?,?> m = (Map<?,?>)v;
			return m.size() == 0;
		}else if(isArray(v.getClass())) {
			return getArrayLength(v) == 0;
		}
		return false;
	}
	
	
	/**
	 * @see parseExpression(String exp, String lregex, String r, boolean validate)
	 * @return
	 */
	public static StringLinker parseExpression(String exp) {
		return StringUtils.parseExpression(exp);
	}
	
	/**
	 * @see parseExpression(String exp, String lregex, String r, boolean validate)
	 * @return
	 */
	public static StringLinker parseExpression(String exp, boolean validate) {
		return StringUtils.parseExpression(exp, validate);
	}
	
	/**
	 * 解析类似带${}参数表达式
	 * @param exp: 表达式
	 * @param lregex: 左'括号', 缺省为：${
	 * @param r: 右'括号', 缺省为：}
	 * @param validate: 是否验证左右括号对称性, 缺省为true
	 * @return 
	 */
	public static StringLinker parseExpression(String exp, String lregex, String r, boolean validate) {
		return StringUtils.parseExpression(exp, lregex, r, validate);
	}
		

	/**
	 * 获取List对象
	 * @param c
	 * @return
	 */
	public static List<?> getListInstance(Class<?> c) {
		if(!List.class.isAssignableFrom(c)) throw new CoreException(" is not support List Class:'"+c.getCanonicalName()+"'! ");
		try {
			if(c.isInterface()) {
				if(c.isAssignableFrom(ArrayList.class)) {
					return new ArrayList<Object>();
				}else {
					throw new CoreException(" is not create default List Class:'"+c.getCanonicalName()+"'! ");
				}
			}else {
				return (List<?>) c.newInstance();
			}
		}catch(Exception e) {
			throw new CoreException(" create List Instace Error! ", e);
		}
	}
	
	
	/**
	 * 获取Map对象
	 * @param c
	 * @return
	 */
	public static Map<?,?> getMapInstance(Class<?> c) {
		if(!Map.class.isAssignableFrom(c)) throw new CoreException(" is not support Map Class:'"+c.getName()+"'! ");
		try {
			if(c.isInterface()) {
				if(c.isAssignableFrom(HashMap.class)) {
					return new HashMap<Object,Object>();
				}else {
					throw new CoreException(" is not create default Map Class:'"+c.getName()+"'! ");
				}
			}else {
				return (Map<?,?>) c.newInstance();
			}
		}catch(Exception e) {
			throw new CoreException(" create Map Instace Error! ", e);
		}
	}
	
	
	
	/**
	 * 转换异常
	 * @param throwed: 抛出的异常
	 * @param hope: 期望所转换的异常
	 * @return
	 */
	public static BinaryException transException(Throwable throwed, Class<? extends BinaryException> hope) {
		return transException(throwed, hope, null);
	}
	
	
	/**
	 * 转换异常
	 * @param throwed: 抛出的异常
	 * @param hope: 期望所转换的异常
	 * @param appMsg: 添加的异常信息
	 * @return
	 */
	public static BinaryException transException(Throwable throwed, Class<? extends BinaryException> hope, String appMsg) {
		if(throwed instanceof InvocationTargetException) {
			InvocationTargetException ite = (InvocationTargetException)throwed;
			return transException(ite.getTargetException(), hope, appMsg);
		}
		if((throwed instanceof BinaryException) && (appMsg==null || appMsg.length()==0)) {
			return (BinaryException)throwed;
		}
		
		Class<? extends BinaryException> transC = hope==null ? BinaryException.class : hope;
		
		Class<?>[] paramTypes = new Class<?>[]{ String.class, Throwable.class };
		Object[] params = new Object[]{ appMsg, throwed };
		BinaryException trans = ClassUtils.newInstance(transC, paramTypes, params);
		
		return trans;
	}
	
	
	
	/**
	 * 对List批量添加元素
	 * @param master
	 * @param array
	 * @return
	 */
	public static <T> List<T> addAsList(List<T> master, T[] array) {
		if(array == null) return master;
		if(master == null) master = new ArrayList<T>();
		for(int i=0; i<array.length; i++) {
			master.add(array[i]);
		}
		return master;
	}
	
	
	
	/**
	 * 获取uuid
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	
	/**
	 * 获取一个整数的位数, 如0=1位, 8=1位, 11=2位, 132=3位, 431346=6位
	 * 效率比通过String.valueOf(v).length()快很多
	 * @param v : 整数
	 * @return 位数
	 */
	public static int getBits(long v) {
		if(v < 0) v = 0 - v;
		
		int len = 1;
		while(v > 9) {
			v /= 10;
			len ++ ;
		}
		
		return len;
	}
	
	
	
	/**
	 * 生成一个随机数
	 * @param max : 指定最大值(不包括)
	 * @return value>=0 && value<max
	 */
	public static long random(long max) {
		if(max < 1) throw new BinaryException(" the max is wrong '"+max+"'! ");
		
		if(max > Integer.MAX_VALUE) {
			long v = RND.nextLong();
			if(v < 0) v = 0 - v;
			
			return v % max;
		}else {
			return RND.nextInt((int)max);
		}
	}
	
	
	
	/**
	 * 验证参数是否为空
	 * @param value: 验证对象
	 * @param name: 参数名称
	 * @return
	 */
	public static void checkNull(Object value, String name) {
		if(value == null) {
			throw new IllegalArgumentException(" the '"+name+"' is null argument! ");
		}
	}
	
	
	
	/**
	 * 验证参数是否为空
	 * @param value: 验证对象
	 * @param name: 参数名称
	 * @return
	 */
	public static void checkEmpty(Object value, String name) {
		checkEmpty(value, true, name);
	}
	
	/**
	 * 验证参数是否为空
	 * @param value: 验证对象
	 * @param trim: 如果值是String类型时是否time
	 * @param name: 参数名称
	 * @return
	 */
	public static void checkEmpty(Object value, boolean trim, String name) {
		if(isEmpty(value, trim)) {
			throw new IllegalArgumentException(" the '"+name+"' is "+(value==null?"null":"empty")+" argument! ");
		}
	}
	
	
	
	
	/**
	 * 将二进制数按单位转换
	 * @param v
	 * @return
	 */
	public static String toByteUnit(Long v) {
		if(v == null) return null;
		
		if(v >= 1125899906842624l) {
			return ((double)((long)(((double)v*100)/1125899906842624l)))/100 + "P";
		}else if(v >= 1099511627776l) {
			return ((double)((long)(((double)v*100)/1099511627776l)))/100 + "T";
		}else if(v >= 1073741824) {
			return ((double)((long)(((double)v*100)/1073741824)))/100 + "G";
		}else if(v >= 1048576) {
			return ((double)((long)(((double)v*100)/1048576)))/100 + "M";
		}else if(v >= 1024) {
			return ((double)((long)(((double)v*100)/1024)))/100 + "K";
		}else {
			return v + "";
		}
	}
	
	
	
	/**
	 * 将带单位的数据按单位转换二进制值
	 * @param v
	 * @return
	 */
	public static long parsetByteUnit(String v) {
		if(v==null || (v=v.trim()).length()==0) return 0;
		
		char last = v.charAt(v.length()-1);
		if(last>='0' && last<='9') {
			return (long)Double.parseDouble(v);
		}else {
			double b = Double.parseDouble(v.substring(0, v.length()-1).trim());
			if(last=='K' || last=='k') {
				b *= 1024;
			}else if(last=='M' || last=='m') {
				b *= 1048576;
			}else if(last=='G' || last=='g') {
				b *= 1073741824;
			}else if(last=='T' || last=='t') {
				b *= 1099511627776l;
			}else if(last=='P' || last=='p') {
				b *= 1125899906842624l;
			}else {
				throw new BinaryException(" is wrong value '"+v+"'! ");
			}
			return (long)b;
		}
	}
	
	
	
	
	

	
	/**
	 * 获取日期格式数值, 格式为：yyyyMMdd
	 * @return
	 */
	public static int getNumberDate() {
		return getNumberDate(new Date());
	}
	public static int getNumberDate(Date date) {
		synchronized (DF_DATE_NUM_SYNC) {
			return Integer.parseInt(DF_DATE_NUM.format(date));
		}
	}
	
	
	
	
	/**
	 * 获取日期时间格式数值, 格式为：yyyyMMddHHmmss
	 * @return
	 */
	public static long getNumberDateTime() {
		return getNumberDateTime(new Date());
	}
	public static long getNumberDateTime(Date date) {
		synchronized (DF_DATETIME_NUM_SYNC) {
			return Long.parseLong(DF_DATETIME_NUM.format(date));
		}
	}
	
	
	
	/**
	 * 将日期格式的数值还原为日期类型
	 * @param dateNum
	 * @return
	 */
	public static Date toDate(int dateNum) {
		String s = String.valueOf(dateNum);
		if(s.length() != 8) throw new BinaryException(" is wrong date-num '"+dateNum+"'! ");
		try {
			synchronized (DF_DATE_NUM_SYNC) {
				return DF_DATE_NUM.parse(s);
			}
		} catch (ParseException e) {
			throw new BinaryException(" is wrong date-num '"+dateNum+"'! ", e);
		}
	}
	
	
	
	/**
	 * 将日期时间格式的数值还原为日期类型
	 * @param dateNum
	 * @return
	 */
	public static Date toDateTime(long dateTimeNum) {
		String s = String.valueOf(dateTimeNum);
		if(s.length() != 14) throw new BinaryException(" is wrong dateTime-num '"+dateTimeNum+"'! ");
		try {
			synchronized (DF_DATETIME_NUM_SYNC) {
				return DF_DATETIME_NUM.parse(s);
			}
		} catch (ParseException e) {
			throw new BinaryException(" is wrong dateTime-num '"+dateTimeNum+"'! ", e);
		}
	}
	
	
	
	/**
	 * 将日期格式的数值转换成String日期格式, 由yyyyMMdd  -> yyyy-MM-dd
	 * @param dateNum
	 * @return
	 */
	public static String toStringDate(int dateNum) {
		String s = String.valueOf(dateNum);
		if(s.length() != 8) throw new BinaryException(" is wrong date-num '"+dateNum+"'! ");
		return s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,8);
	}
	
	
	
	/**
	 * 将日期时间格式的数值转换成String日期格式, 由yyyyMMddHHmmss  -> yyyy-MM-dd HH:mm:ss
	 * @param dateTimeNum
	 * @return
	 */
	public static String toStringDateTime(long dateTimeNum) {
		String s = String.valueOf(dateTimeNum);
		if(s.length() != 14) throw new BinaryException(" is wrong dateTime-num '"+dateTimeNum+"'! ");
		return s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,8)+" "+s.substring(8,10)+":"+s.substring(10,12)+":"+s.substring(12,14);
	}
	
	
	
	
	
	/**
	 * 随机排序 (打乱队列的排列顺序)
	 * @param array
	 */
	public static <T> void randomSort(T[] array) {
		if(array==null || array.length<2) return ;
		RandomSortObject<T>[] ros = new RandomSortObject[array.length];
		for(int i=0; i<array.length; i++) {
			ros[i] = new RandomSortObject(array[i]);
		}
		Arrays.sort(ros, RandomSortComparator);
		
		for(int i=0; i<array.length; i++) {
			array[i] = ros[i].e;
		}
	}
	
	
	/**
	 * 随机排序 (打乱队列的排列顺序)
	 * @param array
	 */
	public static <T> void randomSort(List<T> list) {
		if(list==null || list.size()<2) return ;
		
		RandomSortObject<T>[] ros = new RandomSortObject[list.size()];
		for(int i=0; i<ros.length; i++) {
			ros[i] = new RandomSortObject(list.get(i));
		}
		Arrays.sort(ros, RandomSortComparator);
		
		for(int i=0; i<ros.length; i++) {
			list.set(i, ros[i].e);
		}
	}
	
	
	
	
	/**
	 * 将对象数据转换为Map形式, 当键字段为null时则忽略
	 * 应用场景,如: 需要将人员列表转换为以人员ID为键的Map, List<SysOp>  ->  Map<Long, SysOp> key=opId
	 * @param ls 对象数据
	 * @param keyName 做为键的字段名
	 * @param keyType 做为键的字段类型
	 * @return 不会返回null
	 */
	public static <K, V> Map<K, V> toObjectMap(List<V> ls, String keyName) {
		return toObjectMap(ls, keyName, true);
	}
	
	
	/**
	 * 将对象数据转换为Map形式
	 * 应用场景,如: 需要将人员列表转换为以人员ID为键的Map, List<SysOp>  ->  Map<Long, SysOp> key=opId
	 * @param ls 对象数据
	 * @param keyName 做为键的字段名
	 * @param keyType 做为键的字段类型
	 * @param ignoreKeyNull 当键字段为null时是否忽略, 如果不忽略则出现null值则抛出异常@BinaryException
	 * @return 不会返回null
	 */
	public static <K, V> Map<K, V> toObjectMap(List<V> ls, String keyName, boolean ignoreKeyNull) {
		Map<K, V> map = new HashMap<K, V>();
		if(ls!=null && ls.size()>0) {
			BMProxy<V> proxy = (BMProxy<V>) BMProxy.getInstance(ls.get(0).getClass());
			for(int i=0; i<ls.size(); i++) {
				V v = ls.get(i);
				proxy.replaceInnerObject(v);
				
				K key = (K)proxy.get(keyName);
				if(key == null) {
					if(!ignoreKeyNull) throw new BinaryException(" The array["+i+"]."+keyName + " is null! ");
					continue ;
				}
				map.put(key, v);
			}
		}
		return map;
	}
	
	
	
	/**
	 * 将对象数据转换为Map形式, 其中指定的map键可对应多个值, 相当于以键分组, 当键字段为null时则忽略
	 * 应用场景,如: 需要将人员列表转换为以组织ID为键的Map, List<SysOp>  ->  Map<Long, List<SysOp>> key=orgId
	 * @param ls 对象数据
	 * @param keyName 做为键的字段名
	 * @param keyType 做为键的字段类型
	 * @return 不会返回null, 不会出现map中的List为null或长度为0的情况
	 */
	public static <K, V> Map<K, List<V>> toObjectGroupMap(List<V> ls, String keyName) {
		return toObjectGroupMap(ls, keyName, true);
	}
	
	
	
	/**
	 * 将对象数据转换为Map形式, 其中指定的map键可对应多个值, 相当于以键分组
	 * 应用场景,如: 需要将人员列表转换为以组织ID为键的Map, List<SysOp>  ->  Map<Long, List<SysOp>> key=orgId
	 * @param ls 对象数据
	 * @param keyName 做为键的字段名
	 * @param keyType 做为键的字段类型
	 * @param ignoreKeyNull 当键字段为null时是否忽略, 如果不忽略则出现null值则抛出异常@BinaryException
	 * @return 不会返回null, 不会出现map中的List为null或长度为0的情况
	 */
	public static <K, V> Map<K, List<V>> toObjectGroupMap(List<V> ls, String keyName, boolean ignoreKeyNull) {
		Map<K, List<V>> map = new HashMap<K, List<V>>();
		if(ls!=null && ls.size()>0) {
			BMProxy<V> proxy = (BMProxy<V>) BMProxy.getInstance(ls.get(0).getClass());
			for(int i=0; i<ls.size(); i++) {
				V v = ls.get(i);
				proxy.replaceInnerObject(v);
				
				K key = (K)proxy.get(keyName);
				if(key == null) {
					if(!ignoreKeyNull) throw new BinaryException(" The array["+i+"]."+keyName + " is null! ");
					continue ;
				}
				
				List<V> arr = map.get(key);
				if(arr == null) {
					arr = new ArrayList<V>();
					map.put(key, arr);
				}
				arr.add(v);
			}
		}
		return map;
	}
	
	
}
