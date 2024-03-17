package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.SendEmailSMTP;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import util.NhanVienDAO;

/**
 *
 * @author Hi There
 */
public class QuenMatKhau extends javax.swing.JPanel {

    /**
     * Creates new form QuenMatKhau1
     */
    public QuenMatKhau() {
        initComponents();
    }

    String otp;
    long lastSendTime = 0;
    public void changeTab(JPanel pnl) {
        removeAll();
        revalidate();
        repaint();
        setLayout(new AbsoluteLayout());
        add(pnl, new AbsoluteConstraints(0, 0));
    }

    private void checkOTP() {
        if (txtNhapmaxacnhan.getText().equalsIgnoreCase(otp)) {
            JOptionPane.showMessageDialog(this, "Xác nhận thành công");
            DoiMatKhau dmk = new DoiMatKhau();
            dmk.setEmail(txtNhapEmail.getText());
            changeTab(dmk);
        } else {
            JOptionPane.showMessageDialog(this, "Nhập sai mã OTP vui lòng nhập lại");
        }
    }

    private void sendOTP() {
        long currentTime = System.currentTimeMillis();
        // Kiểm tra nếu chưa đủ 30 giây kể từ lần gọi hàm trước đó
        if (currentTime - lastSendTime < 30*1000) {
            JOptionPane.showMessageDialog(this, "Vui lòng đợi 30 giây trước khi gửi lại OTP.");
        } else {
            // Thực hiện gửi OTP
            SendEmailSMTP sm = new SendEmailSMTP();
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!txtNhapEmail.getText().matches(emailRegex)) {
                JOptionPane.showMessageDialog(this, "Email chưa đúng định dạng!");
            } else {
                sm.sendOTP(txtNhapEmail.getText());
                otp = sm.getNumberOTP();
                lastSendTime = currentTime;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblQuayve = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        lblOtp = new javax.swing.JLabel();
        txtNhapmaxacnhan = new javax.swing.JTextField();
        txtNhapEmail = new javax.swing.JTextField();
        lblGuiMa = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(254, 220, 197));
        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 730));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 730));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 730));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hinh (2).png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 450));

        jPanel2.setBackground(new java.awt.Color(254, 230, 214));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Quên mật khẩu");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 220, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Nhập email: (*)");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 101, -1));

        lblQuayve.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuayve.setText("Quay về đăng nhập");
        lblQuayve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuayveMouseClicked(evt);
            }
        });
        jPanel2.add(lblQuayve, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 110, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Nhập mã xác nhận:  (*)");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 160, -1));

        btnXacNhan.setBackground(new java.awt.Color(80, 199, 255));
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXacNhanMouseClicked(evt);
            }
        });
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel2.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, -1, -1));

        lblOtp.setForeground(new java.awt.Color(254, 230, 214));
        jPanel2.add(lblOtp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        txtNhapmaxacnhan.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        txtNhapmaxacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhapmaxacnhanActionPerformed(evt);
            }
        });
        jPanel2.add(txtNhapmaxacnhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 300, 40));

        txtNhapEmail.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        txtNhapEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhapEmailActionPerformed(evt);
            }
        });
        jPanel2.add(txtNhapEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 300, 40));

        lblGuiMa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblGuiMa.setText("<html><body><u>Gửi mã</u></body></html>");
        lblGuiMa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGuiMaMouseClicked(evt);
            }
        });
        jPanel2.add(lblGuiMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, 430, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblQuayveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuayveMouseClicked
        new login().show();
    }//GEN-LAST:event_lblQuayveMouseClicked

    private void btnXacNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanMouseClicked

    }//GEN-LAST:event_btnXacNhanMouseClicked

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        checkOTP();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void lblGuiMaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGuiMaMouseClicked
        if (txtNhapEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Email !");
            return;
        }
        lblGuiMa.setText("<html><u>Gửi lại mã sau 30s</u><html>");
        lblGuiMa.setFocusable(false);
        sendOTP();
        Timer timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lblGuiMa.setFocusable(true);
                lblGuiMa.setText("<html><u>Gửi mã</u><html>");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }//GEN-LAST:event_lblGuiMaMouseClicked

    private void txtNhapEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhapEmailActionPerformed
        txtNhapmaxacnhan.requestFocus();
    }//GEN-LAST:event_txtNhapEmailActionPerformed

    private void txtNhapmaxacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhapmaxacnhanActionPerformed
        if (txtNhapEmail.getText().isEmpty()) {
            txtNhapEmail.requestFocus();
        }
        else{
            checkOTP();
        }
    }//GEN-LAST:event_txtNhapmaxacnhanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblGuiMa;
    private javax.swing.JLabel lblOtp;
    private javax.swing.JLabel lblQuayve;
    private javax.swing.JTextField txtNhapEmail;
    private javax.swing.JTextField txtNhapmaxacnhan;
    // End of variables declaration//GEN-END:variables

}
