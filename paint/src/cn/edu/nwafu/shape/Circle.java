package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * 圆
 * @author shensr
 */
public class Circle extends AbstractShape {


	@Override
	public void draw(Graphics2D g2d) {
		g2d.setPaint(color);
		g2d.setStroke(new BasicStroke(width));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
				Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
	}
}