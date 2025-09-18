package io.corrlang.protocol;

import io.corrlang.domain.schemas.Schema;
import io.grpc.stub.StreamObserver;

public class Schema2Ccp {

    public void translate(Schema schema, StreamObserver<Ccp.SchemaElement> receiver) {
        schema.types().forEach(typ -> {

            if (schema.isStringType(typ)) {
                receiver.onNext(Ccp.SchemaElement.newBuilder()
                                .addFullyQualifiedName(schema.displayName(typ))
                                .setElementType(Ccp.SchemaElementType.DATA_TYPE)
                                .setDataTypeDetails(Ccp.DataTypeKinds.UTF8_BYTES)
                        .build());
            }

        });


        receiver.onCompleted();
    }

}
