package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * @author shensr
 */
public class RoundRect extends AbstractShape {
    /**
     * 定义一个RoundRectangle类，继承Shape类，用于绘制一个圆角矩形
     */

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setStroke(new BasicStroke(width));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
    }
}