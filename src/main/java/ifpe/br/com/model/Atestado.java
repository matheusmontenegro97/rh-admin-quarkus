package ifpe.br.com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Atestado {
    private String codigoAtestado;
    private String codigoFuncionario;
    private String atestado;

    public Atestado(String codigoFuncionario, String atestado) {
        this.codigoFuncionario = codigoFuncionario;
        this.atestado = atestado;
    }

    public Atestado() {
    }

    public String getCodigoAtestado() {
        return codigoAtestado;
    }

    public void setCodigoAtestado(String codigoAtestado) {
        this.codigoAtestado = codigoAtestado;
    }

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getAtestado() {
        return atestado;
    }

    public void setAtestado(String atestado) {
        this.atestado = atestado;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("codigoAtestado: ")
                .append(codigoAtestado)
                .append("\n")
                .append("codigoFuncionario: ")
                .append(codigoFuncionario)
                .toString();
    }
}
