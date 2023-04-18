package ifpe.br.com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cargo {
    private String nome;
    private Double horaSalario;

    public Cargo() {
    }

    public Cargo(String nome, Double horaSalario) {
        this.nome = nome;
        this.horaSalario = horaSalario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getHoraSalario() {
        return horaSalario;
    }

    public void setHoraSalario(Double horaSalario) {
        this.horaSalario = horaSalario;
    }
}