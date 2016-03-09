package com.binary.tools.captcha.support.patchca;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.patchca.color.ColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.filter.predefined.RippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;

import com.binary.core.util.BinaryUtils;
import com.binary.tools.exception.CaptchaException;



public class PatchcaFilterFactory implements FilterFactory {

	
	
	private List<FilterFactory> filterFactories;
	
	
	
	public PatchcaFilterFactory(ColorFactory colorFactory) {
		this.filterFactories = new ArrayList<FilterFactory>();
		//this.filterFactories.add(new DoubleRippleFilterFactory()); //双波纹  
//		this.filterFactories.add(new CurvesRippleFilterFactory(colorFactory));  //干扰线波纹
//		this.filterFactories.add(new DiffuseRippleFilterFactory());  ////漫波纹
//		this.filterFactories.add(new MarbleRippleFilterFactory()); //大理石波纹  
		//this.filterFactories.add(new WobbleRippleFilterFactory()); ////摆波纹  
		this.filterFactories.add(new RippleFilterFactory()); 
		
	}
	
	
	public PatchcaFilterFactory(List<FilterFactory> filterFactories) {
		this.setFilterFactories(filterFactories);
	}
	
	
	
	public List<FilterFactory> getFilterFactories() {
		return filterFactories;
	}


	public void setFilterFactories(List<FilterFactory> filterFactories) {
		if(filterFactories==null || filterFactories.size()==0) throw new CaptchaException(" the filterFactories is empty! ");
		this.filterFactories = filterFactories;
	}


	@Override
	public BufferedImage applyFilters(BufferedImage source) {
		int i = (int)BinaryUtils.random(this.filterFactories.size());
		FilterFactory factory = filterFactories.get(i);
		return factory.applyFilters(source);
	}

}
