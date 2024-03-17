package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

public class MenuPanel extends JPanel {

    private Color defaultBackgroundColor = Color.BLACK;
    private Color hoverBackgroundColor = new Color(175,29,0);
    private Color clickBackgroundColor = new Color(0, 0, 0);

    public MenuPanel(int a, int b, Icon icon, String title) {
        setBackground(a == b ? hoverBackgroundColor : clickBackgroundColor);
        setOpaque(true);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); // Left-aligned with a gap of 15px
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.CENTER);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(new EmptyBorder(15, 10, 0, 0)); // Adjust the padding
        contentPanel.add(iconLabel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(15, 10, 0, 0));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(titleLabel);

        setPreferredSize(new Dimension(250, 55));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (a != b) {
                    SwingUtilities.invokeLater(() -> setBackground(new Color(190, 44, 0)));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (a != b) {
                    SwingUtilities.invokeLater(() -> setBackground(defaultBackgroundColor));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (a != b) {
                    SwingUtilities.invokeLater(() -> setBackground(new Color(175,29,0)));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (a != b) {
                    SwingUtilities.invokeLater(() -> setBackground(hoverBackgroundColor));
                }
            }
        });
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public void setClickBackgroundColor(Color clickBackgroundColor) {
        this.clickBackgroundColor = clickBackgroundColor;
    }
}
