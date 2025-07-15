package com.subnity.domain.subscription.repository;

import com.subnity.domain.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSubscriptionRepository extends JpaRepository<Subscription, Long> {
}
