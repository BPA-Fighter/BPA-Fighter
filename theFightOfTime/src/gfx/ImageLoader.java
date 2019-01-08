/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageLoader {

	/**
	 * @param path
	 * 		file location of image
	 * @return
	 * 		returns a BufferedImage
	 */
	public static BufferedImage loadImage(String path) {	
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
