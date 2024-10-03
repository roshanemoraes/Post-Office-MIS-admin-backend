package com.sep.backend_noAuth.controller;

import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.exception.SignatureVerificationException;

@RestController
public class StripeWebhookController {

    // Inject the webhook secret from application properties
    @Value("${stripe.webhookSecret}")
    private String endpointSecret;

    // Webhook endpoint for Stripe events
    @PostMapping("/stripe/webhook/customer/pay-money-order")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
                                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            // Verify the event by checking the signature
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // Handle the event
            if ("checkout.session.completed".equals(event.getType())) {
                // Retrieve the session object from the event
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);

                // Call your method to handle successful payment
                handleSuccessfulPayment(session);

                return ResponseEntity.ok("Event processed successfully");
            } else {
                // Unhandled event types
                return ResponseEntity.ok("Unhandled event type");
            }
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            // General error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook event");
        }
    }

    // Method to handle successful payment (checkout.session.completed event)
    private void handleSuccessfulPayment(Session session) {
        if (session == null) return;

        // Extract necessary information from the session
        String customerEmail = session.getCustomerDetails().getEmail();
        String paymentIntentId = session.getPaymentIntent();

        // Log the payment details (for debugging or tracking purposes)
        System.out.println("Payment successful for email: " + customerEmail);
        System.out.println("Payment Intent ID: " + paymentIntentId);

        // Example: Mark the order as paid in your database
        markOrderAsPaid(paymentIntentId);

        // Example: Send a confirmation email
        sendConfirmationEmail(customerEmail, paymentIntentId);
    }

    // Sample method to mark an order as paid
    private void markOrderAsPaid(String paymentIntentId) {
        // Add logic to update your database, mark the order as paid
        // Example: orderService.markAsPaid(paymentIntentId);
        System.out.println("Order marked as paid for Payment Intent ID: " + paymentIntentId);
    }

    // Sample method to send a confirmation email
    private void sendConfirmationEmail(String customerEmail, String paymentIntentId) {
        // Add logic to send email
        // Example: emailService.sendPaymentConfirmation(customerEmail, paymentIntentId);
        System.out.println("Confirmation email sent to: " + customerEmail);
    }
}