package ifpe.br.com.config;

import ifpe.br.com.model.SickNote;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class SickNoteCodec implements Codec<SickNote> {
    @Override
    public SickNote decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String sickNoteCode = bsonReader.readString("sickNoteCode");
        String employeeCode = bsonReader.readString("employeeCode");
        String sickNode = bsonReader.readString("sickNode");
        bsonReader.readEndDocument();

        return new SickNote(sickNoteCode, employeeCode, sickNode);
    }

    @Override
    public void encode(BsonWriter bsonWriter, SickNote sickNote, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("sickNoteCode", sickNote.getSickNoteCode());
        bsonWriter.writeString("employeeCode", sickNote.getEmployeeCode());
        bsonWriter.writeString("sickNode", sickNote.getSickNote());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<SickNote> getEncoderClass() {
        return SickNote.class;
    }
}