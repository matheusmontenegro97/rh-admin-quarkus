package ifpe.br.com.config;

import ifpe.br.com.model.Clocking;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClockingCodec implements Codec<Clocking> {

    @Override
    public Clocking decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();

        String clockingCode = reader.readString("clockingCode");
        String employeeCode = reader.readString("employeeCode");
        LocalTime clockIn = LocalTime.parse(reader.readString("clockIn"));
        LocalTime clockInLunch = LocalTime.parse(reader.readString("clockInLunch"));
        LocalTime clockOutLunch = LocalTime.parse(reader.readString("clockOutLunch"));
        LocalTime clockOut = LocalTime.parse(reader.readString("clockOut"));
        LocalDate date = LocalDate.parse(reader.readString("date"));

        reader.readEndDocument();

        return new Clocking(clockingCode, employeeCode, clockIn, clockInLunch, clockOutLunch, clockOut, date);
    }

    @Override
    public void encode(BsonWriter writer, Clocking clocking, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeName("clockingCode");
        writer.writeString(clocking.getClockingCode());

        writer.writeName("employeeCode");
        writer.writeString(clocking.getEmployeeCode());

        writer.writeName("clockIn");
        writer.writeString(clocking.getClockIn().toString());

        writer.writeName("clockInLunch");
        writer.writeString(clocking.getClockInLunch().toString());

        writer.writeName("clockOutLunch");
        writer.writeString(clocking.getClockOutLunch().toString());

        writer.writeName("clockOut");
        writer.writeString(clocking.getClockOut().toString());

        writer.writeName("date");
        writer.writeString(clocking.getDate().toString());

        writer.writeEndDocument();
    }

    @Override
    public Class<Clocking> getEncoderClass() {
        return Clocking.class;
    }
}
