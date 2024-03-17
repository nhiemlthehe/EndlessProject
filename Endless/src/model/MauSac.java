/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class MauSac {
    private String MaMauSac;
    private String TenMauSac;
    private String MoTa;

    public MauSac() {
    }

    public MauSac(String MaMauSac, String TenMauSac, String MoTa) {
        this.MaMauSac = MaMauSac;
        this.TenMauSac = TenMauSac;
        this.MoTa = MoTa;
    }

    public String getMaMauSac() {
        return MaMauSac;
    }

    public void setMaMauSac(String MaMauSac) {
        this.MaMauSac = MaMauSac;
    }

    public String getTenMauSac() {
        return TenMauSac;
    }

    public void setTenMauSac(String TenMauSac) {
        this.TenMauSac = TenMauSac;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

}
