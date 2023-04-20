package com.warrior.luminate.service.deduplication;

import com.warrior.luminate.service.deduplication.build.Builder;
import com.warrior.luminate.service.deduplication.service.DeduplicationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author WARRIOR
 */
@Service
public class DeduplicationHolder {

    private final Map<Integer, Builder> builderHolder = new HashMap<>(4);
    private final Map<Integer, DeduplicationService> serviceHolder = new HashMap<>(4);

    public Builder selectBuilder(Integer key) {
        return builderHolder.get(key);
    }

    public DeduplicationService selectService(Integer key) {
        return serviceHolder.get(key);
    }

    public void putBuilder(Integer key, Builder builder) {
        builderHolder.put(key, builder);
    }

    public void putService(Integer key, DeduplicationService service) {
        serviceHolder.put(key, service);
    }
}
