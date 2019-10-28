package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * @author shensr
 */
public class Hexagon extends AbstractShape {


    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setStroke(new BasicStroke(width));
        int[] x = {Math.min(x1, x2) + Math.abs(x1 - x2) / 4, Math.min(x1, x2),
                Math.min(x1, x2) + Math.abs(x1 - x2) / 4, Math.max(x1, x2) - Math.abs(x2 - x1) / 4, Math.max(x1, x2),
                Math.max(x1, x2) - Math.abs(x2 - x1) / 4};
        int[] y = {Math.min(y1, y2), (y1 + y2) / 2, Math.max(y1, y2), Math.max(y1, y2), (y1 + y2) / 2,
                Math.min(y1, y2)};
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawPolygon(x, y, 6);
    }
}