package kr.co.gitech.storyz.common.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 *
 * 
 */
public class EasyImage {

	private BufferedImage buffer;

	public EasyImage(File file) throws IOException {
		buffer = ImageIO.read(file);
	}

	public EasyImage(URL url) throws IOException {
		buffer = ImageIO.read(url);
	}

	public EasyImage(InputStream stream) throws IOException {
		buffer = ImageIO.read(stream);
	}

	public EasyImage(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public int getWidth() {
		return buffer.getWidth();
	}

	public int getHeight() {
		return buffer.getHeight();
	}

	/**
	 * 제우스를 사용하는 경우, JEUSMain.xml 파일에 '-Djava.awt.headless=true' 없는 경우 IOException이 발생한다. <br/>
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public EasyImage resize(int width, int height) {
		EasyImage easyImage = null;
		try {
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = dest.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.drawImage(buffer, 0, 0, width, height, null);
			g.dispose();
			easyImage = new EasyImage(dest);
		} catch (Exception e) {
			System.err.println("Append '-Djava.awt.headless=true' on JEUSMain.xml");
			e.printStackTrace();
		}
		return easyImage;
	}

	public EasyImage resize(int width) {
		int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
		return resize(width, resizedHeight);
	}

	/**
	 * 제우스를 사용하는 경우, JEUSMain.xml 파일에 '-Djava.awt.headless=true' 없는 경우 IOException이 발생한다. <br/>
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public EasyImage crop(int x, int y, int width, int height) {
		BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = dest.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(buffer, 0, 0, width, height, x, y, x + width, y + height, null);
		g.dispose();
		return new EasyImage(dest);
	}

	public void writeTo(OutputStream stream, String formatName) throws IOException {
		ImageIO.write(buffer, formatName, stream);
	}

	public boolean isSuppoprtedImageFormat() {
		return buffer != null ? true : false;
	}

}
