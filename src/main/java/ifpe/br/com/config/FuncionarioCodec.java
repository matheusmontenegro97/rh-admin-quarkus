package ifpe.br.com.config;

import ifpe.br.com.model.Cargo;
import ifpe.br.com.model.Endereco;
import ifpe.br.com.model.Funcionario;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FuncionarioCodec implements Codec<Funcionario> {

    private final Codec<Endereco> enderecoCodec;
    private final Codec<Cargo> cargoCodec;

    public FuncionarioCodec(Codec<Endereco> enderecoCodec, Codec<Cargo> cargoCodec) {
        this.enderecoCodec = enderecoCodec;
        this.cargoCodec = cargoCodec;
    }

    @Override
    public Funcionario decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String codigoFuncionario = reader.readString("codigoFuncionario");
        String nome = reader.readString("nome");
        String nomeSocial = reader.readString("nomeSocial");
        Cargo cargo = cargoCodec.decode(reader, decoderContext);
        LocalDate dataNascimento = LocalDate.parse(reader.readString("dataNascimento"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String cpf = reader.readString("cpf");
        String rg = reader.readString("rg");
        Endereco endereco = enderecoCodec.decode(reader, decoderContext);
        String email = reader.readString("email");
        reader.readEndDocument();

        return new Funcionario(codigoFuncionario, nome, nomeSocial, cargo, dataNascimento, cpf, rg, endereco, email);
    }

    @Override
    public void encode(BsonWriter writer, Funcionario funcionario, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("codigoFuncionario", funcionario.getCodigoFuncionario());
        writer.writeString("nome", funcionario.getNome());
        writer.writeString("nomeSocial", funcionario.getNomeSocial());
        cargoCodec.encode(writer, funcionario.getCargo(), encoderContext);
        writer.writeString("dataNascimento", funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        writer.writeString("cpf", funcionario.getCpf());
        writer.writeString("rg", funcionario.getRg());
        enderecoCodec.encode(writer, funcionario.getEndereco(), encoderContext);
        writer.writeString("email", funcionario.getEmail());
        writer.writeEndDocument();
    }

    @Override
    public Class<Funcionario> getEncoderClass() {
        return Funcionario.class;
    }
}