package com.yahaha.iitds;

import com.yahaha.iit.calc.IITCalculator;
import com.yahaha.iit.calc.IITCalculatorImpl2024;
import com.yahaha.iit.calc.IITRequest;
import com.yahaha.iit.calc.IITResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SimulationController {
    public static final String PATH = "/api/v1";

    private final IITCalculator calculator = new IITCalculatorImpl2024();

    @PostMapping(path = PATH + "/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public IITResponse simulate(@RequestBody IITRequest request) {
        return calculator.simulate(request);
    }
}
