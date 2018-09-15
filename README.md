# Painting
This is a drawing software written in the Java language. The following is the specific design requirments and ideas.
画图软件
一、需求
利用面向对象的思想，设计并实现一个画图软件。实现基本的图形绘制功能、文本绘制功能、橡皮檫功能、撤销功能以及图片的存取功能，画图软件具有美观的用户界面。
使用户可以绘制直线、曲线、矩形、圆、三角形、五边形、六边形和椭圆等基本图形，可以设置画笔的粗细和颜色，以及绘制填充图形，文件读取最终实现jpg、bmp、png
和gif格式的存取。

二、总体设计
通过分析，画图软件主界面主要分为菜单栏、工具栏、调色板、画板和状态栏五个部分，将主界面设计为一个MyFrame类，将主界面上的菜单栏、工具栏、调色板等均分装成
一个类，完成各自的功能。将菜单栏分装成MyMenu类，主要实现图片的打开、保存和新建功能，画笔颜色粗细设置功能，以及软件的使用说明；将工具栏封装成MyToolbar
类，主要实现图形绘制按钮，文本绘制、文本字体大小和字体风格的设置，同时添加保存文件、清空画板和撤销功能的快捷按钮；将调色板封装成ColorPanel类，主要用来
设置画笔的颜色，可以使用设计好的16种颜色，也可以通过JColorChooser（颜色选择器）选择更多的颜色；将画板封装成DrawPanel类，主要实现图形以及文本的绘制；
状态栏作为MyFrame类的成员变量，用于显示鼠标当前的位置。
对于图形绘制，设计一个抽象Shape类，包含图形绘制的基本属性值和一个抽象draw(Graphics2D g)方法；其他图形类对象均继承Shape类，并实现抽象方法。
程序一共设计com.edu.nwafu.shape、com.edu.nwafu.start和com.edu.nwafu.tool三个包，分别存放图形类对象、主界面和调色板。



