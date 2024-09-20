package com.sep.backend_noAuth.dto.PostMan;

public class DeliveryStatusUpdateDto {
    private String deliveryId;
    private String status;

    // Getters and setters
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}