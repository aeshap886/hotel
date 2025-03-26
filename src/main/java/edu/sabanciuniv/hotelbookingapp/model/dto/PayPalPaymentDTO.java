package edu.sabanciuniv.hotelbookingapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPalPaymentDTO {
    private String paypalOrderId;
    private String paypalPayerId;
}