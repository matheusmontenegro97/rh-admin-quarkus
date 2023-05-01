package ifpe.br.com.config;

import ifpe.br.com.model.Cargo;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class CargoCodec implements Codec<Cargo> {

    @Override
    public Cargo decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String nome = reader.readString("nome");
        Double horaSalario = reader.readDouble("horaSalario");
        reader.readEndDocument();

        return new Cargo(nome, horaSalario);
    }

    @Override
    public void encode(BsonWriter writer, Cargo cargo, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("nome", cargo.getNome());
        writer.writeDouble("horaSalario", cargo.getHoraSalario());
        writer.writeEndDocument();
    }

    @Override
    public Class<Cargo> getEncoderClass() {
        return Cargo.class;
    }
}


