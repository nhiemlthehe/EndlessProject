/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class CapNhapGiaoDien {
    public static void main(String[] args) {
        // Lấy đối tượng Toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Lấy kích thước màn hình
        Dimension screenSize = toolkit.getScreenSize();

        // Hiển thị thông tin kích thước màn hình
        System.out.println("Width: " + screenSize.getWidth());
        System.out.println("Height: " + screenSize.getHeight());
    }
}
