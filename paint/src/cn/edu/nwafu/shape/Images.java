package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * @author shensr
 */
public class Images extends AbstractShape {


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, 0, 0, board);
    }


}
