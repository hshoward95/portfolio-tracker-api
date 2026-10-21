package com.hayden.portfolio;

import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;

@Service
public class HoldingService {
    private final HoldingRepository repository;

    public HoldingService(HoldingRepository repository) {
        this.repository = repository;
    }

    public List<Holding> getAll() {
        return repository.findAll();
    }

    public Holding getById(Long id) {
        Optional<Holding> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new HoldingNotFoundException(id);
        }
        return result.get();
    }

    public Holding create(Holding holding) {
        return repository.save(holding);
    }

    public Holding update(Long id, Holding updated) {
        Holding existing = getById(id);
        existing.setSymbol(updated.getSymbol());
        existing.setShares(updated.getShares());
        existing.setCostPerShare(updated.getCostPerShare());
        return repository.save(existing);
    }

    public void delete(Long id) {
        Holding existing = getById(id);
        repository.delete(existing);
    }

    public PortfolioSummary getSummary() {
        List<Holding> holdings = repository.findAll();
        BigDecimal total = BigDecimal.ZERO;
        Map<String, BigDecimal> bySymbol = new TreeMap<>();
        for (Holding holding : holdings) {
            BigDecimal cost = holding.getCostPerShare().multiply(BigDecimal.valueOf(holding.getShares()));
            total = total.add(cost);
            bySymbol.merge(holding.getSymbol(), cost, BigDecimal::add);
        }
        return new PortfolioSummary(holdings.size(), total, bySymbol);
    }
}