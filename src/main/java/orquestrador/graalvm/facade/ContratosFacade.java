package orquestrador.graalvm.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import orquestrador.graalvm.controller.domains.ContratosResponse;
import orquestrador.graalvm.integrations.contratos.service.ContratosService;

import java.util.List;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ContratosFacade {

    @Autowired
    private ContratosService contratoService;

    public List<ContratosResponse> getAllContratosByCpfCnpjSemVirtualThread(final String cpfCnpj) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start("SemVirtualThreadCall");

        try {
            return contratoService.buscarContratosPorCpfCnpjSemVirtualThread(cpfCnpj);
        } finally {
            stopwatch.stop();
            log.info("⏱️ Tempo de execução (Thread tradicional): {}ms", stopwatch.getTotalTimeMillis());
        }
    }


}
