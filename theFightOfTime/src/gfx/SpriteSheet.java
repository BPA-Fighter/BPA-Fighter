/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;
	
	/**
	 * @param sheet
	 * 		send image to be cropped
	 */
	
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	/**
	 * @param width
	 * 		get width of subimage
	 * @param height
	 * 		get height of subimage
	 * @param x
	 * 		x position of subimage
	 * @param y
	 * 		y position of subimage
	 * @return
	 * 		returns a BufferedImage to calling class
	 */
	
	public BufferedImage crop(int width, int height, int x, int y) {
            System.out.println("Int x: " + x + " Int y: " + y + " Int width: " + width + " Int height: " + height);
		return sheet.getSubimage(x, y, width, height);
	}

}
