package ifpe.br.com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ifpe.br.com.exceptions.EmployeeNotFoundException;
import ifpe.br.com.model.Employee;
import ifpe.br.com.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void getEmployeesSuccessTest() throws JsonProcessingException {
        Employee employee = new Employee();
        employee.setName("name");

        when(employeeRepository.findAllEmployees()).thenReturn(Arrays.asList(employee));

        List<Employee> response = employeeController.getAllEmployees();

        assertEquals(Arrays.asList(employee), response);
    }

    @Test
    void getEmployeeByIdSuccessTest() throws JsonProcessingException {
        Employee employee = new Employee();
        employee.setName("name");

        when(employeeRepository.findEmployeeById(anyString())).thenReturn(employee);

        Employee response = employeeController.getEmployeeById(UUID.randomUUID().toString());

        assertEquals(employee, response);
    }

    @Test
    void updateEmployeeSuccessTest() throws EmployeeNotFoundException {
        Employee employee = new Employee();
        employee.setName("name");

        when(employeeRepository.updateEmployee(anyString(), any())).thenReturn(employee);

        Employee response = employeeController.updateEmployee(UUID.randomUUID().toString(), employee);

        assertEquals(employee, response);
    }

    @Test
    void saveEmployeeSuccessTest() {
        Employee employee = new Employee();
        employee.setName("name");

        when(employeeRepository.saveEmployee(any())).thenReturn(employee);

        Employee response = employeeController.saveEmployee(employee);

        assertEquals(employee, response);
    }

    @Test
    void deleteEmployeeSuccessTest() {
        String employeeCode = UUID.randomUUID().toString();
        String message = employeeCode;

        when(employeeRepository.deleteEmployeeById(anyString())).thenReturn(message);

        String response = employeeController.deleteEmployee(employeeCode);

        assertEquals(message, response);
    }
}
