package org.rrd4j.core;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory class which creates actual {@link org.rrd4j.core.RrdMemoryBackend} objects. Rrd4j's support
 * for in-memory RRDs is still experimental. You should know that all active RrdMemoryBackend
 * objects are held in memory, each backend object stores RRD data in one big byte array. This
 * implementation is therefore quite basic and memory hungry but runs very fast.
 * <p>
 * Calling {@link org.rrd4j.core.RrdDb#close() close()} on RrdDb objects does not release any memory at all
 * (RRD data must be available for the next <code>new RrdDb(path)</code> call. To release allocated
 * memory, you'll have to call {@link #delete(java.lang.String) delete(path)} method of this class.
 *
 */
public class RrdMemoryBackendFactory extends RrdBackendFactory {
    protected final Map<String, RrdMemoryBackend> backends = new ConcurrentHashMap<String, RrdMemoryBackend>();

    /**
     * {@inheritDoc}
     *
     * Creates RrdMemoryBackend object.
     */
    protected RrdBackend open(String id, boolean readOnly) throws IOException {
        RrdMemoryBackend backend;
        if (backends.containsKey(id)) {
            backend = backends.get(id);
        }
        else {
            backend = new RrdMemoryBackend(id);
            backends.put(id, backend);
        }
        return backend;
    }

    /**
     * {@inheritDoc}
     *
     * Method to determine if a memory storage with the given ID already exists.
     */
    protected boolean exists(String id) {
        return backends.containsKey(id);
    }

    /** {@inheritDoc} */
    protected boolean shouldValidateHeader(String path) throws IOException {
        return false;
    }

    /**
     * Removes the storage with the given ID from the memory.
     *
     * @param id Storage ID
     * @return a boolean.
     */
    public boolean delete(String id) {
        if (backends.containsKey(id)) {
            backends.remove(id);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the name of this factory.
     *
     * @return Factory name (equals to "MEMORY").
     */
    public String getName() {
        return "MEMORY";
    }
}
