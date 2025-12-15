package com.fullsystem.fullsystem.infrastructure.adapters;

import com.fullsystem.fullsystem.domain.model.Quotation;
import com.fullsystem.fullsystem.domain.ports.out.QuotationRepositoryPort;
import com.fullsystem.fullsystem.infrastructure.entities.QuotationEntity;
import com.fullsystem.fullsystem.infrastructure.mappers.quotations.QuotationMapper;
import com.fullsystem.fullsystem.infrastructure.repositories.JpaQuotationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that implements the QuotationRepositoryPort using JPA.
 * Bridges the domain layer with the infrastructure persistence layer.
 */
@Component
public class QuotationRepositoryAdapter implements QuotationRepositoryPort {

    private final JpaQuotationRepository jpaQuotationRepository;
    private final QuotationMapper quotationMapper;

    public QuotationRepositoryAdapter(JpaQuotationRepository jpaQuotationRepository,
            QuotationMapper quotationMapper) {
        this.jpaQuotationRepository = jpaQuotationRepository;
        this.quotationMapper = quotationMapper;
    }

    @Override
    public Quotation save(Quotation quotation) {
        QuotationEntity entity = quotationMapper.toEntity(quotation);
        QuotationEntity savedEntity = jpaQuotationRepository.save(entity);
        return quotationMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Quotation> findById(Long id) {
        return jpaQuotationRepository.findById(id)
                .map(quotationMapper::toDomain);
    }

    @Override
    public List<Quotation> findAll() {
        return jpaQuotationRepository.findAll().stream()
                .map(quotationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaQuotationRepository.deleteById(id);
    }
}
