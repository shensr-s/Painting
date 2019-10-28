package cn.edu.nwafu.shape;

import java.awt.*;
import java.util.Random;

/**
 * 刷子类
 *
 * @author shensr
 */
public class Brush extends AbstractShape {

    private int[] fx = new int[100];
    private int[] fy = new int[100];

    public Brush() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            fx[i] = random.nextInt(16) - 16;
            fy[i] = random.nextInt(16) - 16;
            //System.out.println("("+fx[i]+","+fy[i]+")");
        }
    }

    @Override
    public void draw(Graphics2D g) {


        g.setPaint(color);
        g.setStroke(new BasicStroke(0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        for (int i = 0; i < 100; i++) {
            double d = (double) fx[i];
            double c = (double) fy[i];
            g.drawLine((int) (x1 + d * Math.sin(d)), (int) (y1 + c * Math.sin(c)), (int) (x2 + d * Math.sin(d)),
                    (int) (y2 + c * Math.sin(c)));
        }
    }
}