package com.hayden.portfolio;

import java.math.BigDecimal;
import java.util.Map;

public record PortfolioSummary(int holdingCount, BigDecimal totalInvested, Map<String, BigDecimal> investedBySymbol) {
}