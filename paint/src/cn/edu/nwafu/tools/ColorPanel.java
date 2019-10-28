package cn.edu.nwafu.tools;

import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

import cn.edu.nwafu.start.MyFrame;

/**
 * 调色板
 *
 * @author shensr
 */
public class ColorPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JButton bt;
    private static JButton bt1;

    /**
     * 调色板
     * 颜色数组，用来设置按钮的背景颜色
     */
    private Color[] colors = {new Color(255, 255, 255), new Color(0, 0, 0), new Color(127, 127, 127),
            new Color(195, 195, 195), new Color(136, 0, 21), new Color(185, 122, 87), new Color(237, 28, 36),
            new Color(255, 174, 201), new Color(255, 127, 39), new Color(255, 242, 0), new Color(239, 228, 176),
            new Color(34, 117, 76), new Color(181, 230, 29), new Color(0, 162, 232), new Color(153, 217, 234),
            new Color(63, 72, 204), new Color(112, 146, 190), new Color(163, 73, 164), new Color(200, 191, 231),
            new Color(89, 173, 154), new Color(8, 193, 194), new Color(9, 253, 76), new Color(153, 217, 234),
            new Color(199, 73, 4)};

    public ColorPanel() {
        addColorPanel();
    }

    private void addColorPanel() {
        // 主面板添加左边面板
        this.setPreferredSize(new Dimension(60, 60));
        this.setLayout(null);
        this.setBackground(new Color(195, 195, 195));
        // 左边面板添加子面板

        JPanel panelDownChild = new JPanel();
        panelDownChild.setBackground(Color.cyan);
        panelDownChild.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelDownChild.setBounds(10, 10, 40, 280);
        panelDownChild.setToolTipText("颜色");
        this.add(panelDownChild);



        // 按钮特效,简单的双线斜面边框
        BevelBorder bb = new BevelBorder(0, Color.gray, Color.white);
        BevelBorder bb1 = new BevelBorder(1, Color.gray, Color.white);

        JPanel left = new JPanel();
        left.setBackground(Color.white);
        left.setLayout(null);
        left.setBorder(bb);
        left.setPreferredSize(new Dimension(40, 40));

        // 左面板中的两个颜色按钮
        bt = new JButton();
        bt.setBounds(5, 5, 20, 20);
        bt.setBorder(bb);
        bt.setBackground(Color.black);
        bt.setSize(20, 20);
        bt1 = new JButton();
        bt1.setBorder(bb1);
        bt1.setBackground(Color.white);
        bt1.setBounds(15, 15, 20, 20);
        left.add(bt);
        left.add(bt1);

        // 右面板
        JPanel right = new JPanel();
        right.setBackground(new Color(195, 195, 195));
        right.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        right.setPreferredSize(new Dimension(60, 240));

        panelDownChild.add(left);
        panelDownChild.add(right);

        // 循环添加24个颜色按钮
        for (int i = 0; i < 24; i++) {
            JButton bt3 = new JButton();
            bt3.setOpaque(true);
            bt3.setBackground(colors[i]);
            bt3.setPreferredSize(new Dimension(20, 20));
            bt3.setBorder(bb);
            bt3.addActionListener(e -> {
                // 拿到被选中按钮的对象
                JButton jbt = (JButton) e.getSource();
                // 拿到被选中按钮的背景颜色
                Color c = jbt.getBackground();
                // 把背景颜色复制给WIndowStart中的颜色属性
                MyFrame.color = c;
                // 把左面板中的按钮颜色设置成选中按钮的背景颜色
                bt.setBackground(c);
                MyFrame.itemList[MyFrame.index].color = c;

            });
            right.add(bt3);

        }

        // 添加颜色窗口
        JButton btnColor = new JButton();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/color_48.png"));
        btnColor.setIcon(imageIcon);
        btnColor.setPreferredSize(new Dimension(40, 40));
        btnColor.setToolTipText("更多颜色");
        right.add(btnColor);
        btnColor.addActionListener(e -> chooseColor());
        bt.addActionListener(e -> {
            // 拿到被选中按钮的对象
            JButton jbt = (JButton) e.getSource();
            // 拿到被选中按钮的背景颜色
            Color c = jbt.getBackground();
            // 把背景颜色复制给WIndowStart中的颜色属性
            MyFrame.color = c;
            MyFrame.itemList[MyFrame.index].color = c;

        });
        bt1.addActionListener(e -> {
            // 拿到被选中按钮的对象
            JButton jbt = (JButton) e.getSource();
            // 拿到被选中按钮的背景颜色
            Color c = jbt.getBackground();
            MyFrame.color = c;
            MyFrame.itemList[MyFrame.index].color = c;

        });
    }

    /**
     * 选择当前颜色程序段
     */
    public static void chooseColor() {
        MyFrame.color = JColorChooser.showDialog(null, "请选择颜色", MyFrame.color);
        // 把左面板中的按钮颜色设置成选中按钮的背景颜色
        ColorPanel.bt.setBackground(MyFrame.color);
        MyFrame.itemList[MyFrame.index].color = MyFrame.color;

    }
}