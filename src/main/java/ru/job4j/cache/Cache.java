package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> function = (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base update = new Base(k, v.getVersion() + 1);
            update.setName(model.getName());
            return update;
        };
        return memory.computeIfPresent(model.getId(), function) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(Integer id) {
        return memory.get(id);
    }
}