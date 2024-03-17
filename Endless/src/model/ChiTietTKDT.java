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
public class ChiTietTKDT{
    private Date thoiGian;
    private Double tongSoHoaDon;
    private int slKhachHang;
    private int slSanPham;
    private Double doanhThu;

    public ChiTietTKDT() {
    }

    public ChiTietTKDT(Date thoiGian, Double tongSoHoaDon, int slKhachHang, int slSanPham, Double doanhThu) {
        this.thoiGian = thoiGian;
        this.tongSoHoaDon = tongSoHoaDon;
        this.slKhachHang = slKhachHang;
        this.slSanPham = slSanPham;
        this.doanhThu = doanhThu;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Double getTongSoHoaDon() {
        return tongSoHoaDon;
    }

    public void setTongSoHoaDon(Double tongSoHoaDon) {
        this.tongSoHoaDon = tongSoHoaDon;
    }

    public int getSlKhachHang() {
        return slKhachHang;
    }

    public void setSlKhachHang(int slKhachHang) {
        this.slKhachHang = slKhachHang;
    }

    public int getSlSanPham() {
        return slSanPham;
    }

    public void setSlSanPham(int slSanPham) {
        this.slSanPham = slSanPham;
    }

    public Double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Double doanhThu) {
        this.doanhThu = doanhThu;
    }
    
    
}
