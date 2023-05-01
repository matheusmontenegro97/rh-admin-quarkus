package ifpe.br.com.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import ifpe.br.com.exceptions.FuncionarioNotFoundException;
import ifpe.br.com.model.Atestado;
import ifpe.br.com.repository.AtestadoRepository;
import ifpe.br.com.repository.FuncionarioRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AtestadoRepositoryImpl implements AtestadoRepository {

    private final MongoClient mongoClient;

    private final FuncionarioRepository funcionarioRepository;

    @Inject
    public AtestadoRepositoryImpl(MongoClient mongoClient, FuncionarioRepository funcionarioRepository) {
        this.mongoClient = mongoClient;
        this.funcionarioRepository = funcionarioRepository;
    }

    private MongoCollection<Atestado> getCollection() {
        return mongoClient.getDatabase("rhadmin-quarkus").getCollection("rhadmin-quarkus", Atestado.class);
    }

    private GridFSBucket getGridFSBuckets() {
        return GridFSBuckets.create(mongoClient.getDatabase("rhadmin-quarkus"));
    }

    public Atestado saveAtestado(Atestado atestado) throws Exception {

        Optional.of(funcionarioRepository.findFuncionarioById(atestado.getCodigoFuncionario()))
                .orElseThrow(() ->
                        new FuncionarioNotFoundException(String.format("Funcionario com id: %s não encontrado", atestado.getCodigoFuncionario())));

        atestado.setCodigoAtestado(UUID.randomUUID().toString());

        File file = new File(atestado.getAtestado());
        InputStream targetStream = new FileInputStream(file);
        getGridFSBuckets().uploadFromStream(atestado.getCodigoAtestado(), targetStream);

        getCollection().insertOne(atestado);

        return atestado;
    }
}
