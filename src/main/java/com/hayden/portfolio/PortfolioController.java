package com.hayden.portfolio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private final HoldingService service;

    public PortfolioController(HoldingService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public PortfolioSummary getSummary() {
        return service.getSummary();
    }
}