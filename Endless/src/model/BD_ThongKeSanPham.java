/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class BD_ThongKeSanPham {
    private String tenSP;
    private int soLuong;
    private String hinhAnh;

    public BD_ThongKeSanPham() {
    }

    public BD_ThongKeSanPham(String tenSP, int soLuong, String hinhAnh) {
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    
}
