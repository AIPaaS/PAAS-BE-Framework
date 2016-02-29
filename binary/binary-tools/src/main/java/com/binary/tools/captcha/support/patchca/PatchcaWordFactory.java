package com.binary.tools.captcha.support.patchca;

import org.patchca.word.WordFactory;

import com.binary.core.util.BinaryUtils;




/**
 * 外部自定义WordFactory
 * 按英文加数字随机生成
 * @author wanwb
 */
public class PatchcaWordFactory implements WordFactory {

	
	protected String characters;
	protected int minLength;
	protected int maxLength;
	
	
	public PatchcaWordFactory() {
		//去除英文字母: O、o、l
		this("ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz0123456789", 6, 6);
	}
	public PatchcaWordFactory(String characters, int minLength, int maxLength) {
		this.characters = characters.trim();
		BinaryUtils.checkEmpty(this.characters, "characters");
		
		this.minLength = minLength;
		this.maxLength = maxLength;
	}
	
	
	public void setCharacters(String characters) {
		BinaryUtils.checkEmpty(this.characters, "characters");
		this.characters = characters.trim();
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	
	
	@Override
	public String getNextWord() {
		int count = minLength + (maxLength>minLength ? (int)BinaryUtils.random(maxLength-minLength+1) : 0);
		char[] cs = new char[count];
		
		int csmaxlen = this.characters.length();
		for(int i=0; i<count; i++) {
			int index = (int)BinaryUtils.random(csmaxlen);
			cs[i] = this.characters.charAt(index);
		}
		
		return new String(cs);
	}
	
	
	
}



