package ifpe.br.com.config;

import ifpe.br.com.model.Ponto;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.LocalDate;
import java.time.LocalTime;

public class PontoCodec implements Codec<Ponto> {

    @Override
    public Ponto decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        String codigoPonto = reader.readString("codigoPonto");
        String codigoFuncionario = reader.readString("codigoFuncionario");
        LocalTime horaEntradaTrabalho = LocalTime.parse(reader.readString("horaEntradaTrabalho"));
        LocalTime horaSaidaAlmoco = LocalTime.parse(reader.readString("horaSaidaAlmoco"));
        LocalTime horaVoltaAlmoco = LocalTime.parse(reader.readString("horaVoltaAlmoco"));
        LocalTime horaSaidaTrabalho = LocalTime.parse(reader.readString("horaSaidaTrabalho"));
        LocalDate data = LocalDate.parse(reader.readString("data"));

        reader.readEndDocument();

        return new Ponto(codigoPonto, codigoFuncionario, horaEntradaTrabalho, horaSaidaAlmoco, horaVoltaAlmoco, horaSaidaTrabalho, data);
    }

    @Override
    public void encode(BsonWriter writer, Ponto ponto, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeName("codigoPonto");
        writer.writeString(ponto.getCodigoPonto());

        writer.writeName("codigoFuncionario");
        writer.writeString(ponto.getCodigoFuncionario());

        writer.writeName("horaEntradaTrabalho");
        writer.writeString(ponto.getHoraEntradaTrabalho().toString());

        writer.writeName("horaSaidaAlmoco");
        writer.writeString(ponto.getHoraSaidaAlmoco().toString());

        writer.writeName("horaVoltaAlmoco");
        writer.writeString(ponto.getHoraVoltaAlmoco().toString());

        writer.writeName("horaSaidaTrabalho");
        writer.writeString(ponto.getHoraSaidaTrabalho().toString());

        writer.writeName("data");
        writer.writeString(ponto.getData().toString());

        writer.writeEndDocument();
    }

    @Override
    public Class<Ponto> getEncoderClass() {
        return Ponto.class;
    }
}
