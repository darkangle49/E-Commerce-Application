package com.eu;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	@Autowired
	PaymentRepository repo;
	
	public Payment doPayment(Payment payment)
	{payment.setPaymentStatus(paymentProccessing());
		payment.setTransactionId(UUID.randomUUID().toString());
		return repo.insert(payment);
	}
	public String paymentProccessing() {
		return new Random().nextBoolean()?"success":"false";
	}
}
