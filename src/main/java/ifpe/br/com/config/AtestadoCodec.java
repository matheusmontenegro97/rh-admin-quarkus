package ifpe.br.com.config;

import ifpe.br.com.model.Atestado;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AtestadoCodec implements Codec<Atestado> {
    @Override
    public Atestado decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String codigoAtestado = bsonReader.readString("codigoAtestado");
        String codigoFuncionario = bsonReader.readString("codigoFuncionario");
        String atestado = bsonReader.readString("atestado");
        bsonReader.readEndDocument();

        return new Atestado(codigoAtestado, codigoFuncionario, atestado);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Atestado atestado, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("codigoAtestado", atestado.getCodigoAtestado());
        bsonWriter.writeString("codigoFuncionario", atestado.getCodigoFuncionario());
        bsonWriter.writeString("atestado", atestado.getAtestado());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Atestado> getEncoderClass() {
        return Atestado.class;
    }
}