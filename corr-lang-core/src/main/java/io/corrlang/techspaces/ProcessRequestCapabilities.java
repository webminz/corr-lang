package io.corrlang.techspaces;

import io.corrlang.domain.data.Data;

import java.io.InputStream;

public interface ProcessRequestCapabilities extends TechSpaceCapability {

    RequestHandler<InputStream, Data> requestHandler();

}
