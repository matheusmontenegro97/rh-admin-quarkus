package ifpe.br.com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private String employeeCode;
    private String name;
    private String socialName;
    private Role role;
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String individualTaxpayerRegistration;
    private String nationalIdentityCard;
    private Address address;
    private String email;

    public Employee() {
    }

    public Employee(String employeeCode, String name, String socialName, Role role, LocalDate dateOfBirth, String individualTaxpayerRegistration, String nationalIdentityCard, Address address, String email) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.socialName = socialName;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.individualTaxpayerRegistration = individualTaxpayerRegistration;
        this.nationalIdentityCard = nationalIdentityCard;
        this.address = address;
        this.email = email;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public Role getCargo() {
        return role;
    }

    public void setCargo(Role role) {
        this.role = role;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIndividualTaxpayerRegistration() {
        return individualTaxpayerRegistration;
    }

    public void setIndividualTaxpayerRegistration(String individualTaxpayerRegistration) {
        this.individualTaxpayerRegistration = individualTaxpayerRegistration;
    }

    public String getNationalIdentityCard() {
        return nationalIdentityCard;
    }

    public void setNationalIdentityCard(String nationalIdentityCard) {
        this.nationalIdentityCard = nationalIdentityCard;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
