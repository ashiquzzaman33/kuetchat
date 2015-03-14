package com.n.utility.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UtilityMethod {
	public static final ImageIcon getImageIcon(String src) {
		return new ImageIcon(UtilityMethod.class.getResource(src));
	}

	public static final Image getImage(String src) {
		return UtilityMethod.getImageIcon(src).getImage();
	}

	public static final ImageIcon getImageIconFormFile(String path) {
		File file = new File(path);
		boolean isValid = false;
		String[] suffixStrings = ImageIO.getReaderFileSuffixes();
		if (file.exists() && file.isFile()) {
			for (String string : suffixStrings) {
				if (file.getName().endsWith(string)) {
					isValid = true;
					break;
				}
			}
		}
		if (isValid)
			try {
				return new ImageIcon(ImageIO.read(file));
			} catch (IOException e) {
				return null;
			}
		else {
			return null;
		}
	}

	public static void saveImageToFile(File imageFile, String path,
			String imageName) throws IOException {
		BufferedImage image = ImageIO.read(imageFile);
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		file = new File(path + "\\" + imageName);

		ImageIO.write(image, "png", file);
	}

	public static void saveImageToFile(ImageIcon imageIcon, String path,
			String imageName) throws IOException {
		imageName = imageName.toLowerCase();
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		file = new File(path + "\\" + imageName + ".png");

		BufferedImage bi = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		// paint the Icon to the BufferedImage.
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		ImageIO.write(bi, "png", file);
	}

	public static void saveImageToFile(BufferedImage image, String path,
			String imageName) throws IOException {
		imageName = imageName.toLowerCase();
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		file = new File(path + "\\" + imageName + ".png");
		ImageIO.write(image, "png", file);

	}

}
