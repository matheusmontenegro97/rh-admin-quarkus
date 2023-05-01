package ifpe.br.com.repository.impl;


import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import ifpe.br.com.model.Funcionario;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FuncionarioRepositoryImplTest {

    @Mock
    private MongoClient mongoClient;

    @InjectMocks
    private FuncionarioRepositoryImpl funcionarioRepositoryImpl;

    @Mock
    private MongoDatabase database;

    @Mock
    private MongoCollection<Funcionario> coll;

    @Mock
    private FindIterable<Funcionario> findIterable;

    @BeforeEach
    void beforeEach(){
        resetAllMocks();
    }

    void resetAllMocks(){
        reset(mongoClient);
        reset(database);
        reset(coll);
        reset(findIterable);
    }

    @Test
    void saveFuncionarioTest(){
        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Funcionario.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);

        Funcionario func = new Funcionario();
        func.setDataNascimento(LocalDate.now());

        funcionarioRepositoryImpl.saveFuncionario(func);

        verify(coll, times(1)).insertOne(func);
    }

    @Test
    void updateFuncionarioTest(){
        String codigoFuncionario = UUID.randomUUID().toString();
        Bson filter = Filters.eq("codigoFuncionario", codigoFuncionario);

        Funcionario func = new Funcionario();
        func.setDataNascimento(LocalDate.now());

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Funcionario.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);

        funcionarioRepositoryImpl.updateFuncionario(codigoFuncionario, func);

        verify(coll, times(1)).replaceOne(filter, func);
    }

    @Test
    void findAllTest() {
        Funcionario func = new Funcionario();
        func.setDataNascimento(LocalDate.now());

        MongoCursor cursor = mock(MongoCursor.class);

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Funcionario.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(coll.find()).thenReturn(findIterable);
        when(findIterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext())
                .thenReturn(true)
                .thenReturn(false);
        when(cursor.next())
                .thenReturn(func);

        List<Funcionario> funcionarioList = funcionarioRepositoryImpl.findAll();

        assertFalse(funcionarioList.isEmpty());
    }

    @Test
    void findFuncionarioByIdTest() {
        String codigoFuncionario = UUID.randomUUID().toString();
        Funcionario func = new Funcionario();
        func.setCodigoFuncionario(codigoFuncionario);
        func.setDataNascimento(LocalDate.now());

        Bson filter = Filters.eq("codigoFuncionario", codigoFuncionario);

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Funcionario.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(coll.find(filter)).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(func);

        Funcionario funcionario = funcionarioRepositoryImpl.findFuncionarioById(codigoFuncionario);

        assertEquals(func, funcionario);
    }

    @Test
    void deleteByIdTest() {
        String codigoFuncionario = UUID.randomUUID().toString();
        Bson filter = Filters.eq("codigoFuncionario", codigoFuncionario);

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Funcionario.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(coll.deleteOne(filter)).thenReturn(DeleteResult.acknowledged(1));

        funcionarioRepositoryImpl.deleteFuncionarioById(codigoFuncionario);

        verify(coll,times(1)).deleteOne(filter);
    }
}
