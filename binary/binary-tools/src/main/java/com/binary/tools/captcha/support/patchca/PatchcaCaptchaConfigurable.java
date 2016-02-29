package com.binary.tools.captcha.support.patchca;

import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.font.FontFactory;
import org.patchca.service.AbstractCaptchaService;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.WordFactory;

import com.binary.core.util.BinaryUtils;
import com.binary.tools.captcha.ImageTypeFactory;



/**
 * 验证码配置项
 * @author wanwb
 */
public class PatchcaCaptchaConfigurable extends AbstractCaptchaService {
	
	private ImageTypeFactory imageTypeFactory;
	
	
	public PatchcaCaptchaConfigurable() {
		fontFactory = new PatchcaFontFactory();
		wordFactory = new PatchcaWordFactory();
		colorFactory = new PatchcaColorFactory();
		backgroundFactory = new PatchcaBackgroundFactory(colorFactory);
		textRenderer = new PatchcaTextRenderer();
		filterFactory = new PatchcaFilterFactory(colorFactory);
		width = 160;
		height = 60;
		imageTypeFactory = new ImageTypeFactory();
	}
	
	
	
	
	@Override
	public void setFontFactory(FontFactory fontFactory) {
		BinaryUtils.checkNull(fontFactory, "fontFactory");
		this.fontFactory = fontFactory;
	}

	@Override
	public void setWordFactory(WordFactory wordFactory) {
		BinaryUtils.checkNull(wordFactory, "wordFactory");
		this.wordFactory = wordFactory;
	}

	@Override
	public void setColorFactory(ColorFactory colorFactory) {
		BinaryUtils.checkNull(colorFactory, "colorFactory");
		this.colorFactory = colorFactory;
	}

	@Override
	public void setBackgroundFactory(BackgroundFactory backgroundFactory) {
		BinaryUtils.checkNull(backgroundFactory, "backgroundFactory");
		this.backgroundFactory = backgroundFactory;
	}

	@Override
	public void setTextRenderer(TextRenderer textRenderer) {
		BinaryUtils.checkNull(textRenderer, "textRenderer");
		this.textRenderer = textRenderer;
	}

	@Override
	public void setFilterFactory(FilterFactory filterFactory) {
		BinaryUtils.checkNull(filterFactory, "filterFactory");
		this.filterFactory = filterFactory;
	}
	
	
	
	
	
	public void setImageTypeFactory(ImageTypeFactory imageTypeFactory) {
		BinaryUtils.checkNull(imageTypeFactory, "imageTypeFactory");
		this.imageTypeFactory = imageTypeFactory;
	}
	
	
	public ImageTypeFactory getImageTypeFactory() {
		return imageTypeFactory;
	}


	
	
	
	
	
	
}
