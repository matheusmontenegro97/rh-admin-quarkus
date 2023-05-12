package ifpe.br.com.config;

import ifpe.br.com.model.Address;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AddressCodec implements Codec<Address> {

    @Override
    public Address decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String zipCode = reader.readString("zipCode");
        String street = reader.readString("street");
        String number = reader.readString("number");
        String district = reader.readString("district");
        String city = reader.readString("city");
        String state = reader.readString("state");
        reader.readEndDocument();

        return new Address(zipCode, street, number, district, city, state);
    }

    @Override
    public void encode(BsonWriter writer, Address address, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("zipCode", address.getZipCode());
        writer.writeString("street", address.getStreet());
        writer.writeString("number", address.getNumber());
        writer.writeString("district", address.getDistrict());
        writer.writeString("city", address.getCity());
        writer.writeString("state", address.getState());
        writer.writeEndDocument();
    }

    @Override
    public Class<Address> getEncoderClass() {
        return Address.class;
    }
}

