package model;

public class LoaiGiay {
    private String maLoaiGiay;
    private String tenLoaiGiay;
    private String moTa;

    public LoaiGiay() {
    }

    public LoaiGiay(String maLoaiGiay, String tenLoaiGiay, String moTa) {
        this.maLoaiGiay = maLoaiGiay;
        this.tenLoaiGiay = tenLoaiGiay;
        this.moTa = moTa;
    }

    public String getMaLoaiGiay() {
        return maLoaiGiay;
    }

    public void setMaLoaiGiay(String maLoaiGiay) {
        this.maLoaiGiay = maLoaiGiay;
    }

    public String getTenLoaiGiay() {
        return tenLoaiGiay;
    }

    public void setTenLoaiGiay(String tenLoaiGiay) {
        this.tenLoaiGiay = tenLoaiGiay;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    
}

