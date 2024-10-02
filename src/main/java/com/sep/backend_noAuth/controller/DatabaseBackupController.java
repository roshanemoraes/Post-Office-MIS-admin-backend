package com.sep.backend_noAuth.controller;

import com.sep.backend_noAuth.service.DatabaseBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DatabaseBackupController {
    @Autowired
    private DatabaseBackupService backupService;

    @GetMapping("/backup")
    public String backupDatabase() {
        try {
            backupService.backupDatabase();
            return "Backup completed successfully!";
        } catch (Exception e) {
            return "Backup failed: " + e.getMessage();
        }
    }
}
