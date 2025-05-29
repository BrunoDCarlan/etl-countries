package com.mdm_countries.etl.repository;

import com.mdm_countries.etl.model.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {
    
}