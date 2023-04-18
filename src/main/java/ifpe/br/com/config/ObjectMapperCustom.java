package ifpe.br.com.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObjectMapperCustom {

    public ObjectMapper getObjectMapper() {
        ObjectMapper om = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return om;
    }
}
