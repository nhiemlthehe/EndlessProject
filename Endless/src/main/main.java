package main;

import controller.MenuPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import util.Auth;
import util.KhuyenMaiDAO;
import util.SanPhamDAO;
import view.GiaoDich;
import view.HoaDon;
import view.KhachHang;
import view.KhuyenMai;
import view.NhaCungCap;
import view.NhanVien;
import view.NhapHang;
import view.SanPham;
import view.TaiKhoan;
import view.ThongKe;
import view.ThuocTinh;
import view.TrangChu;
import view.login;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class main extends javax.swing.JFrame {

    int location = 0;

    public void changeLocation(int a) {
        this.location = a;
    }

    KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
    SanPhamDAO spDAO = new SanPhamDAO();

    public main() {
        initComponents();
        setResizable(false);
        setTabSelect(location);
        addMenuPanel();
        lblUsername.setText(util.Auth.user.getMaTK());
        lblRole.setText(util.Auth.isManager() ? "Quản lý" : "Nhân viên");
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/person/" + util.Auth.user.getHinh()));
        lblHinhAnh.setImage(icon);
    }

    public void addMenuPanel() {
        pnlMenu.setLayout(new AbsoluteLayout());
        int k = 0;
        String[] menuName = {"Trang chủ", "Giao dịch", "Lịch sử giao dịch", "Nhân viên", "Khách hàng", "Nhà cung cấp",
            "Sản phẩm", "Thuộc tính", "Nhập hàng", "Khuyến mãi", "Thống kê", "Đăng xuất", "Thoát"};
        String[] icon = {"TrangChu_24x.jpg", "GiaoDich_24x.jpg", "HoaDon_24x.jpg", "NhanVien_24x.jpg", "KhachHang_24x.png", "NhaCungCap_24x.jpg",
            "SanPham_24x.jpg", "ChiTietSanPham_24x.jpg", "NhapHang_24x.jpg", "KhuyenMai_24x.jpg", "ThongKe_24x.jpg", "Thoat_24x.jpg", "thoat.png"};
        for (int i = 0; i < 13; i++) {
            ImageIcon icon1 = new ImageIcon(getClass().getResource("/icon/" + icon[i]));
            MenuPanel menutab = new MenuPanel(i, location, icon1, menuName[i]);

            int finalI = i;
            menutab.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    location = finalI;
                    setTabSelect(finalI);
                }
            });
            pnlMenu.add(menutab, new AbsoluteConstraints(0, k));
            k += 55;
        }
    }

    public void updatePrice() {
        List<model.KhuyenMai> kmList = kmDAO.selectAll();
        for (model.KhuyenMai km : kmList) {
            if (km.getNgayKetThuc().before(new Date())) {
                List<model.SanPham> spList = spDAO.selectByKM(km.getMaKM());
                for (model.SanPham sp : spList) {
                    if (sp.getMaKM() != null) {
                        sp.setMaKM(null);
                        sp.setGiaKhuyenMai(0);
                        spDAO.update(sp);
                    }
                }
            }
        }
    }

    public void changeTab(JPanel pnl) {
        GiaoDien.removeAll();
        GiaoDien.revalidate();
        GiaoDien.repaint();
        GiaoDien.setLayout(new AbsoluteLayout());
        GiaoDien.add(pnl, new AbsoluteConstraints(0, 0));
        pnlMenu.removeAll();
        pnlMenu.revalidate();
        pnlMenu.repaint();
        addMenuPanel();
        updatePrice();
    }

    public void setTabSelect(int location) {
        switch (location) {
            case 0:
                changeTab(new TrangChu());
                changeLocation(0);
                break;
            case 1:
                changeTab(new GiaoDich());
                changeLocation(0);
                break;
            case 2:
                changeTab(new HoaDon());
                changeLocation(0);
                break;
            case 3:
                if (!Auth.isManager()) {
                    JOptionPane.showMessageDialog(this, "Chỉ quản lý mới được vào trang này!");
                    break;
                }
                changeTab(new NhanVien());
                changeLocation(0);
                break;
            case 4:
                changeTab(new KhachHang());
                changeLocation(0);
                break;
            case 5:
                changeTab(new NhaCungCap());
                changeLocation(0);
                break;
            case 6:
                changeTab(new SanPham());
                changeLocation(0);
                break;
            case 7:
                changeTab(new ThuocTinh());
                changeLocation(0);
                break;
            case 8:
                if (!Auth.isManager()) {
                    JOptionPane.showMessageDialog(this, "Chỉ quản lý mới được vào trang này!");
                    break;
                }
                changeTab(new NhapHang());
                changeLocation(0);
                break;
            case 9:
                if (!Auth.isManager()) {
                    JOptionPane.showMessageDialog(this, "Chỉ quản lý mới được vào trang này!");
                    break;
                }
                changeTab(new KhuyenMai());
                changeLocation(0);
                break;
            case 10:
                if (!Auth.isManager()) {
                    JOptionPane.showMessageDialog(this, "Chỉ quản lý mới được vào trang này!");
                    break;
                }
                changeTab(new ThongKe());
                break;
            case 11:
                this.setVisible(false);
                new login().setVisible(true);
            case 12:
                this.dispose();
                break;
            case 13:
                changeTab(new TaiKhoan());
                break;
            default:
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GiaoDien = new javax.swing.JPanel();
        logo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblHinhAnh = new controller.ImageAvatar();
        lblUsername = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        blackButton1 = new controller.BlackButton();
        pnlMenu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1550, 825));
        setMinimumSize(new java.awt.Dimension(1550, 825));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1550, 825));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        GiaoDien.setBorder(null);
        GiaoDien.setMaximumSize(new java.awt.Dimension(1280, 730));
        GiaoDien.setMinimumSize(new java.awt.Dimension(1280, 730));
        GiaoDien.setPreferredSize(new java.awt.Dimension(1280, 730));

        javax.swing.GroupLayout GiaoDienLayout = new javax.swing.GroupLayout(GiaoDien);
        GiaoDien.setLayout(GiaoDienLayout);
        GiaoDienLayout.setHorizontalGroup(
            GiaoDienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1300, Short.MAX_VALUE)
        );
        GiaoDienLayout.setVerticalGroup(
            GiaoDienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        getContentPane().add(GiaoDien, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 1300, 750));

        logo.setBackground(new java.awt.Color(0, 0, 0));
        logo.setMaximumSize(new java.awt.Dimension(250, 810));
        logo.setMinimumSize(new java.awt.Dimension(250, 810));
        logo.setPreferredSize(new java.awt.Dimension(250, 810));
        logo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logocokhung.png"))); // NOI18N
        jLabel4.setText("jLabel1");
        logo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 80, 80));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Bức phá giới hạn");
        logo.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("ENDLESS");
        logo.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 140, -1));

        getContentPane().add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 80));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setForeground(new java.awt.Color(175, 29, 0));
        jPanel3.setMaximumSize(new java.awt.Dimension(1530, 80));
        jPanel3.setMinimumSize(new java.awt.Dimension(1530, 80));
        jPanel3.setPreferredSize(new java.awt.Dimension(1530, 80));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHinhAnh.setBorderSize(3);
        lblHinhAnh.setBorderSpace(3);
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });
        jPanel3.add(lblHinhAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(1302, 8, 50, 60));

        lblUsername.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Admin");
        lblUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsernameMouseClicked(evt);
            }
        });
        jPanel3.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 10, 140, 40));

        lblRole.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblRole.setForeground(new java.awt.Color(255, 204, 0));
        lblRole.setText("Quản lý");
        lblRole.setToolTipText("");
        lblRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRoleMouseClicked(evt);
            }
        });
        jPanel3.add(lblRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(1370, 39, 140, 20));

        blackButton1.setBackground(new java.awt.Color(29, 36, 46));
        blackButton1.setText("blackButton1");
        blackButton1.setRadius(65);
        blackButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blackButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1295, 8, 220, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1550, 80));

        pnlMenu.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 715, Short.MAX_VALUE)
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 250, 715));

        jPanel1.setBackground(new java.awt.Color(29, 36, 46));

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Coppyright © Endless");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 795, 250, 30));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void myButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myButton1MouseClicked
        setTabSelect(13);
    }//GEN-LAST:event_myButton1MouseClicked

    private void blackButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blackButton1ActionPerformed
        setTabSelect(13);
    }//GEN-LAST:event_blackButton1ActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        setTabSelect(13);
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void lblUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsernameMouseClicked
        setTabSelect(13);
    }//GEN-LAST:event_lblUsernameMouseClicked

    private void lblRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRoleMouseClicked
        setTabSelect(13);
    }//GEN-LAST:event_lblRoleMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GiaoDien;
    private controller.BlackButton blackButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private controller.ImageAvatar lblHinhAnh;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel logo;
    private javax.swing.JPanel pnlMenu;
    // End of variables declaration//GEN-END:variables
}
