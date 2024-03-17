package util;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailSMTP {

    String numberOTP;

    public String getNumberOTP() {
        return numberOTP;
    }

    public void setNumberOTP(String numberOTP) {
        this.numberOTP = numberOTP;
    }

    public int generateRandomOTP() {
        Random random = new Random();
        // Tạo một số ngẫu nhiên từ 100000 đến 999999 (gồm 6 chữ số)
        int min = 100000;
        int max = 999999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        setNumberOTP(randomNumber + "");
        return randomNumber;
    }

    public void sendOTP(String emailTo) {
        String otp = generateRandomOTP() + "";
        String username = "endlessshop.contact@gmail.com";
        String password = "ycqv jrnt vitn lseo";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Yêu cầu thay đổi mật khẩu");
            String emailBody = "<html lang='vi'>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<style>"
                    + "body { font-family: 'Times New Roman', serif; font-size: 16px; line-height: 1.6; margin: 0; padding: 0; }"
                    + "h2 { color: #333333; margin-bottom: 10px; }"
                    + "span { color: #555555; display: block; margin-bottom: 20px; }"
                    + "</style>"
                    + "</head>"
                    + "<body style='background-color: #f4f4f4; padding: 20px; margin: 0;'>"
                    + "<div style='background-color: #ffffff; max-width: 600px; margin: 0 auto; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                    + "<h2>Xin chào !</h2>"
                    + "<span>Ai đó đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn. "
                    + "Nếu đây không phải là bạn, vui lòng bỏ qua email này.</span>"
                    + "<span>Sử dụng mã kích hoạt này để khôi phục mật khẩu của bạn: <strong>" + otp + "</strong></span>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            message.setContent(emailBody, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
