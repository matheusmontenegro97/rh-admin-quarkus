package ifpe.br.com.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ifpe.br.com.model.Funcionario;
import ifpe.br.com.model.Ponto;
import ifpe.br.com.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PontoRepositoryImplTest {
    @Mock
    MongoClient mongoClient;

    @InjectMocks
    PontoRepositoryImpl pontoRepositoryImpl;

    @Mock
    MongoDatabase database;

    @Mock
    MongoCollection<Ponto> coll;

    @Mock
    FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void beforeEach(){
        resetAllMocks();
    }

    void resetAllMocks(){
        reset(mongoClient);
        reset(database);
        reset(coll);
        reset(funcionarioRepository);
    }

    @Test
    void savePontoSuccessTest() throws Exception {
        String codigoFuncionario = UUID.randomUUID().toString();

        Ponto ponto = new Ponto();
        ponto.setCodigoFuncionario(codigoFuncionario);

        Funcionario func = new Funcionario();
        func.setDataNascimento(LocalDate.now());

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Ponto.class))).thenReturn(coll);
        when(funcionarioRepository.findFuncionarioById(codigoFuncionario)).thenReturn(func);

        pontoRepositoryImpl.savePonto(ponto);

        verify(coll, times(1)).insertOne(ponto);
    }

}
