package io.corrlang.domain.msgs;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class MsgHeader {

    public enum  MsgInstantSource {
        RECEIVED,
        ANSWERED,
        FAILED;
    }

    private final UUID id;

    private final Instant ts;

    private final MsgInstantSource tsSource;

    private final UUID refersTo;

    public MsgHeader(UUID id, Instant ts, MsgInstantSource tsSource, @Nullable UUID refersTo) {
        this.id = id;
        this.ts = ts;
        this.tsSource = tsSource;
        this.refersTo = refersTo;
    }

    public UUID getId() {
        return id;
    }

    public Instant getTs() {
        return ts;
    }

    public MsgInstantSource getTsSource() {
        return tsSource;
    }

    public Optional<UUID> getRefersTo() {
        return Optional.ofNullable(refersTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsgHeader msgHeader = (MsgHeader) o;
        return Objects.equals(id, msgHeader.id) && Objects.equals(ts, msgHeader.ts) && tsSource == msgHeader.tsSource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ts, tsSource);
    }


    public static MsgHeader received() {
        return new MsgHeader(UUID.randomUUID(), Instant.now(), MsgInstantSource.RECEIVED, null);
    }

    public static MsgHeader answered(UUID reference) {
        return new MsgHeader(UUID.randomUUID(), Instant.now(), MsgInstantSource.ANSWERED, reference);
    }

    public static MsgHeader failed(UUID reference) {
        return new MsgHeader(UUID.randomUUID(), Instant.now(), MsgInstantSource.FAILED, reference);

    }


}
