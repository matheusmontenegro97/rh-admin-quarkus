package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import ifpe.br.com.config.ClockingCodec;
import ifpe.br.com.exceptions.EmployeeNotFoundException;
import ifpe.br.com.model.Clocking;
import ifpe.br.com.repository.ClockingRepository;
import ifpe.br.com.repository.EmployeeRepository;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ClockingRepositoryImpl implements ClockingRepository {

    private final MongoClient mongoClient;

    private final EmployeeRepository employeeRepository;

    @Inject
    public ClockingRepositoryImpl(MongoClient mongoClient, EmployeeRepository employeeRepository) {
        this.mongoClient = mongoClient;
        this.employeeRepository = employeeRepository;
    }

    public MongoCollection<Clocking> getCollection() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true)
                                .register(ClockingCodec.class)
                                .build()
                )
        );
        return mongoClient.getDatabase("rhadmin-spring").getCollection("clocking", Clocking.class)
                .withCodecRegistry(pojoCodecRegistry);
    }

    public Clocking saveClocking(Clocking clocking) throws EmployeeNotFoundException {

        if (Objects.isNull(employeeRepository.findEmployeeById(clocking.getEmployeeCode())))
            throw new EmployeeNotFoundException(String.format("Employee with id: %s not found", clocking.getEmployeeCode()));

        clocking.setClockingCode(UUID.randomUUID().toString());

        getCollection().insertOne(clocking);

        return clocking;
    }
}
