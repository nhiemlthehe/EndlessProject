package model;

import java.util.Date;

public class NhapHang {
    private String MaDN;
    private String MaNCC;
    private String MaNV;
    private Date NgayNhap;
    private String GhiChu;

    public NhapHang() {
    }

    public NhapHang(String MaDN, String MaNCC, String MaNV, Date NgayNhap, String GhiChu) {
        this.MaDN = MaDN;
        this.MaNCC = MaNCC;
        this.MaNV = MaNV;
        this.NgayNhap = NgayNhap;
        this.GhiChu = GhiChu;
    }

    public String getMaDN() {
        return MaDN;
    }

    public void setMaDN(String MaDN) {
        this.MaDN = MaDN;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
}
