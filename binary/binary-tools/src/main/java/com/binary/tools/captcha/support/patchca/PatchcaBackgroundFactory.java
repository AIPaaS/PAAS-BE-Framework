package com.binary.tools.captcha.support.patchca;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;

import com.binary.core.util.BinaryUtils;

public class PatchcaBackgroundFactory implements BackgroundFactory {
	
	
	/** 干扰点个数 **/
	private int pointCount;
	
	/** 干扰点最小宽度 **/
	private int pointMinWidth = 1;
	
	/** 干扰点最大宽度 **/
	private int pointMaxWidth = 6;
	
	/** 干扰点最小高度 **/
	private int pointMinHeight = 1;
	
	/** 干扰点最大高度 **/
	private int pointMaxHeight = 6;
	
	
	
	
	
	/** 干扰线条数 **/
	private int lineCount;
	
	
	/** 点线颜色生成器 **/
	private ColorFactory colorFactory;
	
	
	
	public PatchcaBackgroundFactory(ColorFactory colorFactory) {
		this.pointCount = 20;
		this.lineCount = 3;
		this.colorFactory = colorFactory;
	}
	
	
	public int getPointCount() {
		return pointCount;
	}

	public void setPointCount(int pointCount) {
		this.pointCount = pointCount;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public ColorFactory getColorFactory() {
		return colorFactory;
	}

	public void setColorFactory(ColorFactory colorFactory) {
		BinaryUtils.checkNull(colorFactory, "colorFactory");
		this.colorFactory = colorFactory;
	}
	
	
	public int getPointMinWidth() {
		return pointMinWidth;
	}

	public void setPointMinWidth(int pointMinWidth) {
		this.pointMinWidth = pointMinWidth;
	}

	public int getPointMaxWidth() {
		return pointMaxWidth;
	}

	public void setPointMaxWidth(int pointMaxWidth) {
		this.pointMaxWidth = pointMaxWidth;
	}

	public int getPointMinHeight() {
		return pointMinHeight;
	}

	public void setPointMinHeight(int pointMinHeight) {
		this.pointMinHeight = pointMinHeight;
	}

	public int getPointMaxHeight() {
		return pointMaxHeight;
	}

	public void setPointMaxHeight(int pointMaxHeight) {
		this.pointMaxHeight = pointMaxHeight;
	}

	
	
	public void fillBackground(BufferedImage image) {
		Graphics graphics = image.getGraphics();

		//验证码图片的宽高
		int imgWidth = image.getWidth();
		int imgHeight = image.getHeight();
		
		//填充为白色背景
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imgWidth, imgHeight);
		
		//画干扰点
		for(int i=0; i<this.pointCount; i++) {
			Color color = this.colorFactory.getColor(i);
			int x = (int)BinaryUtils.random(imgWidth);
			int y = (int)BinaryUtils.random(imgHeight);
			
			int w = pointMinWidth + (pointMaxWidth>pointMinWidth ? (int)BinaryUtils.random(pointMaxWidth-pointMinWidth+1) : 0);
			int h = pointMinHeight + (pointMaxHeight>pointMinHeight ? (int)BinaryUtils.random(pointMaxHeight-pointMinHeight+1) : 0);
			
			int startAngle = (int)BinaryUtils.random(360);
			int arcAngle = (int)BinaryUtils.random(360);
			
			graphics.setColor(color);
			graphics.fillArc(x, y, w, h, startAngle, arcAngle);
		}
		
		//画干扰线
		for(int i=0; i<this.lineCount; i++) {
			int x1 = (int)BinaryUtils.random(imgWidth);
			int y1 = (int)BinaryUtils.random(imgHeight);
			int x2 = (int)BinaryUtils.random(imgWidth);
			int y2 = (int)BinaryUtils.random(imgHeight);
			graphics.drawLine(x1, y1, x2, y2);
		}
	}
}