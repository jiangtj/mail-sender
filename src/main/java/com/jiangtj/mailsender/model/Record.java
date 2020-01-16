package com.jiangtj.mailsender.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue
    private UUID uuid;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
    @Column(nullable = false, length = 36)
    private String sender;
    @Column(nullable = false, length = 36)
    private String addressee;
    @Column(nullable = false, length = 128)
    private String title;
    @Column(nullable = false, length = 256)
    private String content;
}
