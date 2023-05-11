package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import ifpe.br.com.config.AtestadoCodec;
import ifpe.br.com.exceptions.FuncionarioNotFoundException;
import ifpe.br.com.model.Atestado;
import ifpe.br.com.repository.AtestadoRepository;
import ifpe.br.com.repository.FuncionarioRepository;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
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

    public MongoCollection<Atestado> getCollection() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true)
                                .register(AtestadoCodec.class)
                                .build()
                )
        );
        return mongoClient.getDatabase("rhadmin-spring").getCollection("atestado", Atestado.class)
                .withCodecRegistry(pojoCodecRegistry);
    }

    private GridFSBucket getGridFSBuckets() {
        return GridFSBuckets.create(mongoClient.getDatabase("rhadmin-quarkus"));
    }

    public Atestado saveAtestado(Atestado atestado) throws Exception {

        if (Objects.isNull(funcionarioRepository.findFuncionarioById(atestado.getCodigoFuncionario())))
            throw new FuncionarioNotFoundException(String.format("Funcionario com id: %s não encontrado", atestado.getCodigoFuncionario()));

        atestado.setCodigoAtestado(UUID.randomUUID().toString());

        File file = new File(atestado.getAtestado());
        InputStream targetStream = new FileInputStream(file);
        getGridFSBuckets().uploadFromStream(atestado.getCodigoAtestado(), targetStream);

        getCollection().insertOne(atestado);

        return atestado;
    }
}
