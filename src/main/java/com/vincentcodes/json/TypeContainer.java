package com.vincentcodes.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This is essentially TypeReference<T> used by jackson
 * Since I do not want to download the whole jar of jackson
 * I copied the class source code from jackson. If devs of
 * jackson is not happy about it, I am willing to delete it.
 */
public class TypeContainer<T> {
    private final Type type;

    public TypeContainer() {
        Type superclass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    /**
     * May cast Type to ParameterizedType
     * <code>
     *     ((ParameterizedType)type.getType()).getActualTypeArguments()
     * </code>
     */
    public Type getType() {
        return type;
    }
}
