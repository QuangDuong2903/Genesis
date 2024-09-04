package com.genesis.rule_service;

import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final KieContainer kieContainer;

    public BigDecimal calculatePaymentAmount(Payment payment) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(payment);
        kieSession.fireAllRules();
        kieSession.dispose();
        return payment.getAmount();
    }
}
