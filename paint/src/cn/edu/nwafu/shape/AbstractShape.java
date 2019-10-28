package cn.edu.nwafu.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.JPanel;

/**
 * 抽象父类,所有图形类均要继承该类
 * @author shensr
 */
public abstract class AbstractShape implements Serializable {

    /**
     * 绘制图形的坐标
     */
    public int x1, y1, x2, y2;
    /**
     * 画笔颜色
     */
    public Color color;
    /**
     *画笔粗细
     */
    public int width;
    /**
     *形状
     */
    public int currentChoice;
    /**
     *铅笔或橡皮擦的笔迹长度
     */
    public int length;
    /**
     *存放待打开图片
     */
    public BufferedImage image;
    /**
     *绘画的画板
     */
    public JPanel board;
    /**
     *字体大小
     */
    public int  fontSize;
    /**
     *字体
     */
    public String fontName;
    /**
     *文本
     */
    public String s;
    /**
     *粗体
     */
    public int blodtype;
    /**
     *斜体
     */
    public int italic;

    /**
     * 绘制
     * @param g 画笔
     */

    public abstract void draw(Graphics2D g);

}