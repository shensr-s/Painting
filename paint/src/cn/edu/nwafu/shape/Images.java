package cn.edu.nwafu.shape;

import java.awt.*;

public class Images extends Shape{


	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, board);
	}
	
	
}
