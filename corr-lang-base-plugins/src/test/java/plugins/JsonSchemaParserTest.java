package plugins;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.corrlang.plugins.json.JsonSchemaBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class JsonSchemaParserTest {

    @Test
    public void testReadUser() throws IOException {
        try (InputStream stream = JsonSchemaParserTest.class.getResourceAsStream("/jsonschema/user.json")) {
            JsonMapper mapper = new JsonMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            JsonSchemaBean userSchema = mapper.readValue(stream, JsonSchemaBean.class);
            Assertions.assertNotNull(userSchema);
        }
    }
}
