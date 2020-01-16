package com.jiangtj.mailsender.repository;

import com.jiangtj.mailsender.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/10/15.
 */
public interface RecordRepository extends JpaRepository<Record, Long> {
}
