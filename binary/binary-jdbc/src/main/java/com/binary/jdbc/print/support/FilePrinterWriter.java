package com.binary.jdbc.print.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.binary.jdbc.exception.PrinterException;
import com.binary.jdbc.print.PrinterWriterType;

public class FilePrinterWriter extends AbstractPrinterWriter {

	
	private File file;
	private String charset;
	
	private BufferedWriter bw;
	
	
	public FilePrinterWriter(String filePath) {
		this(new File(filePath), null);
	}
	public FilePrinterWriter(File file) {
		this(file, null);
	}
	
	public FilePrinterWriter(String filePath, String charset) {
		this(new File(filePath), charset);
	}
	public FilePrinterWriter(File file, String charset) {
		super(PrinterWriterType.FILE);
		this.file = file;
		this.charset = charset!=null&&(charset=charset.trim()).length()>0 ? charset : "UTF-8";
		try {
			this.bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			throw new PrinterException(e);
		}
	}
	
	
	@Override
	public void write(String msg) {
		try {
			this.bw.write(msg);
			this.bw.flush();
		} catch (IOException e) {
			throw new PrinterException(e);
		}
	}
	
	
	
	public File getFile() {
		return file;
	}
	public String getCharset() {
		return charset;
	}
	
	
	
	
	
	
	
}
