package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ForgetPasswordController {

    @FXML
    private JFXButton btnClickToSubmit;

    @FXML
    private Label lblEmail;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    void btnClickToSubmitOnAction(ActionEvent event) {
        String to = txtEmail.getText().trim();

        // Validate email input
        if (to.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an email address.").show();
            return;
        }

        // Generate a random OTP
        int otp = (int) (Math.random() * 9000) + 1000; // Generate a 4-digit OTP

        // Email credentials and configuration
        final String FROM = "pprabhadee@gmail.com";
        final String PASSWORD = " ijyz owtl suyw qpyn";
        final String subject = "Your OTP for Password Recovery";

        final String body = "<html>" +
                "<body>" +
                "<p><span style='color:green; font-size:18px; font-weight:bold;'>OTP: " + otp + "</span></p>" +
                "<p><span style='color:red; font-size:16px;'>Please do not share this OTP with others.</span></p>" +
                "</body>" +
                "</html>";

        // Configure SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with email credentials
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Set the email content with HTML formatting
            message.setContent(body, "text/html");

            // Send the email
            Transport.send(message);

            // Show success alert
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();

        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email. Please try again.").show();
        }
    }
}
