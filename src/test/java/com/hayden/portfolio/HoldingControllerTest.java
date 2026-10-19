package com.hayden.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HoldingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private void createHolding(String json) throws Exception {
        mockMvc.perform(post("/api/holdings").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated());
    }

    @Test
    void createHolding_validInput_returns201AndSavedHolding() throws Exception {
        mockMvc.perform(post("/api/holdings").contentType(MediaType.APPLICATION_JSON).content("{\"symbol\":\"AAPL\",\"shares\":10,\"costPerShare\":150.25}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void createHolding_blankSymbol_returns400() throws Exception {
        mockMvc.perform(post("/api/holdings").contentType(MediaType.APPLICATION_JSON).content("{\"symbol\":\"\",\"shares\":10,\"costPerShare\":150.25}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.symbol").exists());
    }

    @Test
    void createHolding_negativeShares_returns400() throws Exception {
        mockMvc.perform(post("/api/holdings").contentType(MediaType.APPLICATION_JSON).content("{\"symbol\":\"AAPL\",\"shares\":-5,\"costPerShare\":150.25}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.shares").exists());
    }

    @Test
    void getHolding_missingId_returns404() throws Exception {
        mockMvc.perform(get("/api/holdings/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No holding with id 9999"));
    }

    @Test
    void summary_afterTwoHoldings_addsThemUp() throws Exception {
        createHolding("{\"symbol\":\"AAPL\",\"shares\":10,\"costPerShare\":150.25}");
        createHolding("{\"symbol\":\"MSFT\",\"shares\":2,\"costPerShare\":300.00}");
        mockMvc.perform(get("/api/portfolio/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.holdingCount").value(2))
                .andExpect(jsonPath("$.investedBySymbol.AAPL").value(1502.5))
                .andExpect(jsonPath("$.investedBySymbol.MSFT").value(600.0));
    }
}