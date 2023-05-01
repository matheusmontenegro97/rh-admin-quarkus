package ifpe.br.com.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import ifpe.br.com.exceptions.FuncionarioNotFoundException;
import ifpe.br.com.model.Ponto;
import ifpe.br.com.repository.FuncionarioRepository;
import ifpe.br.com.repository.PontoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PontoRepositoryImpl implements PontoRepository {

    private final MongoClient mongoClient;

    private final FuncionarioRepository funcionarioRepository;

    @Inject
    public PontoRepositoryImpl(MongoClient mongoClient, FuncionarioRepository funcionarioRepository) {
        this.mongoClient = mongoClient;
        this.funcionarioRepository = funcionarioRepository;
    }

    private MongoCollection<Ponto> getCollection() {
        return mongoClient.getDatabase("rhadmin-quarkus").getCollection("rhadmin-quarkus", Ponto.class);
    }

    public Ponto savePonto(Ponto ponto) throws Exception {

        Optional.of(funcionarioRepository.findFuncionarioById(ponto.getCodigoFuncionario()))
                .orElseThrow(() -> new FuncionarioNotFoundException(String.format("Funcionario com id: %s não encontrado", ponto.getCodigoFuncionario())));

        ponto.setCodigoPonto(UUID.randomUUID().toString());

        getCollection().insertOne(ponto);

        return ponto;
    }
}
