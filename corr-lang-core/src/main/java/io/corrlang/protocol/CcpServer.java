package io.corrlang.protocol;

import io.grpc.ServerBuilder;

public class CcpServer {

    public CcpServer(int port, CcpService service) {
        ServerBuilder.forPort(port).addService(service).build();
    }
}
