package ifpe.br.com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ifpe.br.com.exceptions.FuncionarioNotFoundException;
import ifpe.br.com.model.Funcionario;
import ifpe.br.com.repository.FuncionarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FuncionarioControllerTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioController funcionarioController;

    @Test
    void getFuncionariosSuccessTest() throws JsonProcessingException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("name");

        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario));

        List<Funcionario> response = funcionarioController.getFuncionarios();

        assertEquals(Arrays.asList(funcionario), response);
    }

    @Test
    void getFuncionarioByIdSuccessTest() throws JsonProcessingException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("name");

        when(funcionarioRepository.findFuncionarioById(anyString())).thenReturn(funcionario);

        Funcionario response = funcionarioController.getFuncionario(UUID.randomUUID().toString());

        assertEquals(funcionario, response);
    }

    @Test
    void updateFuncionarioSuccessTest() throws FuncionarioNotFoundException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("name");

        when(funcionarioRepository.updateFuncionario(anyString(), any())).thenReturn(funcionario);

        Funcionario response = funcionarioController.updateFuncionarios(UUID.randomUUID().toString(), funcionario);

        assertEquals(funcionario, response);
    }

    @Test
    void saveFuncionarioSuccessTest() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("name");

        when(funcionarioRepository.saveFuncionario(any())).thenReturn(funcionario);

        Funcionario response = funcionarioController.addFuncionario(funcionario);

        assertEquals(funcionario, response);
    }

    @Test
    void deleteFuncionarioSucessTest() {
        String codigoFuncionario = UUID.randomUUID().toString();
        String message = format("Funcionário com codigoFuncionario %s foi deletado com sucesso", codigoFuncionario);

        when(funcionarioRepository.deleteFuncionarioById(anyString())).thenReturn(message);

        String response = funcionarioController.deleteFuncionario(codigoFuncionario);

        assertEquals(message, response);
    }
}
