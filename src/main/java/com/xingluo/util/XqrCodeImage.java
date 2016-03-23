package com.xingluo.util;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;
/**
 * 二维码
 * 
 * @author Mounate Yan。
 * @version 1.0 
 */
public class XqrCodeImage implements QRCodeImage {

	BufferedImage bufferedImage;
	
	public XqrCodeImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	@Override
	public int getHeight() {
		return bufferedImage.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		return bufferedImage.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		return bufferedImage.getWidth();
	}

}
