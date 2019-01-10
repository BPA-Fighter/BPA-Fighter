/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	/**
	 * @param path
	 * 		file location of image
	 * @return
	 * 		returns a BufferedImage
     * @throws java.io.IOException
	 */
	public static BufferedImage loadImage(String path) {	
            
            if (path != "") {
            System.out.println("loadImage path: " + path);
            //return ImageIO.read(ImageLoader.class.getResource(path));
            } else {
                System.out.println("loadImage path: Empty");
                //return null;
            }
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
                
		return null;
	}
	
}
