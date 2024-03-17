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
public class ThongKeDoanhThu {
    private String thoiGian;
    private Double tongDoanhThu;
    private Double doanhThuCaoNhat;
    private Double doanhThuThapNhat;
    private Double doanhThuTrungBinh;

    public ThongKeDoanhThu() {
    }

    public ThongKeDoanhThu(String thoiGian, Double tongDoanhThu, Double doanhThuCaoNhat, Double doanhThuThapNhat, Double doanhThuTrungBinh) {
        this.thoiGian = thoiGian;
        this.tongDoanhThu = tongDoanhThu;
        this.doanhThuCaoNhat = doanhThuCaoNhat;
        this.doanhThuThapNhat = doanhThuThapNhat;
        this.doanhThuTrungBinh = doanhThuTrungBinh;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(Double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public Double getDoanhThuCaoNhat() {
        return doanhThuCaoNhat;
    }

    public void setDoanhThuCaoNhat(Double doanhThuCaoNhat) {
        this.doanhThuCaoNhat = doanhThuCaoNhat;
    }

    public Double getDoanhThuThapNhat() {
        return doanhThuThapNhat;
    }

    public void setDoanhThuThapNhat(Double doanhThuThapNhat) {
        this.doanhThuThapNhat = doanhThuThapNhat;
    }

    public Double getDoanhThuTrungBinh() {
        return doanhThuTrungBinh;
    }

    public void setDoanhThuTrungBinh(Double doanhThuTrungBinh) {
        this.doanhThuTrungBinh = doanhThuTrungBinh;
    }
}
