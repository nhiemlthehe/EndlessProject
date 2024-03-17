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
public class KhuyenMai {
    private String MaKM;
    private String TenKM;
    private int MucGiamGia;
    private Date NgayBatDau;
    private Date NgayKetThuc;

    public KhuyenMai() {
    }

    public KhuyenMai(String MaKM, String TenKM, int MucGiamGia, Date NgayBatDau, Date NgayKetThuc) {
        this.MaKM = MaKM;
        this.TenKM = TenKM;
        this.MucGiamGia = MucGiamGia;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String MaKM) {
        this.MaKM = MaKM;
    }

    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String TenKM) {
        this.TenKM = TenKM;
    }

    public int getMucGiamGia() {
        return MucGiamGia;
    }

    public void setMucGiamGia(int MucGiamGia) {
        this.MucGiamGia = MucGiamGia;
    }

    public Date getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }



    
}
