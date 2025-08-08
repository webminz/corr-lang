package io.corrlang.techspaces;

import java.io.InputStream;

public interface ParseMessageCapabilities extends TechSpaceCapability {

    /**
     * Ability to parse a binary request.
     */
    MessageParser<InputStream> parseMessage();
}
