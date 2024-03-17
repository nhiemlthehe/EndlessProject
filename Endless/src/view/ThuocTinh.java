/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Auth;
import util.KichThuocDAO;
import util.MauSacDAO;

/**
 *
 * @author 12345
 */
public class ThuocTinh extends javax.swing.JPanel {

    MauSacDAO msDAO = new MauSacDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    int row = -1;
    int row1 = -1;

    /**
     * Creates new form ThuocTinh
     */
    public ThuocTinh() {
        initComponents();
        fillTableMauSac();
        fillTableKichThuoc();
        initTableModel();
      
    }

        public void initTableModel() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < tblMauSac.getColumnCount(); i++) {
            tblMauSac.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblKichThuoc.getColumnCount(); i++) {
            tblKichThuoc.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }
        
    void fillTableMauSac() {
        DefaultTableModel model = (DefaultTableModel) tblMauSac.getModel();
        model.setRowCount(0);
        try {
            List<model.MauSac> list = msDAO.selectAll();
            for (model.MauSac ms : list) {
                Object[] rows = {ms.getMaMauSac(), ms.getTenMauSac(), ms.getMoTa()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void fillTableKichThuoc() {
        DefaultTableModel model = (DefaultTableModel) tblKichThuoc.getModel();
        model.setRowCount(0);
        try {
            List<model.KichThuoc> list = ktDAO.selectAll();
            for (model.KichThuoc kt : list) {
                Object[] rows = {kt.getMaKichThuoc(), kt.getTenKichThuoc(), kt.getMoTa()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

   

    void setFormMauSac(model.MauSac ms) {
        txtTenMau.setText(ms.getTenMauSac());
        txtMoTaMau.setText(ms.getMoTa());
    }

    model.MauSac getFormMauSac() {
        model.MauSac ms = new model.MauSac();
        ms.setTenMauSac(txtTenMau.getText());
        ms.setMoTa(txtMoTaMau.getText());
        return ms;
    }

    void setFormKichThuoc(model.KichThuoc kt) {
        txtKichThuoc.setText(kt.getTenKichThuoc());
        txtMoTaKt.setText(kt.getMoTa());
    }

    model.KichThuoc getFormKichThuoc() {
        model.KichThuoc kt = new model.KichThuoc();
        kt.setTenKichThuoc(txtKichThuoc.getText());
        kt.setMoTa(txtMoTaKt.getText());
        return kt;
    }
    
     public String taoMaMS() {
        model.MauSac ms = msDAO.selectDESC().get(0);
        if (ms == null) {
            ms = new model.MauSac();
            ms.setMaMauSac("MS000");
        }
        String oldID = ms.getMaMauSac();
        String number = oldID.substring(2, oldID.length());
        String newID = "MS";
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
     
      public String taoMaKT() {
        model.KichThuoc kt = ktDAO.selectDESC().get(0);
        if (kt == null) {
            kt = new model.KichThuoc();
            kt.setMaKichThuoc("KT000");
        }
        String oldID = kt.getMaKichThuoc();
        String number = oldID.substring(2, oldID.length());
        String newID = "KT";
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
 

    void ThemMauSac() {
        if (KiemLoiMau() == true) {
            model.MauSac ms = getFormMauSac();
            ms.setMaMauSac(taoMaMS());
            try {
                msDAO.insert(ms);
                this.fillTableMauSac();
                JOptionPane.showMessageDialog(this, "Thêm màu sắc thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm màu sắc thất bại!");
                System.out.println(e);
            }
        }
    }

    void ThemKichThuoc() {
        if (KiemLoiKichThuoc() == true) {
            model.KichThuoc kt = getFormKichThuoc();
            kt.setMaKichThuoc(taoMaKT());
            try {
                ktDAO.insert(kt);
                this.fillTableKichThuoc();
                JOptionPane.showMessageDialog(this, "Thêm kích thước thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm kích thước thất bại!");
                System.out.println(e);
            }
        }
    }

    

    void CapNhatMauSac() {
        if (KiemLoiMau() == true) {
            model.MauSac ms = getFormMauSac();
            ms.setMaMauSac((String) (tblMauSac.getValueAt(row, 0) + ""));
            try {
                msDAO.update(ms);
                this.fillTableMauSac();
                JOptionPane.showMessageDialog(this, "Cập nhật màu sắc thành công");
                LamMoiMauSac();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật màu sắc thất bại!");
                System.out.println(e);
            }
        }
    }

    void CapNhatKichThuoc() {
        if (KiemLoiKichThuoc() == true) {
            model.KichThuoc kt = getFormKichThuoc();
            kt.setMaKichThuoc((String) (tblKichThuoc.getValueAt(row1, 0) + ""));
            try {
                ktDAO.update(kt);
                this.fillTableKichThuoc();
                JOptionPane.showMessageDialog(this, "Cập nhật kích thước thành công");
                LamMoiKichThuoc();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật kích thước thất bại!");
                System.out.println(e);
            }
        }
    }

    

    void XoaMauSac() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa thuộc tính màu sắc !!");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            String tenms = txtTenMau.getText();
            int yesNo = JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa thuộc tính màu sắc này?");
            if (yesNo == JOptionPane.YES_NO_OPTION) {
                try {
                    msDAO.delete(tenms);
                    this.fillTableMauSac();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    LamMoiMauSac();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        }
    }

    void XoaKichThuoc() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa thuộc tính kích thước !!");
        } else if (row1 == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            String tenkt = txtKichThuoc.getText();
            int yesNo = JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa thuộc tính kích thước này?");
            if (yesNo == JOptionPane.YES_NO_OPTION) {
                try {
                    ktDAO.delete(tenkt);
                    this.fillTableKichThuoc();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    LamMoiKichThuoc();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        }
    }

    

    void LamMoiMauSac() {
        txtTenMau.setText("");
        txtMoTaMau.setText("");
        this.row = -1;
        tblMauSac.clearSelection();
    }

    void LamMoiKichThuoc() {
        txtKichThuoc.setText("");
        txtMoTaKt.setText("");
        this.row1 = -1;
        tblKichThuoc.clearSelection();
    }

    
    public boolean KiemLoiMau() {
        String err = "";
        if (txtTenMau.getText().isEmpty()) {
            err += "Tên màu không được bỏ trống\n";
        }
        if (!err.isEmpty()) {
            JOptionPane.showMessageDialog(this, err);
            return false;
        }
        return true;
    }

    public boolean KiemLoiKichThuoc() {
        String err = "";
        if (txtKichThuoc.getText().isEmpty()) {
            err += "Tên kích thước không được bỏ trống\n";
        }
        if (!err.isEmpty()) {
            JOptionPane.showMessageDialog(this, err);
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

        jPanel1 = new javax.swing.JPanel();
        txtMoTaMau = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTenMau = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnThemMs1 = new javax.swing.JButton();
        btnCapNhatMs1 = new javax.swing.JButton();
        btnXoaMs1 = new javax.swing.JButton();
        btnLamMoiMs1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMauSac = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKichThuoc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMoTaKt = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKichThuoc = new javax.swing.JTable();
        btnThemKt = new javax.swing.JButton();
        btnCapNhatKt = new javax.swing.JButton();
        btnXoaKt = new javax.swing.JButton();
        btnLamMoiKt = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));
        setPreferredSize(new java.awt.Dimension(1280, 730));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(630, 690));
        jPanel1.setMinimumSize(new java.awt.Dimension(630, 690));
        jPanel1.setPreferredSize(new java.awt.Dimension(630, 690));

        txtMoTaMau.setColumns(20);
        txtMoTaMau.setRows(5);
        txtMoTaMau.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtMoTaMau.setMaximumSize(new java.awt.Dimension(232, 84));
        txtMoTaMau.setMinimumSize(new java.awt.Dimension(232, 84));
        txtMoTaMau.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMoTaMauCaretUpdate(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(0, 153, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 255));
        jLabel7.setText("Màu Sắc");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tên màu :(*)");

        txtTenMau.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Mô tả :");

        btnThemMs1.setBackground(new java.awt.Color(80, 199, 255));
        btnThemMs1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemMs1.setForeground(new java.awt.Color(255, 255, 255));
        btnThemMs1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThemMs1.setText("Thêm");
        btnThemMs1.setBorder(null);
        btnThemMs1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMsActionPerformed(evt);
            }
        });

        btnCapNhatMs1.setBackground(new java.awt.Color(80, 199, 255));
        btnCapNhatMs1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhatMs1.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatMs1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhatMs1.setText("Cập nhật");
        btnCapNhatMs1.setBorder(null);
        btnCapNhatMs1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatMsActionPerformed(evt);
            }
        });

        btnXoaMs1.setBackground(new java.awt.Color(80, 199, 255));
        btnXoaMs1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaMs1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaMs1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        btnXoaMs1.setText("Xóa");
        btnXoaMs1.setBorder(null);
        btnXoaMs1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMsActionPerformed(evt);
            }
        });

        btnLamMoiMs1.setBackground(new java.awt.Color(80, 199, 255));
        btnLamMoiMs1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoiMs1.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiMs1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnLamMoiMs1.setText("Làm mới");
        btnLamMoiMs1.setBorder(null);
        btnLamMoiMs1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiMsActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin màu sắc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblMauSac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã màu sắc", "Tên màu sắc", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMauSac.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMauSacMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblMauSac);
        if (tblMauSac.getColumnModel().getColumnCount() > 0) {
            tblMauSac.getColumnModel().getColumn(0).setMinWidth(100);
            tblMauSac.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMauSac.getColumnModel().getColumn(0).setMaxWidth(100);
            tblMauSac.getColumnModel().getColumn(1).setMinWidth(110);
            tblMauSac.getColumnModel().getColumn(1).setPreferredWidth(110);
            tblMauSac.getColumnModel().getColumn(1).setMaxWidth(110);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnThemMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnCapNhatMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnXoaMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLamMoiMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(txtTenMau)
                            .addComponent(txtMoTaMau, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(242, 242, 242))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addComponent(txtTenMau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(txtMoTaMau, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaMs1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setMaximumSize(new java.awt.Dimension(628, 690));
        jPanel2.setMinimumSize(new java.awt.Dimension(628, 690));
        jPanel2.setPreferredSize(new java.awt.Dimension(628, 690));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("Kích Thước");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Kích thước :(*)");

        txtKichThuoc.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mô tả :");

        txtMoTaKt.setColumns(20);
        txtMoTaKt.setRows(5);
        txtMoTaKt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtMoTaKt.setMaximumSize(new java.awt.Dimension(232, 84));
        txtMoTaKt.setMinimumSize(new java.awt.Dimension(232, 84));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin kích thước", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblKichThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã kích thước", "Kích thước", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKichThuoc.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblKichThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKichThuocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKichThuoc);
        if (tblKichThuoc.getColumnModel().getColumnCount() > 0) {
            tblKichThuoc.getColumnModel().getColumn(0).setMinWidth(100);
            tblKichThuoc.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblKichThuoc.getColumnModel().getColumn(0).setMaxWidth(100);
            tblKichThuoc.getColumnModel().getColumn(1).setMinWidth(100);
            tblKichThuoc.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblKichThuoc.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnThemKt.setBackground(new java.awt.Color(80, 199, 255));
        btnThemKt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemKt.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThemKt.setText("Thêm");
        btnThemKt.setBorder(null);
        btnThemKt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKtActionPerformed(evt);
            }
        });

        btnCapNhatKt.setBackground(new java.awt.Color(80, 199, 255));
        btnCapNhatKt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhatKt.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatKt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhatKt.setText("Cập nhật");
        btnCapNhatKt.setBorder(null);
        btnCapNhatKt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatKtActionPerformed(evt);
            }
        });

        btnXoaKt.setBackground(new java.awt.Color(80, 199, 255));
        btnXoaKt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaKt.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        btnXoaKt.setText("Xóa");
        btnXoaKt.setBorder(null);
        btnXoaKt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKtActionPerformed(evt);
            }
        });

        btnLamMoiKt.setBackground(new java.awt.Color(80, 199, 255));
        btnLamMoiKt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoiKt.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiKt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnLamMoiKt.setText("Làm mới");
        btnLamMoiKt.setBorder(null);
        btnLamMoiKt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiKtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnThemKt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnCapNhatKt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXoaKt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnLamMoiKt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(txtMoTaKt, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                            .addComponent(txtKichThuoc))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addComponent(txtKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMoTaKt, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatKt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaKt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiKt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMauSacMouseClicked
        row = tblMauSac.getSelectedRow();
        if (evt.getClickCount() == 1) {
            String id = (String) tblMauSac.getValueAt(row, 0);
            model.MauSac ms = msDAO.selectById(id);
            setFormMauSac(ms);
        }
    }//GEN-LAST:event_tblMauSacMouseClicked

    private void btnLamMoiMsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiMsActionPerformed
        LamMoiMauSac();
    }//GEN-LAST:event_btnLamMoiMsActionPerformed

    private void btnXoaMsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMsActionPerformed
        XoaMauSac();
    }//GEN-LAST:event_btnXoaMsActionPerformed

    private void btnCapNhatMsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatMsActionPerformed
        CapNhatMauSac();
    }//GEN-LAST:event_btnCapNhatMsActionPerformed

    private void btnThemMsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMsActionPerformed
        ThemMauSac();
    }//GEN-LAST:event_btnThemMsActionPerformed

    private void btnLamMoiKtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiKtActionPerformed
        LamMoiKichThuoc();
    }//GEN-LAST:event_btnLamMoiKtActionPerformed

    private void btnXoaKtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKtActionPerformed
        XoaKichThuoc();
    }//GEN-LAST:event_btnXoaKtActionPerformed

    private void btnCapNhatKtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatKtActionPerformed
        CapNhatKichThuoc();
    }//GEN-LAST:event_btnCapNhatKtActionPerformed

    private void btnThemKtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKtActionPerformed
        ThemKichThuoc();
    }//GEN-LAST:event_btnThemKtActionPerformed

    private void tblKichThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKichThuocMouseClicked
        row1 = tblKichThuoc.getSelectedRow();
        if (evt.getClickCount() == 1) {
            String id = (String) tblKichThuoc.getValueAt(row1, 0);
            model.KichThuoc kt = ktDAO.selectByID(id);
            setFormKichThuoc(kt);
        }
    }//GEN-LAST:event_tblKichThuocMouseClicked

    private void txtMoTaMauCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMoTaMauCaretUpdate
     

    }//GEN-LAST:event_txtMoTaMauCaretUpdate

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_formPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatKt;
    private javax.swing.JButton btnCapNhatMs1;
    private javax.swing.JButton btnLamMoiKt;
    private javax.swing.JButton btnLamMoiMs1;
    private javax.swing.JButton btnThemKt;
    private javax.swing.JButton btnThemMs1;
    private javax.swing.JButton btnXoaKt;
    private javax.swing.JButton btnXoaMs1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tblKichThuoc;
    private javax.swing.JTable tblMauSac;
    private javax.swing.JTextField txtKichThuoc;
    private javax.swing.JTextArea txtMoTaKt;
    private javax.swing.JTextArea txtMoTaMau;
    private javax.swing.JTextField txtTenMau;
    // End of variables declaration//GEN-END:variables
}
