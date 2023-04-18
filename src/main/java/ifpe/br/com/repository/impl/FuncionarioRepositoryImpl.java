package ifpe.br.com.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import ifpe.br.com.config.ObjectMapperCustom;
import ifpe.br.com.model.Funcionario;
import ifpe.br.com.repository.FuncionarioRepository;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class FuncionarioRepositoryImpl implements FuncionarioRepository {

    private final MongoClient mongoClient;

    private final ObjectMapperCustom om;

    @Inject
    public FuncionarioRepositoryImpl(MongoClient mongoClient, ObjectMapperCustom om) {
        this.mongoClient = mongoClient;
        this.om = om;
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("rhadmin-quarkus").getCollection("rhadmin-quarkus");
    }

    @Override
    public Funcionario saveFuncionario(Funcionario funcionario) {

        funcionario.setCodigoFuncionario(UUID.randomUUID().toString());

        Document document = new Document()
                .append("codigoFuncionario", funcionario.getCodigoFuncionario())
                .append("nome", funcionario.getNome())
                .append("nomeSocial", funcionario.getNomeSocial())
                .append("dataNascimento", funcionario.getDataNascimento().toString())
                .append("cargo", funcionario.getCargo())
                .append("cpf", funcionario.getCpf())
                .append("rg", funcionario.getRg())
                .append("endereco", funcionario.getEndereco())
                .append("email", funcionario.getEmail());

        getCollection().insertOne(document);

        return funcionario;
    }

    @Override
    public Funcionario updateFuncionario(String codigoFuncionario, Funcionario funcionario) {
        Document doc = findDocumentById(codigoFuncionario);

        Document document = doc;
        document.put("nome", funcionario.getNome());
        document.put("nomeSocial", funcionario.getNomeSocial());
        document.put("dataNascimento", funcionario.getDataNascimento().toString());
        document.put("cargo", funcionario.getCargo());
        document.put("cpf", funcionario.getCpf());
        document.put("rg", funcionario.getRg());
        document.put("endereco", funcionario.getEndereco());
        document.put("email", funcionario.getEmail());

        Document query = new Document();
        query.append("codigoFuncionario", codigoFuncionario);

        getCollection().replaceOne(query, document);

        return funcionario;
    }

    @Override
    public List<Funcionario> findAll() throws JsonProcessingException {
        List<Funcionario> funcionarios = new ArrayList<>();

        getCollection().find().forEach(func -> {
            try {
                funcionarios.add(mapFuncionario(func));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return funcionarios;
    }

    @Override
    public Funcionario findFuncionarioById(String codigoFuncionario) throws JsonProcessingException {
        Document document = findDocumentById(codigoFuncionario);

        return mapFuncionario(document);
    }

    @Override
    public void deleteFuncionarioById(String codigoFuncionario) {
        Document query = new Document();
        query.append("codigoFuncionario", codigoFuncionario);

        getCollection().deleteOne(query);
    }

    private Document findDocumentById(String codigoFuncionario) {
        Document document =
                getCollection().find(eq("codigoFuncionario", codigoFuncionario)).first();

        return document;
    }

    private Funcionario mapFuncionario(Document document) throws JsonProcessingException {
        String json = document.toJson();
        Funcionario funcionario = om.getObjectMapper().readValue(json, Funcionario.class);

        return funcionario;
    }
}
