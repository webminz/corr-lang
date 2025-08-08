package io.corrlang.techspaces;

import java.io.InputStream;

public interface ParseDataCapabilities extends TechSpaceCapability {

    InstanceParser<InputStream> parseData();


}
