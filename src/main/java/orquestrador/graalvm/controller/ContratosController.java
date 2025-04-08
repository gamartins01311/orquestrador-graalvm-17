package orquestrador.graalvm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import orquestrador.graalvm.controller.domains.ContratosResponse;
import orquestrador.graalvm.facade.ContratosFacade;
import org.springframework.util.StopWatch;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/java21")
public class ContratosController {

    @Autowired
    private ContratosFacade contratosFacade;


    @GetMapping("/contratos/novt/{cpfCnpj}")
    public List<ContratosResponse> getAllContratosSemVirtualThread(@PathVariable("cpfCnpj") String cpfCnpj) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start("NoVirtualThreadController");

        List<ContratosResponse> response = contratosFacade.getAllContratosByCpfCnpjSemVirtualThread(cpfCnpj);

        stopwatch.stop();
        log.info("🚀 Tempo total (Controller + Thread Tradicional): {}ms", stopwatch.getTotalTimeMillis());

        return response;
    }
}
