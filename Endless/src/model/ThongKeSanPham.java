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
public class ThongKeSanPham {
    private String loaiHang;
    private String tenSP;
    private int tonKho;
    private int daBan;
    private Double doanhThu;

    public ThongKeSanPham() {
    }

    public ThongKeSanPham(String loaiHang, String tenSP, int tonKho, int daBan, Double doanhThu) {
        this.loaiHang = loaiHang;
        this.tenSP = tenSP;
        this.tonKho = tonKho;
        this.daBan = daBan;
        this.doanhThu = doanhThu;
    }

    public String getLoaiHang() {
        return loaiHang;
    }

    public void setLoaiHang(String loaiHang) {
        this.loaiHang = loaiHang;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public int getDaBan() {
        return daBan;
    }

    public void setDaBan(int daBan) {
        this.daBan = daBan;
    }

    public Double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Double doanhThu) {
        this.doanhThu = doanhThu;
    }
    
    
}
