package ifpe.br.com.exceptions;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
