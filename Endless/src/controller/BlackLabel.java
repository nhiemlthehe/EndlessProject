package controller;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import javax.swing.JLabel;

public class BlackLabel extends JLabel {

    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private int radius = 0;

    public BlackLabel() {
        setColor(Color.BLACK);
        colorOver = new Color(0, 0, 0);
        colorClick = new Color(23, 23, 23);

        setLayout(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setLabelColor(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setLabelColor(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setLabelColor(colorClick);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (over) {
                    setLabelColor(colorOver);
                } else {
                    setLabelColor(color);
                }
            }
        });
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setLabelColor(color);
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private void setLabelColor(Color bgColor) {
        setBackground(bgColor);
        repaint();
    }


  @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ đường viền xung quanh với màu xám để tạo hiệu ứng bóng đổ
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(192, 192, 192)); // Màu xám
        g2d.setStroke(new BasicStroke(3)); // Độ rộng của đường viền
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.dispose();
    }

}