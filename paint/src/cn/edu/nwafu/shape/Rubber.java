package cn.edu.nwafu.shape;

import java.awt.*;

/**
 * 橡皮檫类
 * @author shensr
 *
 */
public class Rubber extends AbstractShape {
	/**
	 * BasicStroke的几个字段
	 * CAP_SQUARE 	使用正方形结束未封闭的子路径和虚线线段，
	 * 				正方形越过线段端点，并延长等于线条宽度一半的距离。
	 * CAP_BUTT 	 无装饰地结束未封闭的子路径和虚线线段。
	 * CAP_ROUND	使用半径等于画笔宽度一半的圆形装饰结束未封闭的子路径和虚线线段。
	 * JOIN_BEVEL	通过直线连接宽体轮廓的外角，将路径线段连接在一起。
	 */
	public Rubber(){
		
	}
	@Override
	public void draw(Graphics2D g) {
		g.setPaint(Color.white);
		g.setStroke(new BasicStroke(20, BasicStroke.CAP_SQUARE , BasicStroke.JOIN_BEVEL));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawLine(x1, y1, x2, y2);
	}
}