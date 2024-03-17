package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CustomGradientPanel extends JComponent {

    private Color colorStart;
    private Color colorEnd;
    private float[] fractions;
    private Color borderColor;
    private float borderThickness;

    public CustomGradientPanel() {
        // Default constructor
        this.colorStart = Color.RED; // Default start color
        this.colorEnd = Color.BLUE; // Default end color
        this.fractions = new float[]{0.0f, 1.0f}; // Default fractions
        this.borderColor = Color.BLACK; // Default border color
        this.borderThickness = 1.0f; // Default border thickness
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw the border
        g2d.setColor(borderColor);
        Stroke borderStroke = new BasicStroke(borderThickness);
        g2d.setStroke(borderStroke);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // Define the start and end points of the gradient
        Point2D start = new Point2D.Float(0, 0); // Start from the top-left corner
        Point2D end = new Point2D.Float(getWidth(), getHeight()); // End at the bottom-right corner

        // Create the linear gradient paint
        LinearGradientPaint gradientPaint = new LinearGradientPaint(start, end, fractions, new Color[]{colorStart, colorEnd});

        // Set the paint for the graphics context
        g2d.setPaint(gradientPaint);

        // Draw a filled rectangle with the linear gradient
        Rectangle2D rect = new Rectangle2D.Float(0, 0, getWidth(), getHeight());
        g2d.fill(rect);
    }

    public Color getColorStart() {
        return colorStart;
    }

    public void setColorStart(Color colorStart) {
        this.colorStart = colorStart;
        repaint();
    }

    public Color getColorEnd() {
        return colorEnd;
    }

    public void setColorEnd(Color colorEnd) {
        this.colorEnd = colorEnd;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public float getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(float borderThickness) {
        this.borderThickness = borderThickness;
        repaint();
    }
}
