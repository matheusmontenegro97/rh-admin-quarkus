package ifpe.br.com.config;

import ifpe.br.com.model.Endereco;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class EnderecoCodec implements Codec<Endereco> {

    @Override
    public Endereco decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String cep = reader.readString("cep");
        String rua = reader.readString("rua");
        String numero = reader.readString("numero");
        String bairro = reader.readString("bairro");
        String cidade = reader.readString("cidade");
        String estado = reader.readString("estado");
        reader.readEndDocument();

        return new Endereco(cep, rua, numero, bairro, cidade, estado);
    }

    @Override
    public void encode(BsonWriter writer, Endereco endereco, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("cep", endereco.getCep());
        writer.writeString("rua", endereco.getRua());
        writer.writeString("numero", endereco.getNumero());
        writer.writeString("bairro", endereco.getBairro());
        writer.writeString("cidade", endereco.getCidade());
        writer.writeString("estado", endereco.getEstado());
        writer.writeEndDocument();
    }

    @Override
    public Class<Endereco> getEncoderClass() {
        return Endereco.class;
    }
}

