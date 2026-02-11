package com.lksupply.lksupply2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // This fixes the "Cannot resolve method" error
    List<ActivityLog> findTop10ByOrderByTimestampDesc();
}