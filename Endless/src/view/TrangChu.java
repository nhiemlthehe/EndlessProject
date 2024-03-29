/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import controller.slide1;
import controller.slide2;
import controller.slide3;
import controller.slide4;
import controller.slide5;
import util.HoaDonDAO;
import util.SanPhamDAO;
import util.TrangChuDAO;
import util.XNumber;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class TrangChu extends javax.swing.JPanel {

    /**
     * Creates new form TrangChu
     */
    public TrangChu() {
        initComponents();
        init();
    }
    
    TrangChuDAO tcDAO = new TrangChuDAO();
    XNumber xn = new XNumber();
    public void init() {
        
        slideshow.initSlideshow(new slide1(), new slide2(), new slide3(), new slide4(), new slide5() );
        
        int soHoaDon = 0;
        lblSLHD.setText(tcDAO.countHoaDon()+"");
        lblSLSP.setText(tcDAO.sumSoLuong()+"");
        lblDoanhThu.setText(xn.formatDecimal(tcDAO.sumTongTien())+"");
        lblSLDN.setText(tcDAO.countNhapHang()+"");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        customGradientPanel4 = new controller.CustomGradientPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblSLDN = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        customGradientPanel3 = new controller.CustomGradientPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblSLSP = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        customGradientPanel2 = new controller.CustomGradientPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblDoanhThu = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        customGradientPanel1 = new controller.CustomGradientPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSLHD = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        slideshow = new controller.Slideshow();

        setMaximumSize(new java.awt.Dimension(1290, 730));
        setMinimumSize(new java.awt.Dimension(1290, 730));
        setPreferredSize(new java.awt.Dimension(1290, 730));
        setRequestFocusEnabled(false);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, null));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 204, 51), null));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 8, -1, -1));

        customGradientPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        customGradientPanel4.setForeground(new java.awt.Color(255, 255, 255));
        customGradientPanel4.setBorderThickness(200.0F);
        customGradientPanel4.setColorEnd(new java.awt.Color(0, 184, 0));
        customGradientPanel4.setColorStart(new java.awt.Color(0, 7, 0));
        customGradientPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setMaximumSize(new java.awt.Dimension(244, 30));
        jPanel4.setMinimumSize(new java.awt.Dimension(244, 30));

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ĐƠN NHẬP HÔM NAY");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        customGradientPanel4.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 187, -1, -1));

        lblSLDN.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblSLDN.setForeground(new java.awt.Color(255, 255, 255));
        lblSLDN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSLDN.setText("0");
        customGradientPanel4.add(lblSLDN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 250, -1));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/containerx50.png"))); // NOI18N
        customGradientPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 50, 50));

        jLabel16.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Đơn nhập");
        customGradientPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 136, 250, 30));

        jPanel5.add(customGradientPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 30, 250, 220));

        customGradientPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        customGradientPanel3.setForeground(new java.awt.Color(255, 255, 255));
        customGradientPanel3.setBorderThickness(200.0F);
        customGradientPanel3.setColorEnd(new java.awt.Color(252, 161, 14));
        customGradientPanel3.setColorStart(new java.awt.Color(51, 20, 0));
        customGradientPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setMaximumSize(new java.awt.Dimension(244, 30));
        jPanel3.setMinimumSize(new java.awt.Dimension(244, 30));

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("SẢN PHẨM ĐÃ BÁN HÔM NAY");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        customGradientPanel3.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 187, -1, -1));

        lblSLSP.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblSLSP.setForeground(new java.awt.Color(255, 255, 255));
        lblSLSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSLSP.setText("0");
        customGradientPanel3.add(lblSLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 250, -1));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/productx50.png"))); // NOI18N
        customGradientPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 50, 50));

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Sản phẩm");
        customGradientPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 136, 250, 30));

        jPanel5.add(customGradientPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 250, 220));

        customGradientPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        customGradientPanel2.setForeground(new java.awt.Color(255, 255, 255));
        customGradientPanel2.setBorderColor(new java.awt.Color(12, 0, 69));
        customGradientPanel2.setBorderThickness(200.0F);
        customGradientPanel2.setColorEnd(new java.awt.Color(0, 101, 143));
        customGradientPanel2.setColorStart(new java.awt.Color(6, 0, 63));
        customGradientPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setMaximumSize(new java.awt.Dimension(244, 30));
        jPanel2.setMinimumSize(new java.awt.Dimension(244, 30));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("DOANH THU HÔM NAY");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        customGradientPanel2.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 187, -1, -1));

        lblDoanhThu.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThu.setText("0");
        customGradientPanel2.add(lblDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 250, -1));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/moneyx50.png"))); // NOI18N
        customGradientPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 50, 50));

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("VND");
        customGradientPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 136, 250, 30));

        jPanel5.add(customGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 250, 220));

        customGradientPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        customGradientPanel1.setForeground(new java.awt.Color(255, 255, 255));
        customGradientPanel1.setBorderColor(new java.awt.Color(69, 0, 41));
        customGradientPanel1.setBorderThickness(200.0F);
        customGradientPanel1.setColorEnd(new java.awt.Color(131, 1, 130));
        customGradientPanel1.setColorStart(new java.awt.Color(51, 51, 51));
        customGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setMaximumSize(new java.awt.Dimension(244, 30));
        jPanel1.setMinimumSize(new java.awt.Dimension(244, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(244, 30));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HÓA ĐƠN HÔM NAY");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        customGradientPanel1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 187, -1, -1));

        lblSLHD.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lblSLHD.setForeground(new java.awt.Color(255, 255, 255));
        lblSLHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSLHD.setText("0");
        customGradientPanel1.add(lblSLHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 250, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hoaDonx50.png"))); // NOI18N
        customGradientPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 50, 50));

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hóa đơn");
        customGradientPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 136, 250, 30));

        jPanel5.add(customGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 250, 220));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(null);
        jPanel10.setPreferredSize(new java.awt.Dimension(1230, 355));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(slideshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1230, 355));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.CustomGradientPanel customGradientPanel1;
    private controller.CustomGradientPanel customGradientPanel2;
    private controller.CustomGradientPanel customGradientPanel3;
    private controller.CustomGradientPanel customGradientPanel4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblDoanhThu;
    private javax.swing.JLabel lblSLDN;
    private javax.swing.JLabel lblSLHD;
    private javax.swing.JLabel lblSLSP;
    private controller.Slideshow slideshow;
    // End of variables declaration//GEN-END:variables
}
