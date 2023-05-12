package ifpe.br.com.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ifpe.br.com.exceptions.EmployeeNotFoundException;
import ifpe.br.com.model.Employee;
import ifpe.br.com.model.Clocking;
import ifpe.br.com.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClockingRepositoryImplTest {
    @Mock
    private MongoClient mongoClient;

    @InjectMocks
    private ClockingRepositoryImpl clockingRepositoryImpl;

    @Mock
    private MongoDatabase database;

    @Mock
    private MongoCollection<Clocking> coll;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void beforeEach(){
        resetAllMocks();
    }

    void resetAllMocks(){
        reset(mongoClient);
        reset(database);
        reset(coll);
        reset(employeeRepository);
    }

    @Test
    void saveClockingSuccessTest() throws Exception {
        String employeeCode = UUID.randomUUID().toString();

        Clocking clocking = new Clocking();
        clocking.setEmployeeCode(employeeCode);

        Employee func = new Employee();
        func.setDateOfBirth(LocalDate.now());

        when(mongoClient.getDatabase(anyString())).thenReturn(database);
        when(database.getCollection(anyString(), eq(Clocking.class))).thenReturn(coll);
        when(coll.withCodecRegistry(any())).thenReturn(coll);
        when(employeeRepository.findEmployeeById(employeeCode)).thenReturn(func);

        clockingRepositoryImpl.saveClocking(clocking);

        verify(coll, times(1)).insertOne(clocking);
    }

    @Test
    void saveClockingErrorTest() {
        String employeeCode = UUID.randomUUID().toString();

        Clocking clocking = new Clocking();
        clocking.setEmployeeCode(employeeCode);

        when(employeeRepository.findEmployeeById(any())).thenReturn(null);

        assertThrows(EmployeeNotFoundException.class, () -> clockingRepositoryImpl.saveClocking(clocking));
    }

}
