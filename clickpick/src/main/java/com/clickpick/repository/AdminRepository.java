package com.clickpick.repository;

import com.clickpick.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository <Admin, String> {
}
