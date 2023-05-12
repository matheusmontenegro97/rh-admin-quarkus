package ifpe.br.com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ifpe.br.com.exceptions.EmployeeNotFoundException;
import ifpe.br.com.model.Employee;
import ifpe.br.com.repository.EmployeeRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rh/api/employee")
public class EmployeeController {

    @Inject
    private EmployeeRepository employeeRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() throws JsonProcessingException {
        return employeeRepository.findAllEmployees();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById
            (@PathParam("id") String id) throws JsonProcessingException {
        return employeeRepository.findEmployeeById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Employee updateEmployee
            (@PathParam("id") String id, Employee employee) throws EmployeeNotFoundException {
        return employeeRepository.updateEmployee(id, employee);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee saveEmployee(Employee employee) {

        return employeeRepository.saveEmployee(employee);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteEmployee(@PathParam("id") String id) {
        return employeeRepository.deleteEmployeeById(id);
    }
}
