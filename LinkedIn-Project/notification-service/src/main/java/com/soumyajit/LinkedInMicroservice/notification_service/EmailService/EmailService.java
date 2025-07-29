package com.soumyajit.LinkedInMicroservice.notification_service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("idenoctrune@gmail.com"); // your gmail
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("idenoctrune@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }


    public String generateHtmlEmail(String recipientName, Long ownerUserId, String postContent, String imageUrl) {
        return String.format("""
        <!DOCTYPE html>
        <html lang="en" style="margin:0; padding:0; font-family:Arial, sans-serif;">
        <head>
          <meta charset="UTF-8" />
          <title>LinkedIn Clone Notification</title>
        </head>
        <body style="margin:0; padding:0; background-color:#f4f4f4;">
          <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f4f4; padding:20px 0;">
            <tr>
              <td align="center">
                <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 4px 12px rgba(0,0,0,0.1);">
                  <!-- Header -->
                  <tr>
                    <td style="background-color:#0a66c2; padding:20px;">
                      <h1 style="color:#ffffff; margin:0; font-size:24px;">LinkedIn Clone</h1>
                    </td>
                  </tr>

                  <!-- Banner Image -->
                  <tr>
                    <td>
                      <img src="%s" alt="Post Image" style="width:100%%; max-height:300px; object-fit:cover;" />
                    </td>
                  </tr>

                  <!-- Body Content -->
                  <tr>
                    <td style="padding:30px;">
                      <p style="font-size:16px; color:#333;">Hi <strong>%s</strong>,</p>
                      <p style="font-size:15px; color:#444;">
                        Your connection with ID <strong>%d</strong> has created this post:
                      </p>

                      <blockquote style="margin:20px 0; font-style:italic; font-size:16px; color:#0a66c2; border-left:4px solid #0a66c2; padding-left:10px;">
                        “%s”
                      </blockquote>

                      <div style="text-align:center; margin:30px 0;">
                        <a href="#" style="background-color:#0a66c2; color:white; padding:12px 24px; text-decoration:none; font-size:16px; border-radius:6px;">
                          View Post
                        </a>
                      </div>

                      <p style="font-size:14px; color:#666;">Cheers,<br/>The LinkedIn Clone Team</p>
                    </td>
                  </tr>

                  <!-- Footer -->
                  <tr>
                    <td style="background-color:#1a1a1a; padding:20px; text-align:center;">
                      <p style="color:#888; font-size:12px;">
                        © 2025 LinkedIn Clone, All rights reserved.<br/>
                        You are receiving this email because you're connected with this user.
                      </p>
                      <p style="margin-top:10px;">
                        <a href="#" style="color:#0a66c2; text-decoration:none; font-size:12px;">Unsubscribe</a>
                      </p>
                    </td>
                  </tr>

                </table>
              </td>
            </tr>
          </table>
        </body>
        </html>
        """, imageUrl, recipientName, ownerUserId, postContent);
    }

}
