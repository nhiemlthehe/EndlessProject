/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class ChiTietSanPham {
    private String maCTSP;
    private String maSP;
    private String maMau;
    private String maKT;
    private int soLuong;
    private String moTa;

    public ChiTietSanPham() {
    }

    public ChiTietSanPham(String maCTSP, String maSP, String maMau, String maKT, int soLuong, String moTa) {
        this.maCTSP = maCTSP;
        this.maSP = maSP;
        this.maMau = maMau;
        this.maKT = maKT;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaMau() {
        return maMau;
    }

    public void setMaMau(String maMau) {
        this.maMau = maMau;
    }

    public String getMaKT() {
        return maKT;
    }

    public void setMaKT(String maKT) {
        this.maKT = maKT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    
    
}
