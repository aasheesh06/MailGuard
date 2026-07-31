package com.aash.mailguard.smtp;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;

@Service
public class SmtpVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(SmtpVerificationService.class);

    public boolean verify(String email, String mxHost) {
        logger.info("Starting SMTP verification for {} ",email);
        logger.info("Connecting to MX host: {}", mxHost);

        try (
                Socket socket = new Socket();

        ) {
            socket.connect(new InetSocketAddress(mxHost, 25), 5000);
            socket.setSoTimeout(5000);

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream()));
            logger.info("Connected successfully");

            // 220 Service Ready
            String response = reader.readLine();

            if (!response.startsWith("220")) {
                return false;
            }
            logger.info("Server: {}", response);

            // HELO
             logger.info("Sending HELO command");
            writer.write("HELO localhost\r\n");
            writer.flush();


            response = reader.readLine();

            if (!response.startsWith("250")) {
                return false;
            }
            logger.info("Server: {}", response);

            // MAIL FROM
            logger.info("Sending MAIL FROM command");
            writer.write("MAIL FROM:<verify@mailguard.com>\r\n");
            writer.flush();

            response = reader.readLine();

            if (!response.startsWith("250")) {
                return false;
            }
            logger.info("Server: {}", response);

            // RCPT TO
            logger.info("Sending RCPT TO command for {}", email);
            writer.write("RCPT TO:<" + email + ">\r\n");
            writer.flush();

            response = reader.readLine();
            logger.info("Server: {}", response);

            // Connection close politely
            logger.info("Sending QUIT command");
            writer.write("QUIT\r\n");
            writer.flush();

            if (response.startsWith("250")) {
                logger.info("SMTP verification successful for {}", email);
                return true;

            }

            if (response.startsWith("550")) {
                logger.warn("Recipient rejected by SMTP server for {}", email);
                return false;
            }
            logger.warn("Unexpected SMTP response: {}", response);
            return false;

        } catch (IOException e) {
            logger.error("SMTP verification failed", e);
            return false;
        }
    }
}