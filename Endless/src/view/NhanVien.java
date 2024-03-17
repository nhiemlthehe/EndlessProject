/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Auth;
import util.Encode;
import util.NhanVienDAO;
import util.XImage;

/**
 *
 * @author Admin
 */
public final class NhanVien extends javax.swing.JPanel {

    JFileChooser fileChooser = new JFileChooser();
    NhanVienDAO nvDAO = new NhanVienDAO();
    int row = -1;
    int count = 0;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Encode en = new Encode();

    /**
     * Creates new form NhanVien1
     */
    public NhanVien() {
        initComponents();
        fillTableNhanVien();
        initForm();
    }

    public void initForm() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);

        for (int i = 0; i < tblNV.getColumnCount(); i++) {
            tblNV.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

    }

    void setForm(model.NhanVien nv) {
        txtIDnhanvien.setText(nv.getMaNV() + "");
        txtTenNV.setText(nv.getTenNV());
        txtTaikhoan.setText(nv.getMaTK());
        rdoNam.setSelected(nv.isGioiTinh());
        rdoNu.setSelected(!nv.isGioiTinh());
        dcsNgaySinh.setDate(nv.getNgaySinh());
        txtSDT.setText(nv.getSDT());
        txtEmail.setText(nv.getEmail());
        rdoTruongPhong.setSelected(nv.isVaiTro());
        rdoNhanvien.setSelected(!nv.isVaiTro());
        taaDiaChi.setText(nv.getDiaChi());
        if (nv.getHinh() != null) {
            lblHinh.setToolTipText(nv.getHinh());
            lblHinh.setImage(new ImageIcon(getClass().getResource("/image/person/" + nv.getHinh())));
        }

    }

    model.NhanVien getForm() {
        model.NhanVien nv = new model.NhanVien();
        if (!txtIDnhanvien.getText().equals("")) {
            nv.setMaNV(txtIDnhanvien.getText());
        }
        nv.setTenNV(txtTenNV.getText());
        nv.setVaiTro(rdoTruongPhong.isSelected());
        nv.setMaTK(txtTaikhoan.getText());
        nv.setMatKhau(en.hashPassword(new String(txtMatKhau.getPassword())));
        if (txtMatKhau.getPassword().length == 0 || txtMatKhau.getText().isEmpty()) {
            nv.setMatKhau(nvDAO.selectById(txtIDnhanvien.getText()).getMatKhau());
            System.out.println("null");
        }
        nv.setGioiTinh(rdoNam.isSelected());
        String ngaySinhText = dcsNgaySinh.getDateFormatString().trim();
        if (!ngaySinhText.isEmpty()) {
            nv.setNgaySinh(dcsNgaySinh.getDate());
        }
        nv.setSDT(txtSDT.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(taaDiaChi.getText());
        if (nv.getHinh() != null) {
            lblHinh.setToolTipText(nv.getHinh());
            lblHinh.setImage(XImage.read("person/", nv.getHinh()));
        }
        nv.setHinh(lblHinh.getToolTipText());
        return nv;
    }

    public String taoMaNV() {
        model.NhanVien nv = nvDAO.selectDESC().get(0);
        if (nv == null) {
            nv = new model.NhanVien();
            nv.setMaNV("NV000");
        }

        String oldID = nv.getMaNV();
        String number = oldID.substring(2, oldID.length());
        String newID = "NV";
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

    void Delete() {
        if (txtTaikhoan.getText().equals(Auth.user.getMaTK())) {
            JOptionPane.showMessageDialog(this, "Không thể xóa '" + Auth.user.getMaTK() + "' vì bạn đang đăng nhập bằng tài khoản này !!");
        } else {
            String id = txtIDnhanvien.getText();
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn muốn xoá Nhân Viên này chứ?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {

                try {
                    nvDAO.delete(id);
                    this.fillTableNhanVien();
                    Remove();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        }
    }

    void Remove() {
        txtIDnhanvien.setText("");
        txtTenNV.setText("");
        txtEmail.setText("");
        lblHinh.setImage(null);
        taaDiaChi.setText("");
        txtMatKhau.setText("");
        txtSDT.setText("");
        txtTaikhoan.setText("");
        dcsNgaySinh.setDate(null);
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
    }

    void Add() {
        if (checkValidateForm() == true) {
            model.NhanVien nv = getForm();
            nv.setMaNV(taoMaNV());
            try {
                nvDAO.insert(nv);
                this.fillTableNhanVien();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                System.out.println(e);
            }
        }
    }

    void Update() {
        if (checkValidateForm() == true) {
            model.NhanVien nv = getForm();
            try {
                nvDAO.update(nv);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                Remove();
                fillTableNhanVien();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                System.out.println(e);
            }
        }
    }

    void CickImg() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save("person", file);
            ImageIcon icon = XImage.read("person", file.getName());
            lblHinh.setImage(icon);
            lblHinh.setToolTipText(file.getName());
        }

    }

    void fillTableNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<model.NhanVien> list = nvDAO.selectByKeyword(keyword);
            for (model.NhanVien nv : list) {
                Object[] rows = {nv.getMaNV(), nv.getTenNV(), nv.isVaiTro() ? "Trưởng phòng" : "Nhân viên", nv.getMaTK(), nv.isGioiTinh() ? "Nam" : "Nữ", nv.getNgaySinh(), nv.getSDT(), nv.getEmail(), nv.getHinh()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean checkValidateForm() {
        String loi = "";

        // Kiểm tra tên nhân viên
        if (!txtTenNV.getText().matches("^[\\p{L} .'-]+$")) {
            loi += "Tên nhân viên không đúng định dạng\n";
        }
        // Kiểm tra ngày sinh

        Date ngaySinh = dcsNgaySinh.getDate();
        if (ngaySinh != null) {
            Calendar cal18YearsAgo = Calendar.getInstance();
            cal18YearsAgo.add(Calendar.YEAR, -18);
            if (ngaySinh.after(cal18YearsAgo.getTime())) {
                loi += "Ngày sinh phải trên 18 tuổi.\n";
            }
        } else {
            loi += "Vui lòng chọn ngày sinh.\n";
        }
        // Kiểm tra giới tính
        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            loi += "Giới tính không được bỏ trống\n";
        }
        // Kiểm tra loại nhân viên
        if (!rdoNhanvien.isSelected() && !rdoTruongPhong.isSelected()) {
            loi += "Vai trò không được bỏ trống\n";
        }
        // Kiểm tra số điện thoại
        if (txtSDT.getText().isEmpty() || !txtSDT.getText().matches("^0[0-9]{9}$")) {
            loi += "Vui lòng nhập số điện thoại, số điện thoại gồm 10 số và số đầu phải là số 0\n";
        }
        // Kiểm tra tài khoản
        if (txtTaikhoan.getText().isEmpty()) {
            loi += "Tài khoản không được bỏ trống\n";
        }

        String taiKhoan = txtTaikhoan.getText();
        // Kiểm tra độ dài và thành phần của tài khoản

        if (!taiKhoan.matches(".*[a-zA-Z].*") || !taiKhoan.matches(".*\\d.*")) {
            loi += "Tài khoản phải chứa ít nhất một chữ cái và một số\n";
        } else if (taiKhoan.matches(".*[^a-zA-Z0-9].*")) {
            loi += "Tài khoản không được chứa kí tự đặc biệt\n";
        }

        // Kiểm tra mật khẩu
        String matKhau = new String(txtMatKhau.getPassword());
        if (!matKhau.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$")) {
            loi += "Mật khẩu phải bao gồm kí tự viết thường, kí tự viết hoa, số và kí tự đặt biệt\n";
        }
        // Hiển thị thông báo lỗi nếu có
        if (!loi.equals("")) {
            JOptionPane.showMessageDialog(this, loi);
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txtTimKiem = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTaikhoan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        dcsNgaySinh = new com.toedter.calendar.JDateChooser();
        txtMatKhau = new javax.swing.JPasswordField();
        btnXoa = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblHinh = new controller.ImageAvatar();
        jPanel4 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanvien = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        taaDiaChi = new javax.swing.JTextArea();
        txtIDnhanvien = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));
        setPreferredSize(new java.awt.Dimension(1280, 730));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, null));
        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên nhân viên", "Vai trò", "Tài khoản", "Giới tính", "Ngày sinh", "Số điện thoại", "Email", "Hình ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNV.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblNV.setSelectionForeground(new java.awt.Color(0, 0, 51));
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNV);
        if (tblNV.getColumnModel().getColumnCount() > 0) {
            tblNV.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblNV.getColumnModel().getColumn(0).setMaxWidth(60);
            tblNV.getColumnModel().getColumn(1).setPreferredWidth(230);
            tblNV.getColumnModel().getColumn(1).setMaxWidth(230);
            tblNV.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblNV.getColumnModel().getColumn(2).setMaxWidth(120);
            tblNV.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblNV.getColumnModel().getColumn(3).setMaxWidth(100);
            tblNV.getColumnModel().getColumn(4).setPreferredWidth(70);
            tblNV.getColumnModel().getColumn(4).setMaxWidth(70);
            tblNV.getColumnModel().getColumn(5).setPreferredWidth(110);
            tblNV.getColumnModel().getColumn(5).setMaxWidth(110);
            tblNV.getColumnModel().getColumn(6).setPreferredWidth(110);
            tblNV.getColumnModel().getColumn(6).setMaxWidth(110);
            tblNV.getColumnModel().getColumn(8).setPreferredWidth(120);
            tblNV.getColumnModel().getColumn(8).setMaxWidth(120);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1250, 220));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimKiem.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyTyped(evt);
            }
        });
        jPanel6.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 10, 300, 40));

        blackButton1.setText("blackButton1");
        blackButton1.setRadius(48);
        jPanel6.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 8, 304, 44));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 310, 60));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, null));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("Mã nhân viên:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, -1, -1));
        jPanel2.add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 250, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Tài khoản: (*)");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("Tên nhân viên: (*)");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Mật khẩu: (*)");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, -1, -1));

        txtTaikhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaikhoanActionPerformed(evt);
            }
        });
        jPanel2.add(txtTaikhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 250, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Ngày sinh: (*)");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 40, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("Giới tính: (*)");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, -1, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("Số điện thoại : (*)");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 110, -1, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Email: (*)");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 190, -1, -1));

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        jPanel2.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 130, 250, 40));

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel2.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 210, 250, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("Địa chỉ: (*)");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Vai trò: (*)");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setText("Hình ảnh ");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, 50));

        dcsNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(dcsNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 60, 250, 40));
        jPanel2.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 250, 40));

        btnXoa.setBackground(new java.awt.Color(80, 199, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 310, 130, 40));

        btnThem1.setBackground(new java.awt.Color(80, 199, 255));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(255, 255, 255));
        btnThem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThem1.setText("Thêm");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 310, 140, 40));

        btnLamMoi.setBackground(new java.awt.Color(80, 199, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        jPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 310, 130, 40));

        btnCapNhat.setBackground(new java.awt.Color(80, 199, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 310, 140, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setGradientColor1(new java.awt.Color(0, 204, 255));
        lblHinh.setGradientColor2(new java.awt.Color(0, 107, 255));
        lblHinh.setMaximumSize(new java.awt.Dimension(180, 180));
        lblHinh.setMinimumSize(new java.awt.Dimension(180, 180));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 190, 200));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(null);

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoNam)
                .addGap(27, 27, 27)
                .addComponent(rdoNu)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 250, 40));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(null);

        buttonGroup2.add(rdoTruongPhong);
        rdoTruongPhong.setText("Trưởng phòng");

        buttonGroup2.add(rdoNhanvien);
        rdoNhanvien.setText("Nhân viên");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoTruongPhong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoNhanvien)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTruongPhong)
                    .addComponent(rdoNhanvien))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 250, 40));

        taaDiaChi.setColumns(20);
        taaDiaChi.setLineWrap(true);
        taaDiaChi.setRows(5);
        taaDiaChi.setAutoscrolls(false);
        jScrollPane2.setViewportView(taaDiaChi);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 250, 70));

        txtIDnhanvien.setEditable(false);
        jPanel2.add(txtIDnhanvien, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 250, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1268, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtTaikhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTaikhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaikhoanActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked
        int selectedRow = tblNV.getSelectedRow();
        if (evt.getClickCount() == 2) {
            String id = tblNV.getValueAt(selectedRow, 0).toString();
            model.NhanVien nv = nvDAO.selectById(id);
            setForm(nv);
        }
    }//GEN-LAST:event_tblNVMouseClicked

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        Add();
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        Remove();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        Update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        count++;
        if (count == 2) {
            CickImg();
            count = 0;
        }
    }//GEN-LAST:event_lblHinhMouseClicked

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        fillTableNhanVien();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        fillTableNhanVien();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyTyped
        fillTableNhanVien();
    }//GEN-LAST:event_txtTimKiemKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser dcsNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private controller.ImageAvatar lblHinh;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanvien;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTextArea taaDiaChi;
    private javax.swing.JTable tblNV;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIDnhanvien;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTaikhoan;
    private javax.swing.JTextField txtTenNV;
    private swing.TextFieldAnimation txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
