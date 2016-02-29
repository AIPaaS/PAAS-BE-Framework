package com.binary.tools.captcha.support.patchca;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.patchca.service.AbstractCaptchaService;
import org.patchca.service.Captcha;

import com.binary.core.util.BinaryUtils;
import com.binary.tools.captcha.CaptchaImage;
import com.binary.tools.captcha.ImageType;
import com.binary.tools.exception.CaptchaException;


public class PatchcaCaptcha implements com.binary.tools.captcha.Captcha {

	
	private PatchcaCaptchaConfigurable configurable;
	
	
	
	public PatchcaCaptcha() {
		this.configurable = new PatchcaCaptchaConfigurable();
	}
	public PatchcaCaptcha(PatchcaCaptchaConfigurable configurable) {
		this.setConfigurable(configurable);
	}
	
	
	public AbstractCaptchaService getConfigurable() {
		return configurable;
	}
	public void setConfigurable(PatchcaCaptchaConfigurable configurable) {
		BinaryUtils.checkNull(configurable, "configurable");
		this.configurable = configurable;
	}
	
	
	
	
	@Override
	public CaptchaImage drawImage() {
		try {
			Captcha captcha = configurable.getCaptcha(); 
			
			String code = captcha.getChallenge();
			BufferedImage image = captcha.getImage();
			
			ImageType imageType = configurable.getImageTypeFactory().getImageType();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, imageType.getFormatName(), os);
			
			return new CaptchaImage(code, os.toByteArray(), imageType);
		}catch(Exception e) {
			throw BinaryUtils.transException(e, CaptchaException.class, " create captcha-image error! ");
		}
	}
	
	
	
	
	
}
