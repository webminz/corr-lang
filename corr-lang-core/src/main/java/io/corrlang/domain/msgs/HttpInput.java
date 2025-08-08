package io.corrlang.domain.msgs;

import io.corrlang.domain.exceptions.CorrLangException;
import jakarta.servlet.ServletRequest;
import no.hvl.past.names.UUIDIdentifier;
import no.hvl.past.serde.Format;
import no.hvl.past.trees.TreeReceiver;

import java.io.IOException;

public class HttpInput implements MsgInput {

    private final ServletRequest httpRequest;

    public HttpInput(ServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }



    @Override
    public void read(MsgHeader header, Format format, TreeReceiver receiver) {
        try {
            format.read(new UUIDIdentifier(header.getId()), httpRequest.getInputStream(), httpRequest.getContentLength(), receiver);
        } catch (IOException e) {
            throw CorrLangException.io(e);
        }
    }
}
