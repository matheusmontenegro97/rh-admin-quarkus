package ifpe.br.com.config;

import ifpe.br.com.model.Role;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class RoleCodec implements Codec<Role> {

    @Override
    public Role decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String roleName = reader.readString("roleName");
        Double hourlyWage = reader.readDouble("hourlyWage");
        reader.readEndDocument();

        return new Role(roleName, hourlyWage);
    }

    @Override
    public void encode(BsonWriter writer, Role role, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("roleName", role.getRoleName());
        writer.writeDouble("hourlyWage", role.getHourlyWage());
        writer.writeEndDocument();
    }

    @Override
    public Class<Role> getEncoderClass() {
        return Role.class;
    }
}


