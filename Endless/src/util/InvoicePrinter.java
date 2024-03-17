package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import model.HoaDonChiTiet;
import util.ChiTietSanPhamDAO;
import util.HoaDonChiTietDAO;
import util.HoaDonDAO;
import util.PrintUtility;
import util.SanPhamDAO;

public class InvoicePrinter {

    private SanPhamDAO spDAO = new SanPhamDAO();
    private HoaDonDAO hdDAO = new HoaDonDAO();
    private HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
    private ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();

    public void printInvoice(String invoiceId) throws IOException, DocumentException {
        model.HoaDon hoaDon = hdDAO.selectById(invoiceId);
        if (hoaDon != null) {
            String fileName = invoiceId + ".pdf";
            URL resourceUrl = getClass().getResource("/bill/" + fileName);

            File outputFile = new File(resourceUrl.getFile());
            String outputPath = outputFile.getAbsolutePath();
            System.out.println("Output Path: " + outputPath);

            try (FileOutputStream os = new FileOutputStream(outputPath)) {
                convertHtmlToPdf(createHtmlContent(hoaDon), os);
            }

            System.out.println("Đường dẫn tệp PDF: " + outputPath);
            PrintUtility print = new PrintUtility();
            print.printFileFromPath(outputPath);
        } else {
            System.out.println("Không tìm thấy hóa đơn với mã: " + invoiceId);
        }
    }

    private void convertHtmlToPdf(String htmlContent, FileOutputStream os) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, os);
        document.open();

        com.itextpdf.text.html.simpleparser.HTMLWorker htmlWorker = new com.itextpdf.text.html.simpleparser.HTMLWorker(document);
        htmlWorker.parse(new StringReader(htmlContent));

        document.close();
    }

    private String createHtmlContent(model.HoaDon hoaDon) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><head><style>")
                .append("div { font-family: 'Arial Unicode MS'; }")
                .append("table { width: 100%; border-spacing: 0; border-collapse: collapse; margin-top: 10px; margin-bottom: 10px; }")
                .append("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }")
                .append("th { background-color: #f2f2f2; }")
                .append("</style></head><body>")
                .append("<div>");

        addHeader(htmlContent);
        addShopAddress(htmlContent);
        addInvoiceDetails(htmlContent, hoaDon);

        htmlContent.append("<table>");
        addTableHeader(htmlContent);
        addProductsToTable(htmlContent, getHoaDonChiTietByHoaDonId(hoaDon.getMaHD()));
        htmlContent.append("</table>");

        addTotalAmounts(htmlContent, hoaDon);
        addFooter(htmlContent);

        htmlContent.append("</body></html>");

        return htmlContent.toString();
    }

    private void addHeader(StringBuilder htmlContent) {
        htmlContent.append("<div style='text-align: center; font-size: 18px; font-weight: bold;'>ENDLESS SHOESHOP</div>");
    }

    private void addShopAddress(StringBuilder htmlContent) {
        htmlContent.append("<div style='text-align: center; font-size: 10px;'>ĐC: Toà nhà FPT Polytechnic, Cần Thơ</div>");
    }

    private void addInvoiceDetails(StringBuilder htmlContent, model.HoaDon hoaDon) {
        htmlContent.append("<div style='font-size: 12px;'>")
                .append("Mã Hoá Đơn: ").append(hoaDon.getMaHD())
                .append("\t\tNgày Bán: ").append(hoaDon.getNgayBan())
                .append("\t\tKhách Hàng: ").append(hoaDon.getMaKH())
                .append("</div>");
    }

    private void addTableHeader(StringBuilder htmlContent) {
        htmlContent.append("<tr>")
                .append("<th>SL</th>")
                .append("<th>Giá bán</th>")
                .append("<th>Khuyến mãi</th>")
                .append("<th>Thành tiền</th>")
                .append("</tr>");
    }

    private void addProductsToTable(StringBuilder htmlContent, List<HoaDonChiTiet> hoaDonChiTiets) {
        for (HoaDonChiTiet chiTiet : hoaDonChiTiets) {
            model.SanPham sp = spDAO.selectById(ctspDAO.selectById(chiTiet.getMaCTSP()).getMaSP() + "");
            htmlContent.append("<tr>")
                    .append("<td></td>")
                    .append("<td></td>")
                    .append("<td></td>")
                    .append("<td></td>")
                    .append("<td>").append(chiTiet.getSoLuong()).append("</td>")
                    .append("<td>").append(sp.getDonGiaBan()).append("</td>")
                    .append("<td>").append(sp.getGiaKhuyenMai()).append("</td>");

            if (sp.getGiaKhuyenMai() == 0) {
                htmlContent.append("<td>").append(sp.getDonGiaBan() * chiTiet.getSoLuong()).append("</td>");
            } else {
                htmlContent.append("<td>").append(sp.getGiaKhuyenMai() * chiTiet.getSoLuong()).append("</td>");
            }

            htmlContent.append("</tr>");
        }
    }

    private void addTotalAmounts(StringBuilder htmlContent, model.HoaDon hoaDon) {
        htmlContent.append("<div style='font-size: 12px; font-weight: bold;'>")
                .append("Tổng Tiền: ").append(hoaDon.getTongTien()).append(" VNĐ")
                .append("\t\tThanh Toán: ").append(hoaDon.getTienThanhToan()).append(" VNĐ")
                .append("</div>");
    }

    private void addFooter(StringBuilder htmlContent) {
        htmlContent.append("<div style='text-align: center; font-size: 10px;'>Cảm ơn bạn đã mua sắm tại cửa hàng chúng tôi!</div>");
    }

    private List<HoaDonChiTiet> getHoaDonChiTietByHoaDonId(String hoaDonId) {
        // Thay thế bằng logic lấy chi tiết hóa đơn từ cơ sở dữ liệu dựa trên mã hóa đơn
        return hdctDAO.selectByMaHD(hoaDonId);
    }
}
