package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ifpe.br.com.config.CargoCodec;
import ifpe.br.com.config.EnderecoCodec;
import ifpe.br.com.config.FuncionarioCodec;
import ifpe.br.com.model.Funcionario;
import ifpe.br.com.repository.FuncionarioRepository;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class FuncionarioRepositoryImpl implements FuncionarioRepository {

    private final MongoClient mongoClient;


    @Inject
    public FuncionarioRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoCollection<Funcionario> getCollection() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true)
                                .register(EnderecoCodec.class)
                                .register(CargoCodec.class)
                                .register(FuncionarioCodec.class)
                                .build()
                )
        );
        return mongoClient.getDatabase("rhadmin-spring").getCollection("funcionarios", Funcionario.class)
                .withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public Funcionario saveFuncionario(Funcionario funcionario) {

        funcionario.setCodigoFuncionario(UUID.randomUUID().toString());

        getCollection().insertOne(funcionario);

        return funcionario;
    }

    @Override
    public Funcionario updateFuncionario(String codigoFuncionario, Funcionario funcionario) {

        getCollection().replaceOne(Filters.eq("codigoFuncionario", codigoFuncionario), funcionario);
        return funcionario;
    }

    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = new ArrayList<>();

        FindIterable<Funcionario> funcionarioFindIterable = getCollection().find();

        for (Funcionario funcionario : funcionarioFindIterable) {
            funcionarios.add(funcionario);
        }

        return funcionarios;
    }

    @Override
    public Funcionario findFuncionarioById(String codigoFuncionario) {
        Funcionario funcionario =
                getCollection().find(eq("codigoFuncionario", codigoFuncionario)).first();

        return funcionario;
    }

    @Override
    public String deleteFuncionarioById(String codigoFuncionario) {
        getCollection().deleteOne(eq("codigoFuncionario", codigoFuncionario));
        return codigoFuncionario;
    }
}
