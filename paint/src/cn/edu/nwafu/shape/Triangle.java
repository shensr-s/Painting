package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * 定义一个Triangle类，继承Shape类，用于绘制一个三角形
 *
 * @author shensr
 */
public class Triangle extends AbstractShape {


    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        int[] x = {(x1 + x2) / 2, x1, x2};
        int[] y = {y1, y2, y2};
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawPolygon(x, y, 3);
    }
}