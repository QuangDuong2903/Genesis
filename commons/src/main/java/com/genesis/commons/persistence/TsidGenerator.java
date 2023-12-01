package com.genesis.commons.persistence;

import com.github.f4b6a3.tsid.TsidFactory;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.Instant;

public class TsidGenerator implements IdentifierGenerator {

    private final int datacenter = 1;
    private final int worker = 1;
    private final int node = (datacenter << 5 | worker);

    private final TsidFactory factory = TsidFactory.builder()
            .withRandomFunction(byte[]::new)
            .withCustomEpoch(Instant.ofEpochMilli(1288834974657L))
            .withNode(node)
            .build();

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return factory.create().toLong();
    }

}
