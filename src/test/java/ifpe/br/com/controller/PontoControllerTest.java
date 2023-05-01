package ifpe.br.com.controller;

import ifpe.br.com.model.Ponto;
import ifpe.br.com.repository.PontoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PontoControllerTest {

    @Mock
    private PontoRepository pontoRepository;

    @InjectMocks
    private PontoController pontoController;

    @Test
    void savePontoSuccessTest() throws Exception {
        Ponto ponto = new Ponto();
        ponto.setCodigoFuncionario(UUID.randomUUID().toString());
        ponto.setData(LocalDate.of(2023,2,19));
        ponto.setHoraEntradaTrabalho(LocalTime.of(9,2));
        ponto.setHoraSaidaAlmoco(LocalTime.of(12,6));
        ponto.setHoraVoltaAlmoco(LocalTime.of(13,6));
        ponto.setHoraSaidaAlmoco(LocalTime.of(18,2));

        when(pontoRepository.savePonto(any())).thenReturn(ponto);

        Ponto response = pontoController.savePonto(ponto);

        assertEquals(ponto, response);
    }

}
