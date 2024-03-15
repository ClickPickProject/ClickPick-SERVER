package com.clickpick.repository;

import com.clickpick.domain.Admin;
import com.clickpick.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
