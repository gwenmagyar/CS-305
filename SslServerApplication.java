package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;

@SpringBootApplication
public class SslServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SslServerApplication.class, args);
    }

    // REST Controller to handle the checksum request
    @RestController
    @RequestMapping("/api")
    public static class ChecksumController {

        @GetMapping("/checksum")
        public String getChecksum() {
            try {
                String data = "Hello World Check Sum!";
                String hash = getSHA256Hash(data);
                return "Name: Gwen Magyar\nChecksum: " + hash;
            } catch (Exception e) {
                return "Error calculating checksum: " + e.getMessage();
            }
        }

        // Method to calculate SHA-256 hash
        public static String getSHA256Hash(String data) throws Exception {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        }
    }
}
