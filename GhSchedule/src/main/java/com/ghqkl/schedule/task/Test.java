package com.ghqkl.schedule.task;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("\n\n");
		Test.createAsciipic("D:/images/1.jpg");
	}
	
	public static void createAsciipic(String path){
		String base = "@#&$%*o!;.";
		try {
			BufferedImage image = ImageIO.read(new File(path));
			for (int y = 0; y < image.getHeight(); y += 8) {
				for (int x = 0; x < image.getWidth(); x += 4) {
					int pixel = image.getRGB(x, y);
					int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b= pixel & 0xff;
					float gray = 0.299f * r + 0.578f * g + 0.114f * b;
					//index值越小色素越重，值越大色素越浅
					int index = Math.round(gray * (base.length() + 1) / 255);
					System.out.print(index >= base.length()? " " : String.valueOf(base.charAt(index)));
				}
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
}