package com.binary.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.binary.core.exception.FileSystemException;
import com.binary.core.lang.StringUtils;
import com.binary.core.util.BinaryUtils;

public class FileSystem {
	
	
	private FileSystem() {
	}
	
	/**
	 * 创建FileInputStream实例
	 * @param file
	 * @return
	 */
	public static FileInputStream newFileInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new FileSystemException(e);
		}
	}
	
	/**
	 * 创建FileOutputStream实例
	 * @param file
	 * @return
	 */
	public static FileOutputStream newFileOutputStream(File file) {
		return newFileOutputStream(file, false);
	}
	public static FileOutputStream newFileOutputStream(File file, boolean append) {
		try {
			return new FileOutputStream(file, append);
		} catch (FileNotFoundException e) {
			throw new FileSystemException(e);
		}
	}
	
	/**
	 * 复制，将输入流复制至输出流
	 * @param input
	 * @param output
	 */
	public static void copy(InputStream input, OutputStream output) {
		try {
			if(!(input instanceof BufferedInputStream)) input = new BufferedInputStream(input);
			if(!(output instanceof BufferedOutputStream)) output = new BufferedOutputStream(output);
			int n = -1;
			while((n=input.read()) != -1) output.write(n);
			output.flush();
		}catch(Exception e) {
			throw BinaryUtils.transException(e, FileSystemException.class);
		}
//		finally {
//			try {
//				if(output != null) output.close();
//			} catch (IOException e) {
//				throw new FileSystemException(e);
//			}
//			try {
//				if(input != null) input.close();
//			} catch (IOException e) {
//				throw new FileSystemException(e);
//			}
//		}
	}
	
	
	
	/**
	 * 复制，将输入流复制成文件
	 * @param input
	 * @param output
	 */
	public static void copy(InputStream input, File output) {
		if(output.isDirectory()) {
			throw new FileSystemException(" the output file:'"+output.getPath()+"' is directory! ");
		}
		
		FileOutputStream os = null;
		try {
			os = newFileOutputStream(output);
			copy(input, os);
		}finally {
			try {
				if(os != null) os.close();
			}catch(Exception e) {
				throw BinaryUtils.transException(e, FileSystemException.class);
			}
		}
	}
	
	
	/**
	 * 复制，将文件复制成文件输出流
	 * @param input
	 * @param output
	 */
	public static void copy(File input, OutputStream output) {
		if(input.isDirectory()) {
			throw new FileSystemException(" the input file:'"+input.getPath()+"' is directory! ");
		}
		
		FileInputStream is = null;
		try {
			is = newFileInputStream(input);
			copy(is, output);
		}finally {
			try {
				if(is != null) is.close();
			}catch(Exception e) {
				throw BinaryUtils.transException(e, FileSystemException.class);
			}
		}
	}
	
	
	/**
	 * 如果input为文件、output为文件，则为文件复制
	 * 如果input为文件、output为目录，则为将文件input复制至output目录之下
	 * 如果input为目录、output为文件，则抛FileSystemException
	 * 如果input为目录、output为目录，则为将目录input(包括所有文件)全部考至output目录之下
	 * @param input
	 * @param output
	 */
	public static void copy(File input, File output) {
		if(output.isDirectory()) {
			copy2Directory(input, output);
		}else {
			if(input.isDirectory()) {
				throw new FileSystemException(" the input file:'"+input.getPath()+"' is directory! ");
			}else {
				FileInputStream is = null;
				FileOutputStream os = null;
				try {
					is = newFileInputStream(input);
					os = newFileOutputStream(output);
					copy(is, os);
				}finally {
					Exception ee = null;
					try {
						if(os != null) os.close();
					}catch(Exception e) {
						ee = e;
					}
					try {
						if(is != null) is.close();
					}catch(Exception e) {
						ee = e;
					}
					if(ee != null) throw BinaryUtils.transException(ee, FileSystemException.class);
				}
			}
		}
	}
	
	
	private static void copy2Directory(File input, File dir) {
		if(!dir.exists()) dir.mkdirs();
		if(input.isDirectory()) {
			File pastedir = new File(dir, input.getName());
			if(!pastedir.mkdir()) throw new FileSystemException(" mk dir '"+pastedir.getPath()+"' error!");
			File[] subs = input.listFiles();
			for(int i=0; i<subs.length; i++) {
				copy2Directory(subs[i], pastedir);
			}			
		}else {
			File output = new File(dir, input.getName());
			FileInputStream is = null;
			FileOutputStream os = null;
			try {
				is = newFileInputStream(input);
				os = newFileOutputStream(output);
				copy(is, os);
			}finally {
				Exception ee = null;
				try {
					if(os != null) os.close();
				}catch(Exception e) {
					ee = e;
				}
				try {
					if(is != null) is.close();
				}catch(Exception e) {
					ee = e;
				}
				if(ee != null) throw BinaryUtils.transException(ee, FileSystemException.class);
			}
		}
	}
	
	
	
	/**
	 * 删除文件
	 * @param file: 如果是目录则连带所有子文件一并删除
	 */
	public static void delete(File file) {
		delete(file, true);
	}
	
	
	/**
	 * 清空目录中所有文件，保留目录
	 * @param directory
	 */
	public static void clear(File directory) {
		delete(directory, false);
	}
	
	private static void delete(File file, boolean deldir) {
		if(file.isDirectory()) {
			File[] subs = file.listFiles();
			for(int i=0; i<subs.length; i++) {
				delete(subs[i], deldir);
			}
			if(deldir)file.delete();
		}else {
			file.delete();
		}
	}
	
	
	/**
	 * 从输入流中读取字符
	 * @param input
	 * @return
	 */
	public static String read(InputStream input) {
		return read(input, null);
	}
	public static String read(InputStream input, String encode) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(input, baos);
		try {
			return StringUtils.isEmpty(encode) ? baos.toString() : baos.toString(encode);
		} catch (UnsupportedEncodingException e) {
			throw new FileSystemException(e);
		}
	}
	
	/**
	 * 读取文件内容
	 * @param input
	 * @return
	 */
	public static String read(File input) {
		return read(input, null);
	}
	public static String read(File input, String encode) {
		FileInputStream is = null;
		try {
			is = newFileInputStream(input);
			return read(is, encode);
		}finally {
			try {
				if(is != null) is.close();
			} catch (Exception e) {
				throw BinaryUtils.transException(e, FileSystemException.class);
			}
		}
	}
	
	
	public static void write(OutputStream output, String value) {
		write(output, value, null);
	}
	public static void write(OutputStream output, String value, String encode) {
		byte[] bytes = null;
		try {
			bytes = StringUtils.isEmpty(encode) ? value.getBytes() : value.getBytes(encode);
		} catch (UnsupportedEncodingException e) {
			throw new FileSystemException(e);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		copy(bais, output);
	}
	public static void write(File output, String value) {
		write(output, value, null, false);
	}
	public static void write(File output, String value, String encode) {
		write(output, value, encode, false);
	}
	public static void write(File output, String value, boolean append) {
		write(output, value, null, append);
	}
	public static void write(File output, String value, String encode, boolean append) {
		FileOutputStream fos = null;
		try {
			fos = newFileOutputStream(output, append);
			write(fos, value, encode);
		}finally {
			try {
				if(fos != null) fos.close();
			} catch (Exception e) {
				throw BinaryUtils.transException(e, FileSystemException.class);
			}
		}
		
	}
	
	/**
	 * 获取目录下所有子文件
	 * @param dir
	 * @return
	 */
	public static List<File> getAllFiles(File dir) {
		return getAllFiles(dir, null);
	}
	
	/**
	 * 按文件名正则匹配获取目录下所有子文件
	 * @param dir
	 * @param nameregex
	 * @return
	 */
	public static List<File> getAllFiles(File dir, String nameregex) {
		List<File> files = new ArrayList<File>();
		if(dir.isDirectory()) getAllFiles(files, dir, nameregex);
		return files;
	}
	
	private static void getAllFiles(List<File> files, File dir, String nameregex) {
		File[] subs = dir.listFiles();
		if(subs==null || subs.length==0) return ;
		
		for(int i=0; i<subs.length; i++) {
			File sub = subs[i];
			
			if(sub.isDirectory()) {
				getAllFiles(files, sub, nameregex);
			}else {
				if(nameregex != null) {
					String name = sub.getName();
					if(!name.matches(nameregex)) continue ;
				}
				
				files.add(sub);
			}
		}
	}
	
	
	
	/**
	 * 按目录名正则匹配获取当前目录下所有子目录, (不包含当前目录)
	 * @param dir
	 * @return
	 */
	public static List<File> getAllDirectorys(File dir) {
		return getAllDirectorys(dir, null);
	}
	
	
	/**
	 * 获取当前目录下所有子目录, (不包含当前目录)
	 * @param dir
	 * @return
	 */
	public static List<File> getAllDirectorys(File dir, String nameregex) {
		List<File> dirs = new ArrayList<File>();
		if(dir.isDirectory()) getAllDirectorys(dirs, dir, nameregex);
		return dirs;
	}
	
	
	private static void getAllDirectorys(List<File> dirs, File dir, String nameregex) {
		File[] subs = dir.listFiles();
		if(subs==null || subs.length==0) return ;
		
		for(int i=0; i<subs.length; i++) {
			File sub = subs[i];
			
			if(sub.isDirectory()) {
				getAllDirectorys(dirs, sub, nameregex);
				
				if(nameregex != null) {
					String name = sub.getName();
					if(!name.matches(nameregex)) continue ;
				}
				
				dirs.add(sub);
			}
		}
	}
	
	
	
	
	
	/**
	 * 获取目录下所有子文件和子目录
	 * @param dir
	 * @return
	 */
	public static List<File> getAllSubs(File dir) {
		return getAllSubs(dir, null);
	}
	
	/**
	 * 按文件名正则匹配获取目录下所有子文件和子目录
	 * @param dir
	 * @param nameregex
	 * @return
	 */
	public static List<File> getAllSubs(File dir, String nameregex) {
		List<File> files = new ArrayList<File>();
		if(dir.isDirectory()) getAllSubs(files, dir, nameregex);
		return files;
	}
	
	private static void getAllSubs(List<File> files, File dir, String nameregex) {
		File[] subs = dir.listFiles();
		if(subs==null || subs.length==0) return ;
		
		for(int i=0; i<subs.length; i++) {
			File sub = subs[i];
			if(sub.isDirectory()) getAllSubs(files, sub, nameregex);
			
			if(nameregex != null) {
				String name = sub.getName();
				if(!name.matches(nameregex)) continue ;
			}
			
			files.add(sub);
		}
	}
	
	
}



