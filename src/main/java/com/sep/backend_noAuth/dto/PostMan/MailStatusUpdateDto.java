package com.sep.backend_noAuth.dto.PostMan;
public class MailStatusUpdateDto {

        private String mailId;
        private String status;

        // Getters and setters
        public String getMailId() {
            return mailId;
        }

        public void setMailId(String mailId) {
            this.mailId = mailId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


