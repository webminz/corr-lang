package io.corrlang.domain.msgs;

import io.corrlang.domain.exceptions.CorrLangException;

import no.hvl.past.names.UUIDIdentifier;
import no.hvl.past.serde.Format;
import no.hvl.past.trees.TreeReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInput implements MsgInput {

    private final boolean isLocal;
    private final URL urlLocation;

    private final String fileName;

    public FileInput(boolean isLocal, URL urlLocation, String fileName) {
        this.isLocal = isLocal;
        this.urlLocation = urlLocation;
        this.fileName = fileName;
    }


    private void read() throws URISyntaxException, IOException {

    }

    @Override
    public void read(MsgHeader header, Format format, TreeReceiver receiver) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(urlLocation.toURI()))) {
            format.read(new UUIDIdentifier(header.getId()), reader, receiver);
        } catch (IOException e) {
            throw CorrLangException.io(e);
        } catch (URISyntaxException e) {
            // TODO: REFACTORING: make proper
            throw new RuntimeException(e);
        }

    }


}
