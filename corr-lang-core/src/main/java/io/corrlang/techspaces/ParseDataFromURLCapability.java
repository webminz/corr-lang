package io.corrlang.techspaces;

import io.corrlang.domain.data.Data;
import io.corrlang.domain.data.DataBuilder;

import java.net.URL;

public interface ParseDataFromURLCapability {

    FromURLDataParser dataParser();

    @FunctionalInterface
    interface FromURLDataParser {

        Data parseData(URL source, DataBuilder builder) throws Exception;
    }
}
