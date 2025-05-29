package com.mdm_countries.etl.repository;

import com.mdm_countries.etl.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    
}



