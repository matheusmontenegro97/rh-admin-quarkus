package ifpe.br.com.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import ifpe.br.com.exceptions.FuncionarioNotFoundException;
import ifpe.br.com.model.Funcionario;

import java.util.List;

public interface FuncionarioRepository {
    Funcionario saveFuncionario(Funcionario funcionario);

    Funcionario updateFuncionario(String codigoFuncionario, Funcionario funcionario) throws FuncionarioNotFoundException;

    List<Funcionario> findAll() throws JsonProcessingException;

    Funcionario findFuncionarioById(String codigoFuncionario);

    String deleteFuncionarioById(String codigoFuncionario);
}
