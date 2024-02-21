package com.clickpick.repository;

import com.clickpick.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository <Alarm, Long> {
}
