package ifpe.br.com.repository.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ifpe.br.com.config.AddressCodec;
import ifpe.br.com.config.EmployeeCodec;
import ifpe.br.com.config.RoleCodec;
import ifpe.br.com.model.Employee;
import ifpe.br.com.repository.EmployeeRepository;
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
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final MongoClient mongoClient;


    @Inject
    public EmployeeRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoCollection<Employee> getCollection() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true)
                                .register(AddressCodec.class)
                                .register(RoleCodec.class)
                                .register(EmployeeCodec.class)
                                .build()
                )
        );
        return mongoClient.getDatabase("rhadmin-spring").getCollection("employee", Employee.class)
                .withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        employee.setEmployeeCode(UUID.randomUUID().toString());

        getCollection().insertOne(employee);

        return employee;
    }

    @Override
    public Employee updateEmployee(String employeeCode, Employee employee) {

        employee.setEmployeeCode(employeeCode);
        getCollection().replaceOne(Filters.eq("employeeCode", employeeCode), employee);
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        FindIterable<Employee> employeeFindIterable = getCollection().find();

        for (Employee employee : employeeFindIterable) {
            employees.add(employee);
        }

        return employees;
    }

    @Override
    public Employee findEmployeeById(String employeeCode) {
        Employee employee =
                getCollection().find(eq("employeeCode", employeeCode)).first();

        return employee;
    }

    @Override
    public String deleteEmployeeById(String employeeCode) {
        getCollection().deleteOne(eq("employeeCode", employeeCode));
        return employeeCode;
    }
}
