package com.binary.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.binary.core.exception.CompressionException;
import com.binary.core.util.BinaryUtils;


/**
 * 压缩工具
 * @author wanwb
 */
public abstract class Compression {
	
	
	
	private static class FileInfo {
		String name;
		File file;
		FileInfo(String name, File file) {
			this.name = name;
			this.file = file;
		}
	}
	
	
	
	/**
	 * 压缩文件
	 * @param input : 待压缩文件,可以是目录
	 * @param output : 压缩之后文件, 只能是文件
	 */
	public static void compressZip(File input, File output) {
		compressZip(input, output, null);
	}
	
	
	/**
	 * 压缩文件
	 * @param input : 待压缩文件,可以是目录
	 * @param output : 压缩之后文件, 只能是文件
	 * @param charset : 指定命名字符集
	 */
	public static void compressZip(File input, File output, String charset) {
		if(!input.exists()) throw new CompressionException(" file is not exists '"+input.getPath()+"'! ");
		if(BinaryUtils.isEmpty(charset)) charset = "GBK";
		
		List<FileInfo> files = new ArrayList<FileInfo>();
		getAllFiles(input, files, "");
		
		try {
			ZipOutputStream zos = null;
			try {
				zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(output)), Charset.forName(charset));
				
				for(int i=0; i<files.size(); i++) {
					FileInfo info = files.get(i);
					
					ZipEntry entry = new ZipEntry(info.name);
					zos.putNextEntry(entry);
					
					if(info.file.isFile()) {
						BufferedInputStream bis = null;
						try {
							bis = new BufferedInputStream(new FileInputStream(info.file));
							int n = -1;
							while((n=bis.read()) != -1) {
								zos.write(n);
							}
						}finally {
							if(bis != null) bis.close();
						}
					}
				}				
			}finally {
				if(zos != null) zos.close();
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, CompressionException.class);
		}
	}
	
	
	private static void getAllFiles(File input, List<FileInfo> files, String parentName) {
		if(input.isDirectory()) {
			String name = parentName+input.getName() + "/";
			files.add(new FileInfo(name, input));
			
			File[] subs = input.listFiles();
			if(subs!=null && subs.length>0) {
				for(int i=0; i<subs.length; i++) {
					getAllFiles(subs[i], files, name);
				}
			}
		}else {
			files.add(new FileInfo(parentName+input.getName(), input));
		}
	}
	
	
	
	
	/**
	 * 解压文件
	 * @param input : 待解压文件, 只能是文件
	 * @param dir : 解压之后所存放的目录, 只能是目录
	 */
	public static void uncompressZip(File input, File dir) {
		uncompressZip(input, dir, null);
	}
	
	
	
	
	/**
	 * 解压文件
	 * @param input : 待解压文件, 只能是文件
	 * @param dir : 解压之后所存放的目录, 只能是目录
	 * @param charset : 指定命名字符集
	 */
	public static void uncompressZip(File input, File dir, String charset) {
		if(!input.isFile()) throw new CompressionException(" is not file '"+input.getPath()+"'! ");
		if(!dir.isDirectory() && !dir.mkdirs()) throw new CompressionException(" is not directory '"+dir.getPath()+"'! ");
		if(BinaryUtils.isEmpty(charset)) charset = "GBK";
		
		try {
			ZipFile zip = new ZipFile(input, Charset.forName(charset));
			try {
				Enumeration<? extends ZipEntry> itor = zip.entries();
				while(itor.hasMoreElements()) {
					ZipEntry e = itor.nextElement();
					
					String name = e.getName();
					File file = new File(dir, name);
					
					//压缩文件中是目录
					if(e.isDirectory()) {
						if(!file.isDirectory() && !file.mkdirs()) {
							throw new CompressionException(" is not directory '"+file.getPath()+"'! ");
						}
					}else {
						if(file.isDirectory()) throw new CompressionException(" is directory '"+file.getPath()+"'! ");
						File parent = file.getParentFile();
						if(!parent.isDirectory() && !parent.mkdirs()) {
							throw new CompressionException(" is not directory '"+parent.getPath()+"'! ");
						}
						
						BufferedInputStream bis = null;
						BufferedOutputStream bos = null;
						try {
							bis = new BufferedInputStream(zip.getInputStream(e));
							bos = new BufferedOutputStream(new FileOutputStream(file));
							int n = -1;
							while((n=bis.read()) != -1) {
								bos.write(n);
							}
						}finally {
							if(bos != null) bos.close();
							if(bis != null) bis.close();
						}
					}
				}
			}finally {
				if(zip != null) zip.close();
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, CompressionException.class);
		}
		
	}
	
	
	
	
	/**
	 * 压缩文件
	 * @param input : 待压缩文件, 只能是文件
	 * @param output : 压缩之后文件, 只能是文件
	 */
	public static void compressGZip(File input, File output) {
		if(!input.isFile()) throw new CompressionException(" is not file '"+input.getPath()+"'! ");
		File outputParent = output.getParentFile();
		if(!outputParent.isDirectory() && !outputParent.mkdirs()) {
			throw new CompressionException(" is not directory '"+outputParent.getPath()+"'! ");
		}
		
		try {
			BufferedInputStream bis = null;
			GZIPOutputStream gzos = null;
			try {
				bis = new BufferedInputStream(new FileInputStream(input));
				gzos = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
				int n = -1;
				while((n=bis.read()) != -1) {
					gzos.write(n);
				}
			}finally {
				if(gzos != null) gzos.close();
				if(bis != null) bis.close();
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, CompressionException.class);
		}
	}
	
	
	
	/**
	 * 解压文件
	 * @param input : 待解压文件, 只能是文件
	 * @param output : 解压之后所存放的文件, 只能是文件
	 */
	public static void uncompressGZip(File input, File output) {
		if(!input.isFile()) throw new CompressionException(" is not file '"+input.getPath()+"'! ");
		File outputParent = output.getParentFile();
		if(!outputParent.isDirectory() && !outputParent.mkdirs()) {
			throw new CompressionException(" is not directory '"+outputParent.getPath()+"'! ");
		}
		
		try {
			GZIPInputStream gzis = null;
			BufferedOutputStream bos = null;
			try {
				gzis = new GZIPInputStream(new BufferedInputStream(new FileInputStream(input)));
				bos = new BufferedOutputStream(new FileOutputStream(output));
				int n = -1;
				while((n=gzis.read()) != -1) {
					bos.write(n);
				}
			}finally {
				if(bos != null) bos.close();
				if(gzis != null) gzis.close();
			}
		}catch(Exception e) {
			throw BinaryUtils.transException(e, CompressionException.class);
		}
	}
	
	
	
}
