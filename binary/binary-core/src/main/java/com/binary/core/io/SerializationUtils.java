package com.binary.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.binary.core.exception.SerializationException;
import com.binary.core.util.BinaryUtils;


/**
 * 序列化工具类
 * @author wanwb
 */
public abstract class SerializationUtils {

	
	
	/**
	 * 以序列化方式复制对象
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) {
        if (obj == null) return null;
        
        byte[] data = serialize(obj);
        return (T)deserialize(data);
    }
	
	
	
	
	/**
	 * 序列化对象, 将序列化数据转化成内存字节
	 * @param obj : 被序列化对象
	 * @return 序列化之后字节
	 */
	public static byte[] serialize(Object obj) {
		BinaryUtils.checkNull(obj, "object");
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(obj, baos);
        return baos.toByteArray();
	}
	
	
	
	/**
	 * 序列化对象, 将序列化数据输出至文件中
	 * @param obj : 被序列化对象
	 * @param file : 指定存放文件
	 */
	public static void serialize(Object obj, File outputFile) {
		BinaryUtils.checkNull(outputFile, "outputFile");
		
		FileOutputStream os = null;
		try {
			os = FileSystem.newFileOutputStream(outputFile);
			serialize(obj, os);
		}finally {
			try {
				if(os != null) os.close();
			}catch(Exception e) {
				throw BinaryUtils.transException(e, SerializationException.class);
			}
		}
	}
	
	
	
	/**
	 * 序列化对象, 将序列化数据输出至输出流中, 注: 此方法执行完之后不会关闭输出流
	 * @param obj : 被序列化对象
	 * @param os : 输出流
	 */
	public static void serialize(Object obj, OutputStream os) {
		BinaryUtils.checkNull(obj, "object");
		BinaryUtils.checkNull(os, "outputStream");
		
		if(!(os instanceof BufferedOutputStream)) os = new BufferedOutputStream(os);
		
        ObjectOutputStream oos = null;
        try {
        	oos = new ObjectOutputStream(os);
        	oos.writeObject(obj);
        	oos.flush();
        }catch(Exception e) {
			throw BinaryUtils.transException(e, SerializationException.class);
		}
	}
	
	
	
	
	
	/**
	 * 反序列化对象, 将被序列化字节转化为对象
	 * @param data : 被序列化字节
	 * @return
	 */
	public static Object deserialize(byte[] data) {
		BinaryUtils.checkEmpty(data, "data");
		ByteArrayInputStream baos = new ByteArrayInputStream(data);
        return deserialize(baos);
	}
	
	
	
	/**
	 * 反序列化对象, 将被序列化文件转化为对象
	 * @param inputFile : 将被序列化文件
	 * @return
	 */
	public static Object deserialize(File inputFile) {
		BinaryUtils.checkNull(inputFile, "inputFile");
		
		FileInputStream is = null;
		try {
			is = FileSystem.newFileInputStream(inputFile);
			return deserialize(is);
		}finally {
			try {
				if(is != null) is.close();
			}catch(Exception e) {
				throw BinaryUtils.transException(e, SerializationException.class);
			}
		}
	}
	
	
	/**
	 * 将序列化数据以输入流方式反序列化对象, 注: 此方法执行完之后不会自动关闭输入流
	 * @param is : 序列化数据输入流
	 * @return
	 */
	@SuppressWarnings("resource")
	public static Object deserialize(InputStream is) {
		BinaryUtils.checkNull(is, "inputStream");
		
		if(!(is instanceof BufferedInputStream)) is = new BufferedInputStream(is);
		
		Object obj = null;
        ObjectInputStream ois = null;
        try {
        	ois = new ObjectInputStream(is);
        	obj = ois.readObject();
        }catch(Exception e) {
			throw BinaryUtils.transException(e, SerializationException.class);
		}
        
        return obj;
	}
	

	
	
	
	/**
	 * 反序列化对象, 将被序列化字节转化为指定类型对象
	 * @param data : 被序列化字节
	 * @param type : 指定反序列化对象类型
	 * @return
	 */
	public static <T> T deserialize(byte[] data, Class<T> type) {
		Object obj = deserialize(data);
		return validateType(obj, type);
	}
	
	
	/**
	 * 反序列化对象, 将被序列化文件转化为对象
	 * @param inputFile : 将被序列化文件
	 * @param type : 指定反序列化对象类型
	 * @return
	 */
	public static <T> T deserialize(File inputFile, Class<T> type) {
		Object obj = deserialize(inputFile);
		return validateType(obj, type);
	}
	
	
	/**
	 * 将序列化数据以输入流方式反序列化对象, 注: 此方法执行完之后会自动关闭输入流
	 * @param is : 序列化数据输入流
	 * @param type : 指定反序列化对象类型
	 * @return
	 */
	public static <T> T deserialize(InputStream is, Class<T> type) {
		Object obj = deserialize(is);
		return validateType(obj, type);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private static <T> T validateType(Object obj, Class<T> type) {
		if(obj == null) return null;
		if(!type.isAssignableFrom(obj.getClass())) {
			throw new SerializationException(" the deserialize object ["+obj.getClass().getName()+"] is not typeof ["+type.getName()+"] ! ");
		}
		return (T)obj;
	}
	
	
	
	
}
