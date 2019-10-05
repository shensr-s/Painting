package cn.edu.nwafu.start;

import cn.edu.nwafu.shape.Rectangle;
import cn.edu.nwafu.shape.Shape;
import cn.edu.nwafu.shape.*;
import cn.edu.nwafu.tools.ColorPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // 保存文件的标志
    public static int saved = 0;
    // private Container c;
    // 声明颜色属性，并赋默认值
    public static Color c = Color.black;

    // String oldName; // 原窗口名标记
    // 按钮属性，便于其他类访问
    public Graphics2D g;// 画笔
    public int lengthCount; // 铅笔或橡皮擦图形的存储长度
    // private JButton[] btn_paint;// 定义各种绘图的按钮
    public static String fontName = new String(" 宋体 ");
    private static int fSize = 16;
    private static int blodtype = Font.PLAIN;// 粗体,默认正常
    private static int italic = Font.PLAIN;// 斜体

    // private ImageIcon[] icon; // 存放按钮的图片

    public static int index = 0;// 图形形状的标记
    public static Shape[] itemList = new Shape[5000];// 图形存储单元
    private DrawPanel drawingArea; // 画图区域
    private JLabel statusBar;// 鼠标状态

    public static int stroke = 1;// 画笔粗细
    public static Color color = Color.black;// 用于存放当前颜色
    public static int currentChoice = 3; // 初始状态是画笔

    // 菜单类
    MyMenu menu;

    // 工具条
    MyToolbar myToolbar;

    // 调色板
    ColorPanel colorPanel;
    int length; // 铅笔或橡皮擦的笔迹长度

    public MyFrame(String s) {
        init(s);
        setVisible(true);

    }

    public MyFrame() {

    }

    public void init(String s) {
        this.setTitle(s);// 设置标题
        this.setSize(950, 600);// 设置窗口大小
        this.setLocationRelativeTo(null);// 居中显示

        // 添加菜单
        menu = new MyMenu();

        myToolbar = new MyToolbar();

        colorPanel = new ColorPanel();
        add(colorPanel, BorderLayout.WEST);

        // 设置窗体图标
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/themeicon.png"));
            Image image = imageIcon.getImage();
            this.setIconImage(image);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "图标异常");
        }
        // 创建各种基本图形的按钮
        drawingArea = new DrawPanel();
        this.add(drawingArea, BorderLayout.CENTER);
        statusBar = new JLabel();
        this.add(statusBar, BorderLayout.SOUTH);
        statusBar.setText("坐标");

        /**
         * 由于JLable是透明的，当我们把JLabel控件加载到JPanel控件之上时， 会发现JLabel的背景色总是和JPanel的背景色保持一致,
         */
        statusBar.setOpaque(true);// 设置该组件为透明
        statusBar.setBackground(new Color(195, 195, 195));
        drawingArea.createNewGraphics();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (saved == 0) {
                    int n = JOptionPane.showConfirmDialog(null, "您还没保存，确定要退出？", "提示", JOptionPane.OK_CANCEL_OPTION);
                    if (n == 0) {
                        System.exit(0);
                    }
                }
                if (saved == 1) {
                    System.exit(0);
                }
            }
        });

    }

    // 画图面板类，用来画图
    class DrawPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public DrawPanel() {
            // 设置光标类型，为十字形
            this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            //设置背景颜色
            this.setBackground(Color.white);
            //设置鼠标监听
            this.addMouseListener(new mouseAction());
            this.addMouseMotionListener(new MouseMOtion());
        }

        //重写paintComponent方法，使得画板每次刷新时可将之前的所有图形重新画出来。
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g; // 定义画板
            int j = 0;

            while (j <= index) {
                draw(g2d, itemList[j]);
                j++;

            }
        }

        void draw(Graphics2D g2d, Shape shape) {
            shape.draw(g2d); // 将画笔传入到各个子类中，用来完成各自的绘图
        }

        // 撤销操作的实现
        public void undo() {
            index--;
            if (index >= 0) {
                if (currentChoice == 3 || currentChoice == 16 || currentChoice == 17) {
                    index -= itemList[index].length;
                } else {
                    index--;
                }
                drawingArea.repaint();

            }
            index++;
            drawingArea.createNewGraphics();
        }

        // 新建一个画图基本单元对象的程序段
        public void createNewGraphics() {
            /**
             * MOVE_CURSOR:移动光标类型。 CROSSHAIR_CURSOR:十字光标 CUSTOM_CURSOR 制定类型 WAIT_CURSOR
             * 等待光标类型
             */

            if (currentChoice == 16) {
                try {
                    // 定义鼠标进入画板时的样式
                    String url = "/image/cursor.png"; // 储存鼠标图片的位置
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Image image = new ImageIcon(getClass().getResource(url)).getImage();
                    Cursor cursor = tk.createCustomCursor(image, new Point(10, 10), "norm");
                    drawingArea.setCursor(cursor);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "自定义光标异常");
                }

            } else if (currentChoice == 18) {
                drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            } else {
                drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));// 光标设置
            }

            switch (currentChoice) {
                case 0:
                    itemList[index] = new Images();
                    break;
                case 3:
                    itemList[index] = new Pencil();
                    break;
                case 4:
                    itemList[index] = new Line();
                    break;
                case 5:
                    itemList[index] = new Rectangle();
                    break;
                case 6:
                    itemList[index] = new FillRect();
                    break;
                case 7:
                    itemList[index] = new Oval();
                    break;
                case 8:
                    itemList[index] = new FillOval();
                    break;
                case 9:
                    itemList[index] = new Circle();
                    break;
                case 10:
                    itemList[index] = new FillCircle();
                    break;
                case 11:
                    itemList[index] = new RoundRect();
                    break;
                case 12:
                    itemList[index] = new FillRoundRect();
                    break;
                case 13:
                    itemList[index] = new Triangle();
                    break;
                case 14:
                    itemList[index] = new Pentagon();
                    break;
                case 15:
                    itemList[index] = new Hexagon();
                    break;
                case 16:
                    itemList[index] = new Rubber();
                    break;
                case 17:
                    itemList[index] = new Brush();
                    break;
                case 18:
                    itemList[index] = new Text();
                    String input;
                    input = JOptionPane.showInputDialog("请输入文字");
                    itemList[index].s = input;
                    itemList[index].fontSize = fSize;
                    itemList[index].fontName = fontName;
                    itemList[index].italic = italic;
                    itemList[index].blodtype = blodtype;
                    break;
            }
            itemList[index].color = color;
            itemList[index].width = stroke;

        }

        // 鼠标事件mouseAction类，继承了MouseAdapter，用来完成鼠标相应事件操作
        class mouseAction extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                statusBar.setText("坐标:[" + e.getX() + "," + e.getY() + "]像素");// 设置状态提示
                itemList[index].x1 = itemList[index].x2 = e.getX();
                itemList[index].y1 = itemList[index].y2 = e.getY();
                // 如果当前选择的图形是画笔或者橡皮檫，则进行下面的操作

                if (currentChoice == 3 || currentChoice == 16 || currentChoice == 17) {
                    lengthCount = 0;
                    itemList[index].x1 = itemList[index].x2 = e.getX();
                    itemList[index].y1 = itemList[index].y2 = e.getY();
                    index++;
                    lengthCount++;
                    createNewGraphics();
                }
            }

            public void mouseReleased(MouseEvent e) {
                statusBar.setText("坐标:[" + e.getX() + "," + e.getY() + "]像素");

                if (currentChoice == 3 || currentChoice == 16 || currentChoice == 17) {
                    itemList[index].x1 = e.getX();
                    itemList[index].y1 = e.getY();
                    lengthCount++;
                    itemList[index].length = lengthCount;
                }
                itemList[index].x2 = e.getX();
                itemList[index].y2 = e.getY();
                repaint();
                index++;
                createNewGraphics();
            }

            public void mouseEntered(MouseEvent e) {
                statusBar.setText("坐标:[" + e.getX() + "," + e.getY() + "]像素");
            }

            public void mouseExited(MouseEvent e) {
                statusBar.setText("坐标：");
            }
        }

        // 鼠标事件mouseMOtion类继承了MouseMotionAdapter,用来完成鼠标拖动和鼠标移动时的响应操作
        class MouseMOtion extends MouseMotionAdapter {
            public void mouseDragged(MouseEvent e) {
                statusBar.setText("坐标:[" + e.getX() + "," + e.getY() + "]像素");

                if (currentChoice == 3 || currentChoice == 16 || currentChoice == 17) {
                    itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = e.getX();
                    itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = e.getY();
                    index++;
                    lengthCount++;
                    createNewGraphics();
                } else {
                    itemList[index].x2 = e.getX();
                    itemList[index].y2 = e.getY();
                }
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
                statusBar.setText("坐标:[" + e.getX() + "," + e.getY() + "]像素");
            }
        }

    }

    // 保存图形文件程序

    class MyMenu {
        /**
         * 菜单初始化部分
         */

        private JMenuBar jMenuBar;// 菜单条
        private JMenuItem file_item_new, file_item_open, file_item_save, file_item_exit;// 定文件菜单的菜单项
        private JMenuItem set_item_color, set_item_undo;// 定设置菜单的菜单项
        private JMenuItem[] stroke_item;
        private JMenuItem help_item_info;
        private JMenuItem help_item_use;

        private JMenu file_menu, set_menu, help_menu, stroke_menu;// 定义文件、设置、帮助菜单
        private String strokes[] = {"/image/stroke1.png", "/image/stroke2.png", "/image/stroke3.png",
                "/image/stroke4.png"};

        public MyMenu() {
            addMenu();
        }

        public void addMenu() {

            jMenuBar = new JMenuBar();
            stroke_item = new JMenuItem[strokes.length];
            // 实例化菜单对象
            file_menu = new JMenu("文件");
            set_menu = new JMenu("设置");
            help_menu = new JMenu("帮助");
            stroke_menu = new JMenu("粗细");
            // 实例化菜单项,并通过ImageIcon对象添加图片
            file_item_new = new JMenuItem("新建", new ImageIcon(getClass().getResource("/image/new.png")));
            file_item_open = new JMenuItem("打开", new ImageIcon(getClass().getResource("/image/open.png")));
            file_item_save = new JMenuItem("保存", new ImageIcon(getClass().getResource("/image/save.png")));
            file_item_exit = new JMenuItem("退出", new ImageIcon(getClass().getResource("/image/exit.png")));
            set_item_color = new JMenuItem("颜色", new ImageIcon(getClass().getResource("/image/color.png")));
            set_item_undo = new JMenuItem("撤销", new ImageIcon(getClass().getResource("/image/undo.png")));
            help_item_use = new JMenuItem("使用手册");
            help_item_info = new JMenuItem("关于画图");
            for (int i = 0; i < 4; i++) {
                stroke_item[i] = new JMenuItem("", new ImageIcon(getClass().getResource(strokes[i])));
                stroke_menu.add(stroke_item[i]);
            }
            help_item_info.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null,
                            "" + "关于画图\n" + "****该软件由***开发完成****\n" + "****班级：计算机1602班     *****\n"
                                    + "****学号：00000000  *****\n" + "****邮箱：1111111@qq.com\n",
                            "关于画图", JOptionPane.PLAIN_MESSAGE);

                }
            });
            help_item_use.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "" + "##################\r\n" + "#画图软件使用说明书#\r\n"
                            + "####################\r\n" + "1.本软件可以实现以下功能：\r\n" + "（1）在画布上绘制直线、矩形、椭圆等图形\r\n"
                            + "（2）设置画笔的颜色和粗细\r\n" + "（3）绘制填充图形\r\n" + "（4）依据鼠标轨迹绘制曲线\r\n" + "（5）橡皮擦、保存图片\r\n"
                            + "2.本软件主要分为四个模块：菜单、工具栏、调色板、和画布\r\n" + "（1）菜单栏的文件子菜单包括打开、新建、保存图片以及退出程序，设置有快捷键，方便操作，\r\n"
                            + "	菜单栏的设置子菜单包括设置画笔的粗细和颜色；\r\n" + "（2）工具栏主要包括保存文件、清空画板、撤回操作、图形绘制和文字的绘制；\r\n"
                            + "（3）调色板位于界面的左侧，用于设置画笔的颜色，可以使用已设定的颜色，也可以自己选择系统提供的颜色；\r\n"
                            + "（4）画布用于图形绘制，使用鼠标选中要绘制的图形即可进行绘制。", "使用说明", JOptionPane.PLAIN_MESSAGE);

                }
            });
            help_menu.add(help_item_use);
            help_menu.add(help_item_info);
            // 设置快捷键
            file_item_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
            file_item_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            file_item_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
            file_item_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
            set_item_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
            // 添加粗细子菜单

            // 添加菜单项到菜单
            file_menu.add(file_item_new);
            file_menu.add(file_item_open);
            file_menu.add(file_item_save);
            file_menu.add(file_item_exit);
            set_menu.add(set_item_color);
            set_menu.add(set_item_undo);
            set_menu.add(stroke_menu);

            // 添加菜单到菜单条
            jMenuBar.add(file_menu);
            jMenuBar.add(set_menu);
            jMenuBar.add(help_menu);
            // 添加菜单条
            setJMenuBar(jMenuBar);

            // 给文件菜单设置监听
            file_item_new.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.newFile();
                }
            });
            file_item_save.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // 保存文件，并将标志符saved设置为1
                    menu.saveFile();
                    saved = 1;
                }
            });
            file_item_open.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // 打开文件，并将标志符saved设置为0
                    menu.openFile();
                    saved = 0;
                }
            });
            file_item_exit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // 如果文件已经保存就直接退出，若果文件没有保存，提示用户选择是否退出

                    if (saved == 1) {
                        System.exit(0);
                    } else {
                        int n = JOptionPane.showConfirmDialog(null, "您还没保存，确定要退出？", "提示", JOptionPane.OK_CANCEL_OPTION);
                        if (n == 0) {
                            System.exit(0);
                        }
                    }
                }
            });
            // 给设置菜单添加监听
            set_item_color.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // 设置粗细
                    ColorPanel.chooseColor();

                }
            });

            set_item_undo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // 撤销
                    drawingArea.undo();

                }
            });
            stroke_item[0].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    stroke = 1;
                    itemList[index].width = stroke;

                }
            });
            stroke_item[1].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    stroke = 5;
                    itemList[index].width = stroke;

                }
            });
            stroke_item[2].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    stroke = 15;
                    itemList[index].width = stroke;

                }
            });
            stroke_item[3].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    stroke = 25;
                    itemList[index].width = stroke;

                }
            });

        }

        // 保存图形文件
        public void saveFile() {
            // 文件选择器
            JFileChooser fileChooser = new JFileChooser();
            // 设置文件显示类型为仅显示文件
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // 文件过滤器
            JpgFilter jpg = new JpgFilter();
            BmpFilter bmp = new BmpFilter();
            PngFilter png = new PngFilter();
            GifFilter gif = new GifFilter();
            // 向用户可选择的文件过滤器列表添加一个过滤器。
            fileChooser.addChoosableFileFilter(jpg);
            fileChooser.addChoosableFileFilter(bmp);
            fileChooser.addChoosableFileFilter(png);
            fileChooser.addChoosableFileFilter(gif);
            // 返回当前的文本过滤器，并设置成当前的选择
            fileChooser.setFileFilter(fileChooser.getFileFilter());
            // 弹出一个 "Save File" 文件选择器对话框
            int result = fileChooser.showSaveDialog(MyFrame.this);
            if (result == JFileChooser.CANCEL_OPTION) {
                return;
            }
            File fileName = fileChooser.getSelectedFile();

            if (!fileName.getName().endsWith(fileChooser.getFileFilter().getDescription())) {
                String t = fileName.getPath() + fileChooser.getFileFilter().getDescription();
                fileName = new File(t);
            }
            fileName.canWrite();
            if (fileName == null || fileName.getName().equals("")) {
                JOptionPane.showMessageDialog(fileChooser, "无效的文件名", "无效的文件名", JOptionPane.ERROR_MESSAGE);
            }

            BufferedImage image = createImage(drawingArea);
            try {
                ImageIO.write(image, "png", fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 打开文件
        public void openFile() {
            // 文件选择器
            JFileChooser fileChooser = new JFileChooser();
            // 设置文件显示类型为仅显示文件
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // 文件过滤器
            JpgFilter jpg = new JpgFilter();
            BmpFilter bmp = new BmpFilter();
            PngFilter png = new PngFilter();
            GifFilter gif = new GifFilter();
            // 向用户可选择的文件过滤器列表添加一个过滤器。
            fileChooser.addChoosableFileFilter(jpg);
            fileChooser.addChoosableFileFilter(bmp);
            fileChooser.addChoosableFileFilter(png);
            fileChooser.addChoosableFileFilter(gif);
            // 返回当前的文本过滤器，并设置成当前的选择
            fileChooser.setFileFilter(fileChooser.getFileFilter());
            // 弹出一个 "Open File" 文件选择器对话框
            int result = fileChooser.showOpenDialog(MyFrame.this);
            if (result == JFileChooser.CANCEL_OPTION) {
                return;
            }
            // 得到选择文件的名字
            File fileName = fileChooser.getSelectedFile();
            if (!fileName.getName().endsWith(fileChooser.getFileFilter().getDescription())) {
                JOptionPane.showMessageDialog(MyFrame.this, "文件格式错误！");
                return;
            }
            fileName.canRead();

            if (fileName == null || fileName.getName().equals("")) {
                JOptionPane.showMessageDialog(fileChooser, "无效的文件名", "无效的文件名", JOptionPane.ERROR_MESSAGE);
            }

            BufferedImage image;

            try {
                index = 0;
                currentChoice = 0;
                image = ImageIO.read(fileName);
                drawingArea.createNewGraphics();
                itemList[index].image = image;
                itemList[index].board = drawingArea;
                drawingArea.repaint();
                index++;
                currentChoice = 3;
                drawingArea.createNewGraphics();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }

        //新建文件
        public void newFile() {
            index = 0;
            currentChoice = 3;
            color = Color.black;
            stroke = 1;
            drawingArea.createNewGraphics();
            repaint();
        }

        // 创建image，由saveFile方法调用
        // 将画板内容画到panelImage上
        public BufferedImage createImage(DrawPanel panel) {

            int width = MyFrame.this.getWidth();
            int height = MyFrame.this.getHeight();
            BufferedImage panelImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2D = (Graphics2D) panelImage.createGraphics();

            g2D.setColor(Color.WHITE);
            g2D.fillRect(0, 0, width, height);
            g2D.translate(0, 0);
            panel.paint(g2D);
            g2D.dispose();
            return panelImage;
        }

        // 文件过滤
        class JpgFilter extends FileFilter {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return f.getName().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return ".jpg";
            }

        }

        class BmpFilter extends FileFilter {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return f.getName().endsWith(".bmp");
            }

            @Override
            public String getDescription() {
                return ".bmp";
            }

        }

        class GifFilter extends FileFilter {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return f.getName().endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return ".gif";
            }

        }

        class PngFilter extends FileFilter {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return f.getName().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return ".png";
            }

        }

    }

    class MyToolbar {
        /**
         * 工具栏初始化部分
         */
        private ImageIcon[] icon; // 存放按钮的图片
        private JButton[] btn_paint;// 定义各种绘图的按钮
        private JComboBox<String> jfont;
        private JComboBox<String> jfont_size;
        private JToolBar toolbar; // 定义按钮面板
        private Checkbox btn_blod;// 粗体
        private Checkbox btn_italic;// 斜体
        // 将图片资源的相对路径存放于数组中，方便使用
        private String images[] = {"/image/save.png", "/image/refresh.png", "/image/undo.png", "/image/pencil.png",
                "/image/line.png", "/image/rectangle.png", "/image/rectangle3.png", "/image/oval.png",
                "/image/oval2.png", "/image/circle.png", "/image/fillcircle.png", "/image/rectangle2.png",
                "/image/rectangle4.png", "/image/triangle.png", "/image/pentagon.png", "/image/hexagon.png",
                "/image/eraser.png", "/image/brush.png", "/image/font.png",};
        private String tipText[] = {"保存", "清空", "撤销", "铅笔", "直线", "空心矩形", "填充矩形", "空心椭圆", "填充椭圆", "空心圆形", "填充圆形",
                "空心圆角矩形", "填充圆角矩形", "三角形", "五边形", "六边形", "橡皮擦", "填充", "文本", "粗细"};
        private String font[] = {"宋体", "隶书", "华文彩云", "仿宋_GB2312", "华文行楷", "方正舒体"};
        private String fontSize[] = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
                "48", "72"};

        public MyToolbar() {
            addToorbar();
        }

        public void addToorbar() {
            btn_paint = new JButton[images.length];// 定义指定个数的按钮
            toolbar = new JToolBar("工具栏");// 实例化一个水平的工具标签


            toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
            toolbar.setBackground(new Color(195, 195, 195));
            //中文会乱码
            btn_blod = new Checkbox("blod");//粗体
            btn_italic = new Checkbox("italic");//斜体
            btn_blod.setBackground(new Color(195, 195, 195));
            btn_italic.setBackground(new Color(195, 195, 195));
            btn_blod.setPreferredSize(new Dimension(45, 30));
            btn_italic.setPreferredSize(new Dimension(45, 30));

            jfont_size = new JComboBox<String>(fontSize);
            jfont_size.setPreferredSize(new Dimension(50, 30));
            jfont = new JComboBox<String>(font);
            jfont.setPreferredSize(new Dimension(100, 30));
            icon = new ImageIcon[images.length];

            // 设置按钮图标以及图片
            for (int i = 0; i < images.length; i++) {

                // System.out.println(images[i]);//测试
                btn_paint[i] = new JButton();
                icon[i] = new ImageIcon(getClass().getResource(images[i]));
                btn_paint[i].setIcon(icon[i]);
                btn_paint[i].setToolTipText(tipText[i]);
                btn_paint[i].setPreferredSize(new Dimension(28, 28));// 设置图标大小
                // btn_paint[i].setBorderPainted(false);// 去边框
                // btn_paint[i].setContentAreaFilled(false);
                btn_paint[i].setBackground(Color.WHITE);
                toolbar.add(btn_paint[i]);

            }

            toolbar.setFloatable(true);// 可以拖动
            // toolbar.addSeparator();
            // 字体格式：斜体，粗体
            toolbar.add(btn_italic);
            toolbar.add(btn_blod);

            // 将动作侦听器加入到按钮里面
            for (int i = 2; i < images.length; i++) {
                btn_paint[i].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        for (int j = 0; j < images.length; j++) {
                            // 如果按钮被点击。则设置相应的画笔
                            if (e.getSource() == btn_paint[j]) {
                                currentChoice = j;
                                // System.out.println(images[j]);
                                // System.out.println(j);// 测试 监听设置
                                drawingArea.createNewGraphics();
                                repaint();
                            }
                        }

                    }
                });
            }

            btn_paint[0].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.saveFile();
                    saved = 1;

                }
            });
            btn_paint[1].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    menu.newFile();

                }
            });
            btn_paint[2].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingArea.undo();

                }
            });

            // 添加监听
            btn_italic.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    italic = Font.ITALIC;

                }
            });
            btn_blod.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    blodtype = Font.BOLD;

                }
            });

            // 设置字体大小

            toolbar.add(jfont_size);
            jfont_size.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    fSize = Integer.parseInt(fontSize[jfont_size.getSelectedIndex()]);
                    // System.out.println(fSize);
                }
            });

            // 设置字体

            toolbar.add(jfont);
            jfont.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    fontName = font[jfont.getSelectedIndex()];
                    // System.out.println(fontName);
                }
            });

            MyFrame.this.add(toolbar, BorderLayout.NORTH);// 添加按钮面板到容器中

        }
    }

}

