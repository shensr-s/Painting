package cn.edu.nwafu.shape;

import java.awt.*;


public class Line extends Shape {

	public Line() {
		
	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		g.setStroke(new BasicStroke(width));
		g.drawLine(x1, y1, x2, y2);
	}
 
}
