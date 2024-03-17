package model;

public class ChiTietNhapHang {
    private String MaCTDN;
    private String MaDN;
    private String MaCTSP;
    private int SoLuong;

    public ChiTietNhapHang() {
    }

    public ChiTietNhapHang(String MaCTDN, String MaDN, String MaCTSP, int SoLuong) {
        this.MaCTDN = MaCTDN;
        this.MaDN = MaDN;
        this.MaCTSP = MaCTSP;
        this.SoLuong = SoLuong;
    }

    public String getMaCTDN() {
        return MaCTDN;
    }

    public void setMaCTDN(String MaCTDN) {
        this.MaCTDN = MaCTDN;
    }

    public String getMaDN() {
        return MaDN;
    }

    public void setMaDN(String MaDN) {
        this.MaDN = MaDN;
    }

    public String getMaCTSP() {
        return MaCTSP;
    }

    public void setMaCTSP(String MaCTSP) {
        this.MaCTSP = MaCTSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    
}
