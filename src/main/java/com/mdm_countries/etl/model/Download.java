package com.mdm_countries.etl.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "download")
@Data

public class Download {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downloadId;
    
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    private LocalDateTime downloadedAt;

    String rawFilePath; 

    @PrePersist
    public void onCreate() {
        downloadedAt = LocalDateTime.now();
    }
}
