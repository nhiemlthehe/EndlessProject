/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class Encode {

    private static final int WORKLOAD = 12;

    // Hàm tạo mật khẩu mới với salt và số lần lặp, trả về mật khẩu đã được mã hóa
    public static String hashPassword(String plainPassword) {
        String salt = BCrypt.withDefaults().hashToString(WORKLOAD, plainPassword.toCharArray());
        return salt;
    }

    // Hàm kiểm tra mật khẩu nhập vào có khớp với mật khẩu đã được mã hóa hay không
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
}
