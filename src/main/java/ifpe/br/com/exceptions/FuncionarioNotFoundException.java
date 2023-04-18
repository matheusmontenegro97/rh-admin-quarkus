package ifpe.br.com.exceptions;

public class FuncionarioNotFoundException extends Exception {

    public FuncionarioNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
