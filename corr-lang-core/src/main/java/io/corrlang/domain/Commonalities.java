package io.corrlang.domain;


import no.hvl.past.names.Name;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Abstract interface over a collection of typed trace links.
 */
public interface Commonalities {

    /**
     * The comprehensive system, which contains all the types for commonalities.
     */
    ComprSys typing();

    void put(Name commonalityType, Name commonalityName, Name relatedElementIdentifier, Name relatedElementSystemOrigin);

    Commonality get(Name commonalityType, Name commonalityName);

    void notifyDoubleRelationship(Name commonalityType, Name element, Name elementSystem, Set<Name> commonalityIds);

    Stream<Commonality> find(Name commonalityId);

    Stream<Commonality> iterate(Name commonalityType);

    Stream<Commonality> commonalitiesFor(Name system, Name element);

    class CommonalitiesHashMapImpl implements Commonalities {

        private final Map<Name, Commonality> db = new HashMap<>();
        private final ComprSys comprSys;

        public CommonalitiesHashMapImpl(ComprSys comprSys) {
            this.comprSys = comprSys;
        }

        @Override
        public ComprSys typing() {
            return comprSys;
        }

        @Override
        public void put(Name commonalityType, Name commonalityName, Name relatedElementIdentifier, Name relatedElementSystemOrigin) {
            Name combinedName = commonalityName.typeBy(commonalityType);
            if (this.db.containsKey(combinedName)) {
                this.db.put(combinedName, this.db.get(combinedName).add(relatedElementSystemOrigin, relatedElementIdentifier));
            } else {
                this.db.put(combinedName, Commonality.create(commonalityType, combinedName).add(relatedElementSystemOrigin, relatedElementIdentifier));
            }
        }

        @Override
        public Commonality get(Name commonalityType, Name commonalityName) {
            return db.get(commonalityName.typeBy(commonalityType));
        }

        @Override
        public void notifyDoubleRelationship(Name commonalityType, Name element, Name elementSystem, Set<Name> commonalityIds) {
            // TODO handle this somehow, most important certainly for identifications
        }

        @Override
        public Stream<Commonality> find(Name commonalityId) {
            return this.db.keySet().stream().filter(n -> n.firstPart().equals(commonalityId)).map(this.db::get);
        }

        @Override
        public Stream<Commonality> iterate(Name commonalityType) {
            return this.db.keySet().stream().filter(n -> n.secondPart().equals(commonalityType)).map(this.db::get);
        }

        @Override
        public Stream<Commonality> commonalitiesFor(Name system, Name element) {
            return this.db.values().parallelStream().filter(commonality -> commonality.contains(system, element));
        }
    }
}
