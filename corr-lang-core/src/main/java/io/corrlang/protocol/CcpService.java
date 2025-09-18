package io.corrlang.protocol;

import io.grpc.stub.StreamObserver;

public class CcpService extends CorrLangMetaServiceGrpc.CorrLangMetaServiceImplBase {

    @Override
    public void inspectSchema(Ccp.InspectEndpointSchemaRequest request, StreamObserver<Ccp.SchemaElement> responseObserver) {
        String techSpace = request.getTechSpaceName();
        if (request.hasLocalFileSystemLocation()) {
            String location = request.getLocalFileSystemLocation();
        }
    }
}
