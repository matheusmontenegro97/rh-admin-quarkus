package ifpe.br.com.repository.impl;


import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import ifpe.br.com.model.Employee;
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
class EmployeeRepositoryImplTest {

    @Mock
    private MongoClient mongoClient;

    @InjectMocks
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    @Mock
    private MongoDatabase database;

    @Mock
    private MongoCollection<Employee> coll;

    @Mock
    private FindIterable<Employee> findIterable;

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
    void saveEmployeeTest(){
        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Employee.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);

        Employee func = new Employee();
        func.setDateOfBirth(LocalDate.now());

        employeeRepositoryImpl.saveEmployee(func);

        verify(coll, times(1)).insertOne(func);
    }

    @Test
    void updateEmployeeTest(){
        String employeeCode = UUID.randomUUID().toString();
        Bson filter = Filters.eq("employeeCode", employeeCode);

        Employee employee = new Employee();
        employee.setDateOfBirth(LocalDate.now());

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Employee.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);

        employeeRepositoryImpl.updateEmployee(employeeCode, employee);

        verify(coll, times(1)).replaceOne(filter, employee);
    }

    @Test
    void findAllTest() {
        Employee employee = new Employee();
        employee.setDateOfBirth(LocalDate.now());

        MongoCursor cursor = mock(MongoCursor.class);

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Employee.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(coll.find()).thenReturn(findIterable);
        when(findIterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext())
                .thenReturn(true)
                .thenReturn(false);
        when(cursor.next())
                .thenReturn(employee);

        List<Employee> employeeList = employeeRepositoryImpl.findAllEmployees();

        assertFalse(employeeList.isEmpty());
    }

    @Test
    void findEmployeeByIdTest() {
        String employeeCode = UUID.randomUUID().toString();
        Employee emp = new Employee();
        emp.setEmployeeCode(employeeCode);
        emp.setDateOfBirth(LocalDate.now());

        Bson filter = Filters.eq("employeeCode", employeeCode);

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Employee.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(coll.find(filter)).thenReturn(findIterable);
        when(findIterable.first()).thenReturn(emp);

        Employee employee = employeeRepositoryImpl.findEmployeeById(employeeCode);

        assertEquals(emp, employee);
    }

    @Test
    void deleteByIdTest() {
        String employeeCode = UUID.randomUUID().toString();
        Bson filter = Filters.eq("employeeCode", employeeCode);

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Employee.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(coll.deleteOne(filter)).thenReturn(DeleteResult.acknowledged(1));

        employeeRepositoryImpl.deleteEmployeeById(employeeCode);

        verify(coll,times(1)).deleteOne(filter);
    }
}
