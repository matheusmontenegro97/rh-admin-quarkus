package ifpe.br.com.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import ifpe.br.com.model.Funcionario;

import java.util.List;

public interface FuncionarioRepository {
    Funcionario saveFuncionario(Funcionario funcionario);

    Funcionario updateFuncionario(String codigoFuncionario, Funcionario funcionario);

    List<Funcionario> findAll() throws JsonProcessingException;

    Funcionario findFuncionarioById(String codigoFuncionario) throws JsonProcessingException;

    void deleteFuncionarioById(String codigoFuncionario);
}
