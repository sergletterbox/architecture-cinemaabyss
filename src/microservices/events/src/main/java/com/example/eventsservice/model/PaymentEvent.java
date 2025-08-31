package com.example.eventsservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class PaymentEvent extends BaseEvent {
    @JsonProperty("paymentId")
    private String paymentId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("status")
    private String status="success";

    public PaymentEvent() {
        super("PAYMENT_EVENT");
    }

    public PaymentEvent(String paymentId, String userId, BigDecimal amount, String currency, String paymentMethod, String status) {
        super("PAYMENT_EVENT");
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getters and setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}