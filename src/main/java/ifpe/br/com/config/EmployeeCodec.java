package ifpe.br.com.config;

import ifpe.br.com.model.Address;
import ifpe.br.com.model.Employee;
import ifpe.br.com.model.Role;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeCodec implements Codec<Employee> {

    private final Codec<Address> addressCodec;
    private final Codec<Role> roleCodec;

    public EmployeeCodec(Codec<Address> addressCodec, Codec<Role> roleCodec) {
        this.addressCodec = addressCodec;
        this.roleCodec = roleCodec;
    }

    @Override
    public Employee decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String employeeCode = reader.readString("employeeCode");
        String name = reader.readString("name");
        String socialName = reader.readString("socialName");
        Role role = roleCodec.decode(reader, decoderContext);
        LocalDate dateOfBirth = LocalDate.parse(reader.readString("dateOfBirth"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String individualTaxpayerRegistration = reader.readString("individualTaxpayerRegistration");
        String nationalIdentityCard = reader.readString("nationalIdentityCard");
        Address address = addressCodec.decode(reader, decoderContext);
        String email = reader.readString("email");
        reader.readEndDocument();

        return new Employee(employeeCode, name, socialName, role, dateOfBirth, individualTaxpayerRegistration, nationalIdentityCard, address, email);
    }

    @Override
    public void encode(BsonWriter writer, Employee employee, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("employeeCode", employee.getEmployeeCode());
        writer.writeString("name", employee.getName());
        writer.writeString("socialName", employee.getSocialName());
        roleCodec.encode(writer, employee.getCargo(), encoderContext);
        writer.writeString("dateOfBirth", employee.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        writer.writeString("individualTaxpayerRegistration", employee.getIndividualTaxpayerRegistration());
        writer.writeString("nationalIdentityCard", employee.getNationalIdentityCard());
        addressCodec.encode(writer, employee.getAddress(), encoderContext);
        writer.writeString("email", employee.getEmail());
        writer.writeEndDocument();
    }

    @Override
    public Class<Employee> getEncoderClass() {
        return Employee.class;
    }
}