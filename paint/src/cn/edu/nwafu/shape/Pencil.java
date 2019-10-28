package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * @author shensr
 */
public class Pencil extends AbstractShape {


    public Pencil() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(color);

        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawLine(x1, y1, x2, y2);
    }
}


