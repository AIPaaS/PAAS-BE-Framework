package com.binary.tools.captcha.support.patchca;

import org.patchca.font.RandomFontFactory;



/**
 * 外部自定义FontFactory
 * 按常用字体随机生成
 * @author wanwb
 */
public class PatchcaFontFactory extends RandomFontFactory {

	
	
	public PatchcaFontFactory() {
		super();
		minSize = 28;
		maxSize = 32;
	}
	
	
	
}
