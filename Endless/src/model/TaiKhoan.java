/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class TaiKhoan {
    private String TenDN;
    private String TenCB;
    private boolean VaiTro;
    private String Email;
    private String SDT;

    public TaiKhoan() {
    }

    public TaiKhoan(String TenDN, String TenCB, boolean VaiTro, String Email, String SDT) {
        this.TenDN = TenDN;
        this.TenCB = TenCB;
        this.VaiTro = VaiTro;
        this.Email = Email;
        this.SDT = SDT;
    }

    public String getTenDN() {
        return TenDN;
    }

    public void setTenDN(String TenDN) {
        this.TenDN = TenDN;
    }

    public String getTenCB() {
        return TenCB;
    }

    public void setTenCB(String TenCB) {
        this.TenCB = TenCB;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    
}
