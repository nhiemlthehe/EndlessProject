/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.ChiTietNhapHang;
import util.Auth;
import util.ChiTietNhapHangDAO;
import util.ChiTietSanPhamDAO;
import util.HoaDonChiTietDAO;
import util.HoaDonDAO;

import util.KhachHangDAO;
import util.KichThuocDAO;
import util.MauSacDAO;
import util.NhaCungCapDAO;
import util.NhanVienDAO;
import util.NhapHangDAO;
import util.SanPhamDAO;
import util.XNumber;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class NhaCungCap extends javax.swing.JPanel {

    NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    NhapHangDAO nhDAO = new NhapHangDAO();
    HoaDonDAO hdDAO = new HoaDonDAO();
    HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();
    KhachHangDAO khDAO = new KhachHangDAO();
    SanPhamDAO spDAO = new SanPhamDAO();

    MauSacDAO msDAO = new MauSacDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    ChiTietNhapHangDAO ctnhDAO = new ChiTietNhapHangDAO();
    DecimalFormat format = new DecimalFormat("###,###.##");
    XNumber xn = new XNumber();

    int row = -1;

    /**
     * Creates new form NhaCungCap2
     */
    public NhaCungCap() {
        initComponents();
        fillTableNhaCungCap();
        initForm();
    }

    public void initForm() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);

        for (int i = 0; i < tblNhaCungCap.getColumnCount(); i++) {
            tblNhaCungCap.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblNhapHang.getColumnCount(); i++) {
            tblNhapHang.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    void setForm(model.NhaCungCap ncc) {
        txtMaNhaCungCap.setText(ncc.getMaNCC() + "");
        txtTenNhaCungCap.setText(ncc.getTenNCC());
        txtSoDienThoai.setText(ncc.getSDT());
        txtDiaChi.setText(ncc.getDiaChi());
    }

    model.NhaCungCap getForm() {
        model.NhaCungCap ncc = new model.NhaCungCap();
        if (!txtMaNhaCungCap.getText().equals("")) {
            ncc.setMaNCC(txtMaNhaCungCap.getText());
        }
        ncc.setTenNCC(txtTenNhaCungCap.getText());
        ncc.setSDT(txtSoDienThoai.getText());
        ncc.setDiaChi(txtDiaChi.getText());
        return ncc;
    }

    void Them() {
        if (KiemLoi() == true) {
            model.NhaCungCap ncc = getForm();
            ncc.setMaNCC(taoMaNCC());

            try {
                nccDAO.insert(ncc);
                this.fillTableNhaCungCap();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                System.out.println(e);
            }
        }
    }

    void CapNhat() {
        if (KiemLoi() == true) {
            model.NhaCungCap ncc = getForm();
            try {
                nccDAO.update(ncc);
                this.fillTableNhaCungCap();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                LamMoi();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                System.out.println(e);
            }
        }
    }

    void Xoa() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa khách hàng này!!");
        } else {
            String id = txtMaNhaCungCap.getText();
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn muốn xoá nhà cung cấp này không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {

                try {
                    nccDAO.delete(id);
                    this.fillTableNhaCungCap();
                    LamMoi();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        }
    }

    void LamMoi() {
        txtMaNhaCungCap.setText("");
        txtTenNhaCungCap.setText("");
        txtSoDienThoai.setText("");
        txtDiaChi.setText("");
        this.row = -1;
    }

    public String taoMaNCC() {
        model.NhaCungCap ncc = nccDAO.selectDESC().get(0);
        if (ncc == null) {
            ncc = new model.NhaCungCap();
            ncc.setMaNCC("NCC000");
        }
        String oldID = ncc.getMaNCC();
        String number = oldID.substring(3, oldID.length());
        String newID = "NCC";
        int idNumber = Integer.parseInt(number) + 1;
        if (idNumber < 10) {
            newID += "00" + idNumber;
        } else if (idNumber < 100) {
            newID += "0" + idNumber;
        } else {
            newID += idNumber;
        }
        return newID;
    }

    void fillTableNhaCungCap() {
        DefaultTableModel model = (DefaultTableModel) tblNhaCungCap.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem1.getText();
            List<model.NhaCungCap> list = nccDAO.selectByKeyword(keyword);
            int i = 0;
            for (model.NhaCungCap ncc : list) {
                i++;
                Object[] rows = {i, ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getDiaChi()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void fillLichSuNhapHang() {
        DefaultTableModel model = (DefaultTableModel) tblNhapHang.getModel();
        model.setRowCount(0);
        try {
            List<model.NhapHang> list = nhDAO.selectNguoiNhap(txtMaNhaCungCap.getText());
            for (model.NhapHang nh : list) {
                List<model.ChiTietNhapHang> listCTNH = ctnhDAO.selectByDonNhapID(nh.getMaDN());
                for (ChiTietNhapHang ctnh : listCTNH) {
                    model.ChiTietSanPham ctsp = ctspDAO.selectById(ctnh.getMaCTSP());
                    String tenSP = spDAO.selectById(ctsp.getMaSP()).getTenSP();
                    String tenNV = nvDAO.selectById(nh.getMaNV()).getTenNV();
                    String ms = msDAO.selectByID(ctsp.getMaMau()).getTenMauSac();
                    String kt = ktDAO.selectByID(ctsp.getMaKT()).getTenKichThuoc();
                    Object[] rows = {ctnh.getMaDN(), tenSP, nh.getNgayNhap(), tenNV, ms, kt, ctnh.getSoLuong()};
                    model.addRow(rows);
                }
            }
        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public boolean KiemLoi() {
        String err = "";
        if (txtTenNhaCungCap.getText().isEmpty() || !txtTenNhaCungCap.getText().matches("^[^!-@]+$")) {
            err += "Tên nhà cung cấp không được để trống và không chứa kí tự";
        }
        if (txtSoDienThoai.getText().isEmpty() || !txtSoDienThoai.getText().matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
            err += "Số điện thoại sai định dạng";
        }
        if (txtDiaChi.getText().isEmpty()) {
            err += "Vui lòng nhập địa chỉ";
        }
        if (err.isEmpty()) {
            return true;
        }
        JOptionPane.showMessageDialog(this, err);
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNhaCungCap = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenNhaCungCap = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhaCungCap = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtTimKiem1 = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));
        setPreferredSize(new java.awt.Dimension(1280, 730));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã đơn nhập", "Tên sản phẩm", "Ngày nhập hàng", "Người nhận", "Màu sắc", "Kích thước", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhapHang.setMaximumSize(null);
        tblNhapHang.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhapHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhapHang);
        if (tblNhapHang.getColumnModel().getColumnCount() > 0) {
            tblNhapHang.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblNhapHang.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 760, 400));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Lịch sử nhập hàng:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 780, 450));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1460, 420));
        jPanel1.setMinimumSize(new java.awt.Dimension(1460, 420));
        jPanel1.setPreferredSize(new java.awt.Dimension(1460, 420));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 153, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("THÔNG TIN NHÀ CUNG CẤP");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mã nhà cung cấp:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        txtMaNhaCungCap.setEditable(false);
        jPanel2.add(txtMaNhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 390, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên nhà cung cấp: (*)");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));
        jPanel2.add(txtTenNhaCungCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 390, 50));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Số điện thoại: (*)");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));
        jPanel2.add(txtSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 390, 50));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Địa chỉ: (*)");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));
        jPanel2.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 390, 50));

        jButton1.setBackground(new java.awt.Color(80, 199, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 100, 35));

        jButton2.setBackground(new java.awt.Color(80, 199, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        jButton2.setText("Cập nhật");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 110, 35));

        jButton3.setBackground(new java.awt.Color(80, 199, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 90, 35));

        jButton4.setBackground(new java.awt.Color(80, 199, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        jButton4.setText("Làm mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, 110, 35));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1006, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 470, 450));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNhaCungCap.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhaCungCap.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblNhaCungCap.setSelectionForeground(new java.awt.Color(0, 0, 51));
        tblNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhaCungCapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhaCungCap);
        if (tblNhaCungCap.getColumnModel().getColumnCount() > 0) {
            tblNhaCungCap.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblNhaCungCap.getColumnModel().getColumn(0).setMaxWidth(50);
            tblNhaCungCap.getColumnModel().getColumn(1).setPreferredWidth(110);
            tblNhaCungCap.getColumnModel().getColumn(1).setMaxWidth(110);
            tblNhaCungCap.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblNhaCungCap.getColumnModel().getColumn(2).setMaxWidth(300);
            tblNhaCungCap.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblNhaCungCap.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1230, 180));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimKiem1.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem1ActionPerformed(evt);
            }
        });
        txtTimKiem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyTyped(evt);
            }
        });
        jPanel5.add(txtTimKiem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 10, 300, 40));

        blackButton1.setText("blackButton1");
        blackButton1.setRadius(48);
        jPanel5.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 8, 304, 44));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 380, 60));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 1260, 250));
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaCungCapMouseClicked
        row = tblNhaCungCap.getSelectedRow();
        if (evt.getClickCount() == 2) {
            String id = tblNhaCungCap.getValueAt(row, 1) + "";
            model.NhaCungCap ncc = nccDAO.selectByID(id);
            setForm(ncc);
            fillLichSuNhapHang();
        }
    }//GEN-LAST:event_tblNhaCungCapMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Them();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CapNhat();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Xoa();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LamMoi();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tblNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhapHangMouseClicked
        fillLichSuNhapHang();
        // TODO add your handling code here:
    }//GEN-LAST:event_tblNhapHangMouseClicked

    private void txtTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1ActionPerformed

    private void txtTimKiem1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyPressed
        fillTableNhaCungCap();
    }//GEN-LAST:event_txtTimKiem1KeyPressed

    private void txtTimKiem1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyReleased
        fillTableNhaCungCap();
    }//GEN-LAST:event_txtTimKiem1KeyReleased

    private void txtTimKiem1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyTyped

    }//GEN-LAST:event_txtTimKiem1KeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblNhaCungCap;
    private javax.swing.JTable tblNhapHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaNhaCungCap;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenNhaCungCap;
    private swing.TextFieldAnimation txtTimKiem1;
    // End of variables declaration//GEN-END:variables
}
