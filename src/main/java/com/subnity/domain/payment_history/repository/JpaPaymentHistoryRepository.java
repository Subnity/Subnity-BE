package com.subnity.domain.payment_history.repository;

import com.subnity.domain.payment_history.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
