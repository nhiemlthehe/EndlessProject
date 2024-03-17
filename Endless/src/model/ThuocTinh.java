/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author 12345
 */
public class ThuocTinh {
    private String TenMS;
    private String MoTaMS;
    private String KichThuoc;
    private String MoTaKT;

    public ThuocTinh() {
    }

    public ThuocTinh(String TenMS, String MoTaMS, String KichThuoc, String MoTaKT) {
        this.TenMS = TenMS;
        this.MoTaMS = MoTaMS;
        this.KichThuoc = KichThuoc;
        this.MoTaKT = MoTaKT;
    }

    public String getTenMS() {
        return TenMS;
    }

    public void setTenMS(String TenMS) {
        this.TenMS = TenMS;
    }

    public String getMoTaMS() {
        return MoTaMS;
    }

    public void setMoTaMS(String MoTaMS) {
        this.MoTaMS = MoTaMS;
    }

    public String getKichThuoc() {
        return KichThuoc;
    }

    public void setKichThuoc(String KichThuoc) {
        this.KichThuoc = KichThuoc;
    }

    public String getMoTaKT() {
        return MoTaKT;
    }

    public void setMoTaKT(String MoTaKT) {
        this.MoTaKT = MoTaKT;
    }

    
}
