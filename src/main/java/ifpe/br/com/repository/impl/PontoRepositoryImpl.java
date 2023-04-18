package ifpe.br.com.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import ifpe.br.com.config.ObjectMapperCustom;
import ifpe.br.com.exceptions.FuncionarioNotFoundException;
import ifpe.br.com.model.Ponto;
import ifpe.br.com.repository.FuncionarioRepository;
import ifpe.br.com.repository.PontoRepository;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PontoRepositoryImpl implements PontoRepository {

    private final MongoClient mongoClient;

    private final FuncionarioRepository funcionarioRepository;

    @Inject
    public PontoRepositoryImpl(MongoClient mongoClient, ObjectMapperCustom om, FuncionarioRepository funcionarioRepository) {
        this.mongoClient = mongoClient;
        this.funcionarioRepository = funcionarioRepository;
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("rhadmin-quarkus").getCollection("rhadmin-quarkus");
    }

    public Ponto savePonto(Ponto ponto) throws Exception {

        Optional.of(funcionarioRepository.findFuncionarioById(ponto.getCodigoFuncionario()))
                .orElseThrow(() -> new FuncionarioNotFoundException(String.format("Funcionario com id: %s não encontrado", ponto.getCodigoFuncionario())));

        ponto.setCodigoPonto(UUID.randomUUID().toString());

        Document document = new Document()
                .append("codigoPonto", ponto.getCodigoPonto())
                .append("codigoFuncionario", ponto.getCodigoFuncionario())
                .append("horaEntradaTrabalho", ponto.getHoraEntradaTrabalho())
                .append("horaSaidaAlmoco", ponto.getHoraSaidaAlmoco())
                .append("horaVoltaAlmoco", ponto.getHoraVoltaAlmoco())
                .append("horaSaidaTrabalho", ponto.getHoraSaidaTrabalho())
                .append("data", ponto.getData());

        getCollection().insertOne(document);

        return ponto;
    }
}
