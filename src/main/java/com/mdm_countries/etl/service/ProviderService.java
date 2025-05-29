package com.mdm_countries.etl.service;

import com.mdm_countries.etl.model.Provider;
import com.mdm_countries.etl.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public Optional<Provider> getProviderById(Long id) {
        return providerRepository.findById(id);
    }

    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public Provider updateProvider(Long id, Provider updatedProvider) {
        return providerRepository.findById(id)
            .map(existingProvider -> {
                existingProvider.setProviderName(updatedProvider.getProviderName());
                return providerRepository.save(existingProvider);
            })
            .orElseThrow(() -> new IllegalArgumentException("Provider não encontrado com id: " + id));
    }

    public void deleteProvider(Long id) {
        if (!providerRepository.existsById(id)) {
            throw new IllegalArgumentException("Provider não encontrado com id: " + id);
        }
        providerRepository.deleteById(id);
    }
}
