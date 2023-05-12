package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import ifpe.br.com.config.SickNoteCodec;
import ifpe.br.com.exceptions.EmployeeNotFoundException;
import ifpe.br.com.model.SickNote;
import ifpe.br.com.repository.EmployeeRepository;
import ifpe.br.com.repository.SickNoteRepository;
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
public class SickNoteRepositoryImpl implements SickNoteRepository {

    private final MongoClient mongoClient;

    private final EmployeeRepository employeeRepository;

    @Inject
    public SickNoteRepositoryImpl(MongoClient mongoClient, EmployeeRepository employeeRepository) {
        this.mongoClient = mongoClient;
        this.employeeRepository = employeeRepository;
    }

    public MongoCollection<SickNote> getCollection() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true)
                                .register(SickNoteCodec.class)
                                .build()
                )
        );
        return mongoClient.getDatabase("rhadmin-spring").getCollection("sickNote", SickNote.class)
                .withCodecRegistry(pojoCodecRegistry);
    }

    private GridFSBucket getGridFSBuckets() {
        return GridFSBuckets.create(mongoClient.getDatabase("rhadmin-quarkus"));
    }

    public SickNote saveSickNote(SickNote sickNote) throws Exception {

        if (Objects.isNull(employeeRepository.findEmployeeById(sickNote.getEmployeeCode())))
            throw new EmployeeNotFoundException(String.format("Employee with id: %s not found", sickNote.getEmployeeCode()));

        sickNote.setSickNoteCode(UUID.randomUUID().toString());

        File file = new File(sickNote.getSickNote());
        InputStream targetStream = new FileInputStream(file);
        getGridFSBuckets().uploadFromStream(sickNote.getSickNoteCode(), targetStream);

        getCollection().insertOne(sickNote);

        return sickNote;
    }
}
