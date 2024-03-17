/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import chart.ModelChart;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.ThongKeDoanhThu;
import model.ThongKeSanPham;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.LoaiGiayDAO;
import util.ThongKeDoanhThuDAO;
import util.ThongKeSanPhamDAO;
import util.XDate;
import util.XNumber;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class ThongKe extends javax.swing.JPanel {

    LoaiGiayDAO lgDAO = new LoaiGiayDAO();
    ThongKeSanPhamDAO tkspDAO = new ThongKeSanPhamDAO();
    ThongKeDoanhThuDAO tkdtDAO = new ThongKeDoanhThuDAO();
    XDate XD = new XDate();
    XNumber XN = new XNumber();

    public ThongKe() {
        initComponents();
        initForm();
    }

    public void initForm() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);

        for (int i = 0; i < tblRevenue.getColumnCount(); i++) {
            tblRevenue.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblProduct.getColumnCount(); i++) {
            tblProduct.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        initData();
        initComboboxLoaiGiay();
        fillToTableTKSP();
        fillToTableTKDT();
        createrevenueChart();
    }

    public void createrevenueChart() {
        revenueChart.setBackground(new Color(250, 250, 250));
        revenueChart.addLegend("Doanh thu cao nhất", new Color(245, 189, 135));
        revenueChart.addLegend("Doanh thu thấp nhất", new Color(135, 189, 245));
        revenueChart.addLegend("Doanh thu trung bình", new Color(189, 135, 245));
        revenueChart.addLegend("Tổng doanh thu", new Color(139, 229, 222));

        revenueChart.addData(new ModelChart("January", new double[]{500, 200, 80, 89}));

        revenueChart.start();

    }

    public void addDatarevenueChart() {
        revenueChart.clear();
        try {
            String ngayBatDau = XD.toString(dcsNBD.getDate(), "yyyy-MM-dd");
            String ngayKetThuc = XD.toString(dcsNKT.getDate(), "yyyy-MM-dd");
            String cheDoXem = cboViewMode1.getSelectedItem().toString();
            List<ThongKeDoanhThu> list = tkdtDAO.execThongKeDoanhThu(ngayBatDau, ngayKetThuc, cheDoXem);
            for (model.ThongKeDoanhThu tkdt : list) {
                revenueChart.addData(new ModelChart(tkdt.getThoiGian(), new double[]{tkdt.getDoanhThuCaoNhat(), tkdt.getDoanhThuThapNhat(), tkdt.getDoanhThuTrungBinh(), tkdt.getTongDoanhThu()}));
            }
        } catch (Exception e) {
            return;
        }
        revenueChart.start();
    }

    public void initData() {
        lblTSSO.setText(tkspDAO.countTongSoSanPham() + "");
        lblCTSP.setText(tkspDAO.countTongSoChiTiet() + "");
        lblSPDB.setText(tkspDAO.countSoLuongSanPhamDaBan() + "");
        lblSPTK.setText(tkspDAO.countSoLuongSanPhamTrongKho() + "");
        lblSPHH.setText(tkspDAO.countSoLuongSanPhamHetHang() + "");
        lblSPSH.setText(tkspDAO.countSoLuongSanPhamSapHetHang() + "");
        lblTongDoanhThu.setText(XN.formatDecimal(tkdtDAO.countTongDoanhThu()) + "");
        lblDoanhThuNamNay.setText(XN.formatDecimal(tkdtDAO.countDoanhThuNamNay()) + "");
        lblDoanhThuThangNay.setText(XN.formatDecimal(tkdtDAO.countDoanhThuThangNay()) + "");
        lblDoanhThu7NgayGanNhat.setText(XN.formatDecimal(tkdtDAO.countDoanhThu7NgayGanNhat()) + "");
        lblDoanhThuHomNay.setText(XN.formatDecimal(tkdtDAO.countDoanhThuHomNay()) + "");
        dcsEndDate.setDate(new Date());
        dcsNKT.setDate(new Date());
        Date startDate = new Date("2023/01/01");
        dcsStartDate.setDate(startDate);
        dcsNBD.setDate(startDate);

    }

    public void initComboboxLoaiGiay() {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboProductType1.getModel();
        cboProductType1.removeAllItems();
        cboModel.addElement("Chọn loại giày");
        List<model.LoaiGiay> sp = lgDAO.selectAll();
        for (model.LoaiGiay LoaiGiay : sp) {
            cboModel.addElement(lgDAO.selectById(LoaiGiay.getMaLoaiGiay()).getTenLoaiGiay());
        }
    }

    public void fillToTableTKSP() {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        try {
            String loaiSP = cboProductType1.getSelectedItem().toString();
            if (loaiSP == "Chọn loại giày") {
                loaiSP = null;
            } else {
                loaiSP = cboProductType1.getSelectedItem().toString();
            }
            int i = 0;
            String hinhThuc = cboMode1.getSelectedItem().toString();
            List<ThongKeSanPham> list = tkspDAO.execThongKeSanPham(loaiSP, hinhThuc);
            for (model.ThongKeSanPham tksp : list) {
                i++;
                Object[] rows = {i, tksp.getLoaiHang(), tksp.getTenSP(), tksp.getTonKho(), tksp.getDaBan(), XN.formatDecimal(tksp.getDoanhThu())};
                model.addRow(rows);
            }
        } catch (Exception e) {
            return;
        }
    }

    public void fillToTableTKDT() {
        DefaultTableModel model = (DefaultTableModel) tblRevenue.getModel();
        model.setRowCount(0);
        try {
            String ngayBatDau = XD.toString(dcsStartDate.getDate(), "yyyy-MM-dd");
            String ngayKetThuc = XD.toString(dcsEndDate.getDate(), "yyyy-MM-dd");
            String cheDoXem = cboViewMode.getSelectedItem().toString();
            List<ThongKeDoanhThu> list = tkdtDAO.execThongKeDoanhThu(ngayBatDau, ngayKetThuc, cheDoXem);
            for (model.ThongKeDoanhThu tkdt : list) {
                Object[] rows = {tkdt.getThoiGian(), XN.formatDecimal(tkdt.getDoanhThuThapNhat()), XN.formatDecimal(tkdt.getDoanhThuCaoNhat()),
                    XN.formatDecimal(tkdt.getDoanhThuTrungBinh()), XN.formatDecimal(tkdt.getTongDoanhThu())};
                model.addRow(rows);
            }
        } catch (Exception e) {
            return;
        }
    }

    public void xuatDanhSach(JTable table, File file) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");

            // Tạo một kiểu ô định dạng để căn giữa văn bản
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Tạo dòng tiêu đề
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(table.getColumnName(i));
                cell.setCellStyle(cellStyle);
            }

            // Tạo dòng dữ liệu
            for (int i = 0; i < table.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object value = table.getValueAt(i, j);
                    if (tab.getSelectedIndex() == 0) {
                        if (j == 5) {
                            value = String.valueOf(value).replaceAll(",", "");
                            value = Integer.parseInt(value + "");
                        }
                    } else {
                        if (j == 1 || j == 2 || j == 3 || j == 4) {
                            value = String.valueOf(value).replaceAll(",", "");
                            value = Integer.parseInt(value + "");
                        }
                    }

                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                        cell.setCellStyle(cellStyle);
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(value == null ? "" : value.toString());
                        cell.setCellStyle(cellStyle);
                    }
                }
            }

            // Tự động điều chỉnh chiều rộng của cột dựa trên nội dung
            for (int i = 0; i < table.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Lưu file
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }

            // Hiển thị thông báo
            int open = JOptionPane.showConfirmDialog(
                    null,
                    "In thành công, bạn muốn mở nó không?",
                    "Thông báo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (open == JOptionPane.YES_OPTION) {
                Desktop.getDesktop().browse(file.toURI());
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Có lỗi xảy ra khi lưu file.",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
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

        btngLuaChon = new javax.swing.ButtonGroup();
        btgViewMode = new javax.swing.ButtonGroup();
        tab = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cboProductType1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboMode1 = new javax.swing.JComboBox<>();
        btnPrintExcelRevenue = new javax.swing.JButton();
        customGradientPanel1 = new controller.CustomGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSPSH = new javax.swing.JLabel();
        customGradientPanel2 = new controller.CustomGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTSSO = new javax.swing.JLabel();
        customGradientPanel3 = new controller.CustomGradientPanel();
        jLabel3 = new javax.swing.JLabel();
        lblSPDB = new javax.swing.JLabel();
        customGradientPanel4 = new controller.CustomGradientPanel();
        jLabel4 = new javax.swing.JLabel();
        lblSPTK = new javax.swing.JLabel();
        customGradientPanel5 = new controller.CustomGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        lblSPHH = new javax.swing.JLabel();
        customGradientPanel11 = new controller.CustomGradientPanel();
        jLabel12 = new javax.swing.JLabel();
        lblCTSP = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        dcsStartDate = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        dcsEndDate = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        cboViewMode = new javax.swing.JComboBox<>();
        btnPrintExcelRevenue1 = new javax.swing.JButton();
        customGradientPanel6 = new controller.CustomGradientPanel();
        jLabel15 = new javax.swing.JLabel();
        lblDoanhThuHomNay = new javax.swing.JLabel();
        customGradientPanel7 = new controller.CustomGradientPanel();
        jLabel17 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        customGradientPanel8 = new controller.CustomGradientPanel();
        jLabel19 = new javax.swing.JLabel();
        lblDoanhThuNamNay = new javax.swing.JLabel();
        customGradientPanel9 = new controller.CustomGradientPanel();
        jLabel21 = new javax.swing.JLabel();
        lblDoanhThuThangNay = new javax.swing.JLabel();
        customGradientPanel10 = new controller.CustomGradientPanel();
        jLabel23 = new javax.swing.JLabel();
        lblDoanhThu7NgayGanNhat = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRevenue = new javax.swing.JTable();
        btnPrintExcelRevenue2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        cboViewMode1 = new javax.swing.JComboBox<>();
        revenueChart = new chart.Chart();
        dcsNBD = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dcsNKT = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1280, 750));
        setMinimumSize(new java.awt.Dimension(1280, 750));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1280, 750));

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Loại hàng", "Tên sản phẩm", "Tồn kho", "Đã bán", "Doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.setRowHeight(20);
        tblProduct.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblProduct.setSelectionForeground(new java.awt.Color(0, 0, 51));
        jScrollPane2.setViewportView(tblProduct);
        if (tblProduct.getColumnModel().getColumnCount() > 0) {
            tblProduct.getColumnModel().getColumn(0).setResizable(false);
            tblProduct.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblProduct.getColumnModel().getColumn(1).setResizable(false);
            tblProduct.getColumnModel().getColumn(1).setPreferredWidth(130);
            tblProduct.getColumnModel().getColumn(2).setResizable(false);
            tblProduct.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblProduct.getColumnModel().getColumn(3).setResizable(false);
            tblProduct.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblProduct.getColumnModel().getColumn(4).setResizable(false);
            tblProduct.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblProduct.getColumnModel().getColumn(5).setResizable(false);
            tblProduct.getColumnModel().getColumn(5).setPreferredWidth(130);
        }

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phân loại", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabel10.setText("Loại sản phẩm: ");

        cboProductType1.setBackground(new java.awt.Color(255, 255, 255));
        cboProductType1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboProductType1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboProductTypeMouseClicked(evt);
            }
        });
        cboProductType1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProductTypeActionPerformed(evt);
            }
        });
        cboProductType1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboProductTypePropertyChange(evt);
            }
        });

        jLabel11.setText("Hình thức thống kê: ");

        cboMode1.setBackground(new java.awt.Color(255, 255, 255));
        cboMode1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Top sản phẩm có doanh thu cao nhất", "Top sản phẩm có doanh thu thấp nhất", "Top sản phẩm được mua nhiều nhất", "Top sản phẩm được mua ít nhất", "Top sản phẩm tồn kho nhiều nhất", "Top sản phẩm tồn kho ít nhất" }));
        cboMode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboModeActionPerformed(evt);
            }
        });
        cboMode1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboModePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboProductType1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(cboMode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cboProductType1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPrintExcelRevenue.setBackground(new java.awt.Color(80, 199, 255));
        btnPrintExcelRevenue.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnPrintExcelRevenue.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintExcelRevenue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/export.png"))); // NOI18N
        btnPrintExcelRevenue.setText("Xuất file excel");
        btnPrintExcelRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintExcelRevenueActionPerformed(evt);
            }
        });

        customGradientPanel1.setColorEnd(new java.awt.Color(255, 102, 0));
        customGradientPanel1.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sản phẩm sắp hết hàng");
        customGradientPanel1.add(jLabel1);
        jLabel1.setBounds(0, 110, 190, 16);

        lblSPSH.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblSPSH.setForeground(new java.awt.Color(255, 255, 0));
        lblSPSH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSPSH.setText("0");
        customGradientPanel1.add(lblSPSH);
        lblSPSH.setBounds(0, 30, 190, 70);

        customGradientPanel2.setColorEnd(new java.awt.Color(0, 255, 255));
        customGradientPanel2.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tổng số sản phẩm");
        customGradientPanel2.add(jLabel2);
        jLabel2.setBounds(1, 110, 190, 17);

        lblTSSO.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblTSSO.setForeground(new java.awt.Color(255, 255, 0));
        lblTSSO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTSSO.setText("0");
        customGradientPanel2.add(lblTSSO);
        lblTSSO.setBounds(0, 30, 190, 70);

        customGradientPanel3.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Số lượng đã bán");
        customGradientPanel3.add(jLabel3);
        jLabel3.setBounds(0, 110, 190, 16);

        lblSPDB.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblSPDB.setForeground(new java.awt.Color(255, 255, 0));
        lblSPDB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSPDB.setText("0");
        customGradientPanel3.add(lblSPDB);
        lblSPDB.setBounds(0, 30, 190, 70);

        customGradientPanel4.setColorEnd(new java.awt.Color(204, 0, 153));
        customGradientPanel4.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Số lượng trong kho");
        customGradientPanel4.add(jLabel4);
        jLabel4.setBounds(0, 110, 190, 16);

        lblSPTK.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblSPTK.setForeground(new java.awt.Color(255, 255, 0));
        lblSPTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSPTK.setText("0");
        customGradientPanel4.add(lblSPTK);
        lblSPTK.setBounds(0, 30, 190, 70);

        customGradientPanel5.setColorEnd(new java.awt.Color(232, 0, 46));
        customGradientPanel5.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sản phẩm đã hết hàng");
        customGradientPanel5.add(jLabel5);
        jLabel5.setBounds(0, 110, 190, 16);

        lblSPHH.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblSPHH.setForeground(new java.awt.Color(255, 255, 0));
        lblSPHH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSPHH.setText("0");
        customGradientPanel5.add(lblSPHH);
        lblSPHH.setBounds(0, 30, 190, 70);

        customGradientPanel11.setColorEnd(new java.awt.Color(0, 255, 255));
        customGradientPanel11.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Chi tiết sản phẩm");
        customGradientPanel11.add(jLabel12);
        jLabel12.setBounds(1, 110, 190, 17);

        lblCTSP.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblCTSP.setForeground(new java.awt.Color(255, 255, 0));
        lblCTSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCTSP.setText("0");
        customGradientPanel11.add(lblCTSP);
        lblCTSP.setBounds(0, 30, 190, 70);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrintExcelRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(customGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(customGradientPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(customGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(customGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(customGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(customGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(customGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customGradientPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customGradientPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customGradientPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(btnPrintExcelRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        tab.addTab("Sản phẩm", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane3.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phân loại", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabel26.setText("Từ ngày: ");

        dcsStartDate.setBackground(new java.awt.Color(255, 255, 255));
        dcsStartDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcsStartDatePropertyChange(evt);
            }
        });

        jLabel27.setText("Đến ngày");

        dcsEndDate.setBackground(new java.awt.Color(255, 255, 255));
        dcsEndDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcsEndDatePropertyChange(evt);
            }
        });

        jLabel25.setText("Chế độ xem");

        cboViewMode.setBackground(new java.awt.Color(255, 255, 255));
        cboViewMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo tháng", "Theo năm" }));
        cboViewMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboViewModeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcsStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcsEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboViewMode, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboViewMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dcsEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dcsStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 149, 700, -1));

        btnPrintExcelRevenue1.setBackground(new java.awt.Color(80, 199, 255));
        btnPrintExcelRevenue1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnPrintExcelRevenue1.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintExcelRevenue1.setText("Xuất file excel");
        btnPrintExcelRevenue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintExcelRevenue1ActionPerformed(evt);
            }
        });
        jPanel5.add(btnPrintExcelRevenue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 171, 177, 48));

        customGradientPanel6.setColorEnd(new java.awt.Color(255, 102, 0));
        customGradientPanel6.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Doanh thu hôm nay");
        customGradientPanel6.add(jLabel15);
        jLabel15.setBounds(0, 110, 200, 16);

        lblDoanhThuHomNay.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblDoanhThuHomNay.setForeground(new java.awt.Color(255, 255, 0));
        lblDoanhThuHomNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuHomNay.setText("0");
        customGradientPanel6.add(lblDoanhThuHomNay);
        lblDoanhThuHomNay.setBounds(0, 30, 200, 70);

        jPanel5.add(customGradientPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(947, 6, 200, 137));

        customGradientPanel7.setColorEnd(new java.awt.Color(0, 255, 255));
        customGradientPanel7.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Tổng doanh thu");
        customGradientPanel7.add(jLabel17);
        jLabel17.setBounds(1, 110, 200, 17);

        lblTongDoanhThu.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblTongDoanhThu.setForeground(new java.awt.Color(255, 255, 0));
        lblTongDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongDoanhThu.setText("0");
        customGradientPanel7.add(lblTongDoanhThu);
        lblTongDoanhThu.setBounds(0, 30, 200, 70);

        jPanel5.add(customGradientPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 6, 200, 137));

        customGradientPanel8.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Doanh thu năm nay");
        customGradientPanel8.add(jLabel19);
        jLabel19.setBounds(0, 110, 200, 16);

        lblDoanhThuNamNay.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblDoanhThuNamNay.setForeground(new java.awt.Color(255, 255, 0));
        lblDoanhThuNamNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuNamNay.setText("0");
        customGradientPanel8.add(lblDoanhThuNamNay);
        lblDoanhThuNamNay.setBounds(0, 30, 200, 70);

        jPanel5.add(customGradientPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 6, 200, 137));

        customGradientPanel9.setColorEnd(new java.awt.Color(204, 0, 153));
        customGradientPanel9.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Doanh thu tháng này");
        customGradientPanel9.add(jLabel21);
        jLabel21.setBounds(0, 110, 200, 16);

        lblDoanhThuThangNay.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblDoanhThuThangNay.setForeground(new java.awt.Color(255, 255, 0));
        lblDoanhThuThangNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuThangNay.setText("0");
        customGradientPanel9.add(lblDoanhThuThangNay);
        lblDoanhThuThangNay.setBounds(0, 30, 200, 70);

        jPanel5.add(customGradientPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 6, 200, 137));

        customGradientPanel10.setColorEnd(new java.awt.Color(232, 0, 46));
        customGradientPanel10.setColorStart(new java.awt.Color(51, 51, 51));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Doanh thu 7 ngày gần nhất");
        customGradientPanel10.add(jLabel23);
        jLabel23.setBounds(0, 110, 200, 16);

        lblDoanhThu7NgayGanNhat.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        lblDoanhThu7NgayGanNhat.setForeground(new java.awt.Color(255, 255, 0));
        lblDoanhThu7NgayGanNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThu7NgayGanNhat.setText("0");
        customGradientPanel10.add(lblDoanhThu7NgayGanNhat);
        lblDoanhThu7NgayGanNhat.setBounds(0, 30, 200, 70);

        jPanel5.add(customGradientPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 6, 200, 137));

        tblRevenue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblRevenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thời gian", "Doanh thu thấp nhất", "Doanh thu cao nhất", "Doanh thu trung bình", "Tổng doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRevenue.setRowHeight(20);
        tblRevenue.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblRevenue.setSelectionForeground(new java.awt.Color(0, 0, 51));
        jScrollPane3.setViewportView(tblRevenue);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 1130, 450));

        btnPrintExcelRevenue2.setBackground(new java.awt.Color(1, 167, 104));
        btnPrintExcelRevenue2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnPrintExcelRevenue2.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintExcelRevenue2.setText("Xuất file excel");
        jPanel5.add(btnPrintExcelRevenue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 171, 177, 48));

        jTabbedPane3.addTab("Bảng", jPanel5);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        cboViewMode1.setBackground(new java.awt.Color(255, 255, 255));
        cboViewMode1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo tháng", "Theo năm" }));
        cboViewMode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboViewMode1ActionPerformed(evt);
            }
        });

        dcsNBD.setBackground(new java.awt.Color(255, 255, 255));
        dcsNBD.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcsNBDPropertyChange(evt);
            }
        });

        jLabel8.setText("Từ ngày: ");

        dcsNKT.setBackground(new java.awt.Color(255, 255, 255));
        dcsNKT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcsNKTPropertyChange(evt);
            }
        });

        jLabel9.setText("-");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcsNBD, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcsNKT, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboViewMode1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(revenueChart, javax.swing.GroupLayout.PREFERRED_SIZE, 1069, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dcsNBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dcsNKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cboViewMode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(revenueChart, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Biểu đồ", jPanel9);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        tab.addTab("Doanh thu", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboViewModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboViewModeActionPerformed
        fillToTableTKDT();
    }//GEN-LAST:event_cboViewModeActionPerformed

    private void dcsEndDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcsEndDatePropertyChange
        fillToTableTKDT();
    }//GEN-LAST:event_dcsEndDatePropertyChange

    private void dcsStartDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcsStartDatePropertyChange
        fillToTableTKDT();
    }//GEN-LAST:event_dcsStartDatePropertyChange

    private void dcsNBDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcsNBDPropertyChange
        addDatarevenueChart();
    }//GEN-LAST:event_dcsNBDPropertyChange

    private void dcsNKTPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcsNKTPropertyChange
        addDatarevenueChart();
    }//GEN-LAST:event_dcsNKTPropertyChange

    private void btnPrintExcelRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintExcelRevenueActionPerformed
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", ".xlsx");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save As");
        int value = chooser.showSaveDialog(null);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }
            xuatDanhSach(tblProduct, file);
        };
    }//GEN-LAST:event_btnPrintExcelRevenueActionPerformed

    private void cboModePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboModePropertyChange
        fillToTableTKSP();
    }//GEN-LAST:event_cboModePropertyChange

    private void cboModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboModeActionPerformed
        fillToTableTKSP();
    }//GEN-LAST:event_cboModeActionPerformed

    private void cboProductTypePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboProductTypePropertyChange
        fillToTableTKSP();
    }//GEN-LAST:event_cboProductTypePropertyChange

    private void cboProductTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProductTypeActionPerformed
        fillToTableTKSP();
    }//GEN-LAST:event_cboProductTypeActionPerformed

    private void cboProductTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboProductTypeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProductTypeMouseClicked

    private void btnPrintExcelRevenue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintExcelRevenue1ActionPerformed
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", ".xlsx");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save As");
        int value = chooser.showSaveDialog(null);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }
            xuatDanhSach(tblRevenue, file);
        };
    }//GEN-LAST:event_btnPrintExcelRevenue1ActionPerformed

    private void cboViewMode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboViewMode1ActionPerformed
        addDatarevenueChart();
    }//GEN-LAST:event_cboViewMode1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgViewMode;
    private javax.swing.JButton btnPrintExcelRevenue;
    private javax.swing.JButton btnPrintExcelRevenue1;
    private javax.swing.JButton btnPrintExcelRevenue2;
    private javax.swing.ButtonGroup btngLuaChon;
    private javax.swing.JComboBox<String> cboMode1;
    private javax.swing.JComboBox<String> cboProductType1;
    private javax.swing.JComboBox<String> cboViewMode;
    private javax.swing.JComboBox<String> cboViewMode1;
    private controller.CustomGradientPanel customGradientPanel1;
    private controller.CustomGradientPanel customGradientPanel10;
    private controller.CustomGradientPanel customGradientPanel11;
    private controller.CustomGradientPanel customGradientPanel2;
    private controller.CustomGradientPanel customGradientPanel3;
    private controller.CustomGradientPanel customGradientPanel4;
    private controller.CustomGradientPanel customGradientPanel5;
    private controller.CustomGradientPanel customGradientPanel6;
    private controller.CustomGradientPanel customGradientPanel7;
    private controller.CustomGradientPanel customGradientPanel8;
    private controller.CustomGradientPanel customGradientPanel9;
    private com.toedter.calendar.JDateChooser dcsEndDate;
    private com.toedter.calendar.JDateChooser dcsNBD;
    private com.toedter.calendar.JDateChooser dcsNKT;
    private com.toedter.calendar.JDateChooser dcsStartDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblCTSP;
    private javax.swing.JLabel lblDoanhThu7NgayGanNhat;
    private javax.swing.JLabel lblDoanhThuHomNay;
    private javax.swing.JLabel lblDoanhThuNamNay;
    private javax.swing.JLabel lblDoanhThuThangNay;
    private javax.swing.JLabel lblSPDB;
    private javax.swing.JLabel lblSPHH;
    private javax.swing.JLabel lblSPSH;
    private javax.swing.JLabel lblSPTK;
    private javax.swing.JLabel lblTSSO;
    private javax.swing.JLabel lblTongDoanhThu;
    private chart.Chart revenueChart;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblRevenue;
    // End of variables declaration//GEN-END:variables
}
