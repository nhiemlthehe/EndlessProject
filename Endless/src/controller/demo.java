/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class demo extends JFrame {

    private JPanel menuPanel;
    private JButton toggleButton;
    private Timer slideTimer;

    private boolean menuVisible = false;
    private int menuWidth = 200; // Độ rộng của menu

    public demo() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        menuPanel = new MenuPanel2();
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setPreferredSize(new Dimension(menuWidth, getHeight()));

        toggleButton = new JButton("Toggle Menu");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu();
            }
        });

        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.WEST);
        add(toggleButton, BorderLayout.NORTH);

        slideTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMenuPosition();
            }
        });
    }

    private void toggleMenu() {
        slideTimer.stop();
        slideTimer.start();
    }

    private void updateMenuPosition() {
        int targetX;

        if (menuVisible) {
            // Nếu menu đang hiển thị, di chuyển về phải để ẩn đi
            targetX = -menuWidth;
        } else {
            // Nếu menu đang ẩn, di chuyển về trái để hiển thị
            targetX = 0;
        }

        int currentX = menuPanel.getX();

        if (currentX == targetX) {
            // Nếu đã đạt đến vị trí đích, dừng timer
            slideTimer.stop();
            menuVisible = !menuVisible; // Đảo ngược trạng thái của menu
        } else {
            // Di chuyển menu dựa trên vận tốc và hướng
            int direction = (int) Math.signum(targetX - currentX);
            int newX = currentX + direction * 5; // Vận tốc di chuyển

            // Đảm bảo menu không di chuyển quá vị trí đích
            if ((direction > 0 && newX > targetX) || (direction < 0 && newX < targetX)) {
                newX = targetX;
            }

            menuPanel.setLocation(newX, 0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new demo().setVisible(true);
            }
        });
    }
}
