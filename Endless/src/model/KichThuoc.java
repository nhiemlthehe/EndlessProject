/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class KichThuoc {
    private String MaKichThuoc;
    private String TenKichThuoc;
    private String MoTa;

    public KichThuoc() {
    }

    public KichThuoc(String MaKichThuoc, String TenKichThuoc, String MoTa) {
        this.MaKichThuoc = MaKichThuoc;
        this.TenKichThuoc = TenKichThuoc;
        this.MoTa = MoTa;
    }

    public String getMaKichThuoc() {
        return MaKichThuoc;
    }

    public void setMaKichThuoc(String MaKichThuoc) {
        this.MaKichThuoc = MaKichThuoc;
    }

    public String getTenKichThuoc() {
        return TenKichThuoc;
    }

    public void setTenKichThuoc(String TenKichThuoc) {
        this.TenKichThuoc = TenKichThuoc;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    
    
}
