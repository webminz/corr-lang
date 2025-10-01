package io.corrlang.techspaces;

import io.corrlang.domain.Endpoint;
import io.corrlang.domain.data.Data;
import io.corrlang.domain.data.DataBuilder;
import io.corrlang.domain.schemas.Schema;
import no.hvl.past.names.Name;

import java.io.InputStream;

public interface ParseDataCapability extends TechSpaceCapability {

    DataParser parseData();


    interface DataParser {


        Data parseData(InputStream source, DataBuilder builder) throws Exception;

    }
}
