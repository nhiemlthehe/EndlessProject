/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Auth;
import util.ChiTietSanPhamDAO;
import util.KhuyenMaiDAO;
import util.KichThuocDAO;
import util.LoaiGiayDAO;
import util.MauSacDAO;
import util.SanPhamDAO;
import util.XDate;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class KhuyenMai extends javax.swing.JPanel {

    KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
    LoaiGiayDAO lgDAO = new LoaiGiayDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    MauSacDAO msDAO = new MauSacDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    DecimalFormat format = new DecimalFormat("###,###.##");
    int row = -1;

    /**
     * Creates new form KhuyenMai
     */
    public KhuyenMai() {
        initComponents();
        initForm();
    }

    public void initForm() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < tblDanhSachSanPham.getColumnCount(); i++) {
            tblDanhSachSanPham.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblDanhSachKhuyenMai.getColumnCount(); i++) {
            tblDanhSachKhuyenMai.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        txtMaKhuyenMai.setText(taoMaKM());
        dcsNgayBatDau.setDate(new Date());
        dcsNgayKetThuc.setDate(new Date());
        initComboboxLoaiGiay();
        fillTableKhuyenMai();
    }

    public void initComboboxLoaiGiay() {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboApDung.getModel();
        cboModel.removeAllElements();
        cboModel.addElement("Chọn loại giày");
        List<model.LoaiGiay> lg = new LoaiGiayDAO().selectAll();
        for (model.LoaiGiay loaigiay : lg) {
            String tenLoaiGiay = loaigiay.getTenLoaiGiay();
            cboModel.addElement(tenLoaiGiay);
        }
        cboApDung.setSelectedIndex(0);
    }

    public void checkChoose() {
        boolean choose = false;
        if (cboHinhThucGiamGia.getSelectedIndex() == 0) {
            choose = true;
        }
        for (int i = 0; i < tblDanhSachSanPham.getRowCount(); i++) {
            tblDanhSachSanPham.setValueAt(choose, i, 0);
        }
    }

    public void timKiem() {
        if (cboApDung.getSelectedIndex() != 0) {
            cboApDung.setSelectedIndex(0);
        }
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSanPham.getModel();
        model.setRowCount(0);
        try {
            List<model.SanPham> list = spDAO.selectByKeyword(txtTimKiem.getText());
            for (model.SanPham SP : list) {
                Object[] rows = {false, SP.getMaSP(), SP.getTenSP(), lgDAO.selectById(SP.getMaLoaiGiay()).getTenLoaiGiay(), format.format(SP.getDonGiaNhap()), format.format(SP.getDonGiaBan())};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillTableSanPham(String tenLoaiGiay) {
        cboHinhThucGiamGia.setSelectedIndex(1);
        DefaultTableModel model = (DefaultTableModel) tblDanhSachSanPham.getModel();
        model.setRowCount(0);
        try {
            List<model.SanPham> list;
            if (tenLoaiGiay.equals("Chọn loại giày")) {
                list = spDAO.selectAll();
            } else {
                String maLG = lgDAO.selectByName(tenLoaiGiay).getMaLoaiGiay();
                list = spDAO.selectByShoeType(maLG);
            }
            for (model.SanPham SP : list) {
                boolean km = false;
                if (row != -1) {
                    if (SP.getMaKM() != null && SP.getMaKM().equals(tblDanhSachKhuyenMai.getValueAt(row, 1) + "")) {
                        km = true;
                    }
                }
                Object[] rows = {km, SP.getMaSP(), SP.getTenSP(), lgDAO.selectById(SP.getMaLoaiGiay()).getTenLoaiGiay(), format.format(SP.getDonGiaNhap()), format.format(SP.getDonGiaBan())};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillTableKhuyenMai() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachKhuyenMai.getModel();
        model.setRowCount(0);
        try {
            List<model.KhuyenMai> list = kmDAO.selectAll();
            int i = 0;
            for (model.KhuyenMai km : list) {
                i++;
                String mgg = Integer.parseInt(km.getMucGiamGia()+"") + "%";
                Object[] rows = {i, km.getMaKM(), km.getTenKM(), mgg, XDate.toString(km.getNgayBatDau(), "dd-MM-yyyy"), XDate.toString(km.getNgayKetThuc(), "dd-MM-yyyy")};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setForm(model.KhuyenMai km) {
        txtMaKhuyenMai.setText(km.getMaKM() + "");
        txtTenKhuyenMai.setText(km.getTenKM());
        txtMucGiamGia.setText((km.getMucGiamGia()) + "");
        dcsNgayBatDau.setDate(km.getNgayBatDau());
        dcsNgayKetThuc.setDate(km.getNgayKetThuc());
    }

    model.KhuyenMai getForm() {
        model.KhuyenMai km = new model.KhuyenMai();
        if (!txtMaKhuyenMai.getText().equals("")) {
            km.setMaKM(txtMaKhuyenMai.getText());
        }
        km.setTenKM(txtTenKhuyenMai.getText());
        km.setMucGiamGia(Integer.parseInt(txtMucGiamGia.getText()));
        km.setNgayBatDau(dcsNgayBatDau.getDate());
        km.setNgayKetThuc(dcsNgayKetThuc.getDate());
        return km;
    }

    public String taoMaKM() {
        model.KhuyenMai km = kmDAO.selectDESC().get(0);
        if (km == null) {
            km = new model.KhuyenMai();
            km.setMaKM("KM000");
        }
        String ID = km.getMaKM();
        String number = ID.substring(2, ID.length());
        String newID = "KM";
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

    public void Them() {
        if (Kiemloi() == true) {
            model.KhuyenMai km = getForm();
            km.setMaKM(taoMaKM());
            try {
                kmDAO.insert(km);
                for (int i = 0; i < tblDanhSachSanPham.getRowCount(); i++) {
                    if ((boolean) tblDanhSachSanPham.getValueAt(i, 0)) {
                        model.SanPham sp = spDAO.selectById(tblDanhSachSanPham.getValueAt(i, 1) + "");
                        sp.setMaKM(txtMaKhuyenMai.getText());
                        double giaBan = sp.getDonGiaBan();
                        int mucKM = Integer.parseInt(txtMucGiamGia.getText());
                        double giaKM = (100 - mucKM) * 0.01 * giaBan;
                        sp.setGiaKhuyenMai(giaKM);
                        spDAO.update(sp);
                    }
                }
                this.fillTableKhuyenMai();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                Lammoi();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                System.out.println(e);
            }
        }
    }

    public void capNhat() {
        if (Kiemloi() == true) {
            model.KhuyenMai km = getForm();
            try {
                kmDAO.update(km);
                for (int i = 0; i < tblDanhSachSanPham.getRowCount(); i++) {
                    if ((boolean) tblDanhSachSanPham.getValueAt(i, 0)) {
                        model.SanPham sp = spDAO.selectById(tblDanhSachSanPham.getValueAt(i, 1) + "");
                        sp.setMaKM(txtMaKhuyenMai.getText());
                        double giaBan = sp.getDonGiaBan();
                        int mucKM = Integer.parseInt(txtMucGiamGia.getText());
                        double giaKM = (100 - mucKM) * 0.01 * giaBan;
                        sp.setGiaKhuyenMai(giaKM);
                        spDAO.update(sp);
                    }
                }
                this.fillTableKhuyenMai();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                Lammoi();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                System.out.println(e);
            }
        }
    }

    public void Xoa() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn kmông có quyền xóa khuyến mãi!!");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            String id = txtMaKhuyenMai.getText();
            JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa khuyến mãi này?");

            try {
                List<model.SanPham> spList = spDAO.selectByKM(id);
                if (spList != null) {
                    for (model.SanPham sanPham : spList) {
                        sanPham.setMaKM(null);
                        sanPham.setGiaKhuyenMai(0);
                        spDAO.update(sanPham);
                    }
                }
                kmDAO.delete(id);
                for (int i = 0; i < tblDanhSachSanPham.getRowCount(); i++) {
                    if ((boolean) tblDanhSachSanPham.getValueAt(i, 0)) {
                        model.SanPham sp = spDAO.selectById(tblDanhSachSanPham.getValueAt(i, 1) + "");
                        sp.setMaKM(txtMaKhuyenMai.getText());
                        double giaBan = sp.getDonGiaBan();
                        int mucKM = Integer.parseInt(txtMucGiamGia.getText());
                        double giaKM = (100 - mucKM) * 0.01 * giaBan;
                        sp.setGiaKhuyenMai(giaKM);
                        spDAO.update(sp);
                    }
                }
                this.fillTableKhuyenMai();
                Lammoi();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    public void Lammoi() {
        txtTenKhuyenMai.setText("");
        txtMucGiamGia.setText("");
        dcsNgayBatDau.setDate(null);
        dcsNgayKetThuc.setDate(null);
        txtMaKhuyenMai.setText(taoMaKM());
//        this.row = -1;
//        this.updateStatus();
    }

    public boolean Kiemloi() {
        String err = "";
        if (txtTenKhuyenMai.getText().isEmpty()) {
            err += "Tên khuyến mãi không được để trống\n";
        }
        if (txtMucGiamGia.getText().isEmpty()) {
            err += "Vui lòng nhập mức giảm giá\n";
        } else {
            try {
                double mucgg = Double.parseDouble(txtMucGiamGia.getText());
                if (mucgg<=0) {
                    err+="Mức giảm giá phải lớn hơn 0";
                }
                else if (mucgg>90) {
                    err+="Mức giảm giá không được lớn hơn 90";
                }
            } catch (Exception e) {
                err += "Mức giảm giá sai định dạng\n";
            }
        }
        
        if (dcsNgayBatDau.getDate() == null || dcsNgayBatDau.getDate().before(new Date())) {
            err += "Ngày bắt đầu không được để trống và phải sau ngày hiện tại\n";
        }
        if (dcsNgayKetThuc.getDate() == null || dcsNgayKetThuc.getDate().before(dcsNgayBatDau.getDate())) {
            err += "Ngày kết thúc không được để trống và phải sau ngày bắt đầu\n";
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

        jPanel2 = new javax.swing.JPanel();
        cboApDung = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cboHinhThucGiamGia = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dcsNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dcsNgayKetThuc = new com.toedter.calendar.JDateChooser();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtMaKhuyenMai = new javax.swing.JTextField();
        txtTenKhuyenMai = new javax.swing.JTextField();
        txtMucGiamGia = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSachKhuyenMai = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));
        setPreferredSize(new java.awt.Dimension(1280, 730));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        cboApDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboApDungActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Áp dụng cho :");

        tblDanhSachSanPham.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Tên sản phẩm", "Loại giày", "Giá nhập", "Giá bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSanPham.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblDanhSachSanPham.setSelectionForeground(new java.awt.Color(0, 0, 51));
        jScrollPane1.setViewportView(tblDanhSachSanPham);
        if (tblDanhSachSanPham.getColumnModel().getColumnCount() > 0) {
            tblDanhSachSanPham.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblDanhSachSanPham.getColumnModel().getColumn(0).setMaxWidth(20);
            tblDanhSachSanPham.getColumnModel().getColumn(1).setMinWidth(30);
            tblDanhSachSanPham.getColumnModel().getColumn(1).setPreferredWidth(90);
            tblDanhSachSanPham.getColumnModel().getColumn(1).setMaxWidth(90);
            tblDanhSachSanPham.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblDanhSachSanPham.getColumnModel().getColumn(2).setMaxWidth(200);
            tblDanhSachSanPham.getColumnModel().getColumn(3).setPreferredWidth(170);
            tblDanhSachSanPham.getColumnModel().getColumn(3).setMaxWidth(170);
            tblDanhSachSanPham.getColumnModel().getColumn(4).setPreferredWidth(115);
            tblDanhSachSanPham.getColumnModel().getColumn(4).setMaxWidth(115);
            tblDanhSachSanPham.getColumnModel().getColumn(5).setPreferredWidth(115);
            tblDanhSachSanPham.getColumnModel().getColumn(5).setMaxWidth(115);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Hình thức giảm giá :");

        cboHinhThucGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả sản phẩm", "Tùy chọn" }));
        cboHinhThucGiamGia.setSelectedIndex(1);
        cboHinhThucGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucGiamGiaActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimKiem.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        jPanel4.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 40));

        blackButton1.setText("blackButton1");
        blackButton1.setRadius(48);
        jPanel4.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 8, 304, 44));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboHinhThucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cboHinhThucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("KHUYẾN MÃI");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mã khuyến mãi :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tên khuyến mãi: (*)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Phần trăm giảm giá : (*)");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Ngày bắt đầu : (*)");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Ngày kết thúc : (*)");

        btnThem.setBackground(new java.awt.Color(80, 199, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(null);
        btnThem.setPreferredSize(new java.awt.Dimension(120, 40));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setBackground(new java.awt.Color(80, 199, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setBorder(null);
        btnCapNhat.setPreferredSize(new java.awt.Dimension(120, 40));
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(80, 199, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(null);
        btnXoa.setPreferredSize(new java.awt.Dimension(120, 40));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(80, 199, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setBorder(null);
        btnLamMoi.setPreferredSize(new java.awt.Dimension(120, 40));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtMaKhuyenMai.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcsNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcsNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMucGiamGia)
                    .addComponent(txtTenKhuyenMai)
                    .addComponent(txtMaKhuyenMai))
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel3)
                .addGap(52, 52, 52)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMucGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcsNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcsNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblDanhSachKhuyenMai.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDanhSachKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã khuyến mãi", "Tên khuyến mãi", "Mức giảm giá", "Ngày bắt đầu", "Ngày kết thúc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachKhuyenMai.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblDanhSachKhuyenMai.setSelectionForeground(new java.awt.Color(0, 0, 51));
        tblDanhSachKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhSachKhuyenMai);
        if (tblDanhSachKhuyenMai.getColumnModel().getColumnCount() > 0) {
            tblDanhSachKhuyenMai.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(0).setMaxWidth(50);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(1).setMaxWidth(120);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(2).setPreferredWidth(190);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(2).setMaxWidth(190);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(3).setPreferredWidth(110);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(3).setMaxWidth(110);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(4).setPreferredWidth(110);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(4).setMaxWidth(110);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(5).setPreferredWidth(110);
            tblDanhSachKhuyenMai.getColumnModel().getColumn(5).setMaxWidth(110);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachKhuyenMaiMouseClicked
        row = tblDanhSachKhuyenMai.getSelectedRow();
        model.KhuyenMai km = kmDAO.selectByID("" + tblDanhSachKhuyenMai.getValueAt(row, 1));
        setForm(km);
        fillTableSanPham(cboApDung.getSelectedItem() + "");
    }//GEN-LAST:event_tblDanhSachKhuyenMaiMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        Them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        capNhat();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        Lammoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cboApDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboApDungActionPerformed
        fillTableSanPham(cboApDung.getSelectedItem() + "");
        txtTimKiem.setText("");
    }//GEN-LAST:event_cboApDungActionPerformed

    private void cboHinhThucGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucGiamGiaActionPerformed
        checkChoose();
    }//GEN-LAST:event_cboHinhThucGiamGiaActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        timKiem();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        timKiem();
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboApDung;
    private javax.swing.JComboBox<String> cboHinhThucGiamGia;
    private com.toedter.calendar.JDateChooser dcsNgayBatDau;
    private com.toedter.calendar.JDateChooser dcsNgayKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDanhSachKhuyenMai;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTextField txtMaKhuyenMai;
    private javax.swing.JTextField txtMucGiamGia;
    private javax.swing.JTextField txtTenKhuyenMai;
    private swing.TextFieldAnimation txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
