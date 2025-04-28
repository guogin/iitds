package com.yahaha.iitds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahaha.iit.calc.IITRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SimulationControllerTests {
    @Autowired
    MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void when_simulate_then_ok() throws Exception {
        mvc.perform(post(SimulationController.PATH + "/simulate")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void when_simulate_18k_monthly_and_300k_bonus_then_response_should_have_results() throws Exception {
        IITRequest request = new IITRequest();
        request.setAnnualWageIncome(BigDecimal.valueOf(18000 * 12));
        request.setAnnualOneTimeBonus(BigDecimal.valueOf(300000));

        String json = objectMapper.writeValueAsString(request);

        MockHttpServletResponse httpResponse = mvc.perform(
                        post(SimulationController.PATH + "/simulate")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String responseContent = httpResponse.getContentAsString();

        assertThat(responseContent).isNotEmpty();
        assertThat(responseContent)
                .contains("83,880")
                .contains("72,870")
                .contains("traceLog");
    }
}
