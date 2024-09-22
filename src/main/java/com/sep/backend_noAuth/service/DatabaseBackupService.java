package com.sep.backend_noAuth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DatabaseBackupService {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Value("${mongodb.backup.path}")
    private String backupPath;

    public void backupDatabase() throws IOException, InterruptedException {
        // Enclose the URI in double quotes to prevent special characters from being misinterpreted
        String command = String.format(
                "mongodump --uri=\"%s\" --db=%s --out=%s",
                mongoUri, mongoDatabase, backupPath
        );

        // Use ProcessBuilder for executing the command
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        processBuilder.inheritIO();  // Inherit IO to see the output in the console
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Backup completed successfully.");
        } else {
            System.err.println("Backup failed with exit code: " + exitCode);
        }
    }

    @Scheduled(cron = "0 0 * * * ?")  // Backup every day at midnight
    public void scheduledBackup() throws IOException, InterruptedException {
        backupDatabase();
    }
}
