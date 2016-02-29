package com.binary.tools.captcha;

import java.util.ArrayList;
import java.util.List;

import com.binary.core.util.BinaryUtils;
import com.binary.tools.exception.CaptchaException;




/**
 * ImageTypeFactor
 * @author wanwb
 */
public class ImageTypeFactory {
	
	
	/**
	 * 图片类型
	 * String[]: [0]=文件格式	[1]=ContentType
	 */
	private List<ImageType> imageTypes;
	
	
	public ImageTypeFactory() {
		this.imageTypes = new ArrayList<ImageType>();
//		this.imageTypes.add(new ImageType("bmp", "image/bmp"));
		this.imageTypes.add(new ImageType("gif", "image/gif"));
//		this.imageTypes.add(new ImageType("jpeg", "image/jpeg"));
//		this.imageTypes.add(new ImageType("tiff", "image/tiff"));
		this.imageTypes.add(new ImageType("png", "image/png"));
	}
	
	public ImageTypeFactory(List<ImageType> imageTypes) {
		this.setImageTypes(imageTypes);
	}

	public List<ImageType> getImageTypes() {
		return imageTypes;
	}
	
	

	public void setImageTypes(List<ImageType> imageTypes) {
		if(imageTypes==null || imageTypes.size()==0) throw new CaptchaException(" the imageTypes is empty! ");
		this.imageTypes = imageTypes;
	}
	
	
	
	
	public ImageType getImageType() {
		int i = (int)BinaryUtils.random(this.imageTypes.size());
		return imageTypes.get(i);
	}
	
	
	

}




