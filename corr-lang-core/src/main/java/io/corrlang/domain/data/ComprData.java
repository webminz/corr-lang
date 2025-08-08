package io.corrlang.domain.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.corrlang.domain.Commonalities;
import io.corrlang.domain.Commonality;
import io.corrlang.domain.ComprSys;
import io.corrlang.domain.Endpoint;
import io.corrlang.domain.keys.Key;
import io.corrlang.domain.schemas.Schema;
import no.hvl.past.names.Name;

import java.util.*;
import java.util.stream.Stream;

public class ComprData implements Data {

    public static final class Builder {
        private final Map<Name, Data> distributedData = new LinkedHashMap<>();
        private final ComprSys comprSys;
        private final Commonalities commonalitiesDB;

        public Builder(ComprSys comprSys) {
            this.comprSys = comprSys;
            this.commonalitiesDB = new Commonalities.CommonalitiesHashMapImpl(comprSys);
        }

        public Builder addDataSource(Data data) {
            distributedData.put(data.origin().asId(), data);

            // indexing
            Multimap<Name, Key> keys = HashMultimap.create();
            comprSys.relationKeys()
                    .filter(k -> k.sourceSystem().equals(data.origin()))
                    .forEach(key -> keys.put(key.sourceType(), key));

            if (!keys.isEmpty()) {
                data.index(keys, commonalitiesDB);
            }

            return this;
        }

        public ComprData build() {
            return new ComprData(commonalitiesDB, distributedData, comprSys);
        }

    }

    private final Commonalities commonalities;
    private final Map<Name, Data> distributedData;
    private final ComprSys comprSys;

    public ComprData(Commonalities commonalities, Map<Name, Data> distributedData, ComprSys comprSys) {
        this.commonalities = commonalities;
        this.distributedData = distributedData;
        this.comprSys = comprSys;
    }

    @Override
    public Endpoint origin() {
        return new Endpoint(0, comprSys.name(), new Schema(comprSys.schema())); // comprSys;
    }

    @Override
    public Stream<Name> all(Name type) {
        if (comprSys.isMerged(type)) {
            return Stream.empty(); // TODO do correctly
        }
        if (distributedData.containsKey(type.firstPart())) {
            return distributedData.get(type.firstPart()).all(type.unprefixTop());
        }
        return commonalities.iterate(type).map(Commonality::getId);
    }

    @Override
    public Stream<Name> properties(Name elementId, Name propertyName) {
        if (comprSys.isMerged(propertyName)) {
            return Stream.empty(); // TODO do correctly
        }
        if (distributedData.containsKey(propertyName.firstPart())) {
            return distributedData.get(propertyName.firstPart()).properties(elementId, propertyName.unprefixTop());
        }
        if (propertyName.unprefixAll().isProjection()) {
            Name src = propertyName.unprefixAll().firstPart();
            Name trg = propertyName.unprefixAll().secondPart();
            return commonalities.get(src, elementId).projectionOn(trg).map(Stream::of).orElse(Stream.empty());
        }
        return Stream.empty();
    }

    @Override
    public void index(Multimap<Name, Key> keys, Commonalities commonalities) {
        // Nothing to do here
    }

    @Override
    public Name typeOf(Name element) {
        for (Data d : distributedData.values()) {
            Name name = d.typeOf(element);
            if (name != null) {
                return name;
            }
        }
        return commonalities.find(element).findFirst().map(Commonality::getId).orElse(null);
    }

    public Commonalities getCommonalities() {
        return commonalities;
    }
}
