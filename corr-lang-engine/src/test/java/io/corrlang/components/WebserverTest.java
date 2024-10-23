package io.corrlang.components;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import io.corrlang.server.HttpMethod;
import io.corrlang.server.Webserver;
import io.corrlang.server.WebserviceRequestHandler;
import no.hvl.past.util.GenericIOHandler;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class WebserverTest {


    @Test
    public void testWebserver() {
        Webserver webserver = Webserver.start(9001, () -> {}, () -> {});
        webserver.registerHandler(new WebserviceRequestHandler("/json", HttpMethod.GET, WebserviceRequestHandler.ResponseType.JSON) {

            @Override
            protected GenericIOHandler createHandler(Map<String, String> headers, Map<String, List<String>> queryParams, Map<String, String> cookies, Map<String, Object> sessionData) {
                return new GenericIOHandler() {
                    @Override
                    public void handle(InputStream i, OutputStream o) throws IOException {
                        JsonGenerator generator = new JsonFactory().createGenerator(o);
                        generator.writeStartObject();
                        generator.writeFieldName("name");
                        generator.writeString("Patrick");
                        generator.writeFieldName("age");
                        generator.writeNumber(28);
                        generator.writeEndObject();
                        generator.flush();
                        o.close();
                    }
                };
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:9001/json").openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = inputReader.readLine()) != null) {
                result.append(line);
                result.append(System.lineSeparator());
            }
            inputReader.close();
            connection.disconnect();
            assertEquals("{\"name\":\"Patrick\",\"age\":28}\n", result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

    }
}
