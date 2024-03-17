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
public class HoaDon {
    private String MaHD;
    private String MaKH;
    private String MaNV;
    private Date NgayBan;
    private float TongTien;
    private String HTThanhToan;
    private float TienThanhToan;

    public HoaDon() {
    }

    public HoaDon(String MaHD, String MaKH, String MaNV, Date NgayBan, float TongTien, String HTThanhToan, float TienThanhToan) {
        this.MaHD = MaHD;
        this.MaKH = MaKH;
        this.MaNV = MaNV;
        this.NgayBan = NgayBan;
        this.TongTien = TongTien;
        this.HTThanhToan = HTThanhToan;
        this.TienThanhToan = TienThanhToan;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(Date NgayBan) {
        this.NgayBan = NgayBan;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }

    public String getHTThanhToan() {
        return HTThanhToan;
    }

    public void setHTThanhToan(String HTThanhToan) {
        this.HTThanhToan = HTThanhToan;
    }

    public float getTienThanhToan() {
        return TienThanhToan;
    }

    public void setTienThanhToan(float TienThanhToan) {
        this.TienThanhToan = TienThanhToan;
    }
    
}
