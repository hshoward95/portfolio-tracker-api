package com.hayden.portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HoldingServiceTest {
    @Mock
    private HoldingRepository repository;
    private HoldingService service;

    @BeforeEach
    void setUp() {
        service = new HoldingService(repository);
    }

    private Holding makeHolding(String symbol, int shares, String price) {
        Holding holding = new Holding();
        holding.setSymbol(symbol);
        holding.setShares(shares);
        holding.setCostPerShare(new BigDecimal(price));
        return holding;
    }

    @Test
    void testGetByIdMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(HoldingNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void testGetByIdFound() {
        Holding apple = makeHolding("AAPL", 10, "150.25");
        when(repository.findById(1L)).thenReturn(Optional.of(apple));
        Holding result = service.getById(1L);
        assertThat(result.getSymbol()).isEqualTo("AAPL");
    }

    @Test
    void testGetSummary() {
        List<Holding> holdings = List.of(makeHolding("AAPL", 10, "150.25"), makeHolding("AAPL", 5, "160.00"), makeHolding("MSFT", 2, "300.00"));
        when(repository.findAll()).thenReturn(holdings);
        PortfolioSummary summary = service.getSummary();
        assertThat(summary.holdingCount()).isEqualTo(3);
        assertThat(summary.totalInvested()).isEqualByComparingTo("2902.50");
        assertThat(summary.investedBySymbol().get("AAPL")).isEqualByComparingTo("2302.50");
        assertThat(summary.investedBySymbol().get("MSFT")).isEqualByComparingTo("600.00");
    }
}