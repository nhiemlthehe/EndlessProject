/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class SanPham {
    private String maSP;
    private String maKM;
    private String TenSP;
    private String maLoaiGiay;
    private String maVach;
    private double DonGiaNhap;
    private double DonGiaBan;
    private double GiaKhuyenMai;
    private String hinhAnh;

    public SanPham() {
    }

    public SanPham(String maSP, String maKM, String TenSP, String maLoaiGiay, String maVach, double DonGiaNhap, double DonGiaBan, double GiaKhuyenMai, String hinhAnh) {
        this.maSP = maSP;
        this.maKM = maKM;
        this.TenSP = TenSP;
        this.maLoaiGiay = maLoaiGiay;
        this.maVach = maVach;
        this.DonGiaNhap = DonGiaNhap;
        this.DonGiaBan = DonGiaBan;
        this.GiaKhuyenMai = GiaKhuyenMai;
        this.hinhAnh = hinhAnh;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public String getMaLoaiGiay() {
        return maLoaiGiay;
    }

    public void setMaLoaiGiay(String maLoaiGiay) {
        this.maLoaiGiay = maLoaiGiay;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }

    public double getDonGiaNhap() {
        return DonGiaNhap;
    }

    public void setDonGiaNhap(double DonGiaNhap) {
        this.DonGiaNhap = DonGiaNhap;
    }

    public double getDonGiaBan() {
        return DonGiaBan;
    }

    public void setDonGiaBan(double DonGiaBan) {
        this.DonGiaBan = DonGiaBan;
    }

    public double getGiaKhuyenMai() {
        return GiaKhuyenMai;
    }

    public void setGiaKhuyenMai(double GiaKhuyenMai) {
        this.GiaKhuyenMai = GiaKhuyenMai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    
}
