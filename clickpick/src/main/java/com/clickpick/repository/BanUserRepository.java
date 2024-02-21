package com.clickpick.repository;

import com.clickpick.domain.BanUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BanUserRepository extends JpaRepository<BanUser, Long> {
}
