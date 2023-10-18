package com.vincentcodes.json;

import com.vincentcodes.json.annotation.JsonSerializable;
import com.vincentcodes.json.parser.ConversionException;
import com.vincentcodes.json.parser.JsonParser;
import com.vincentcodes.json.parser.UnexpectedToken;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.vincentcodes.json.TypeUtils.*;

/**
 * Currently supports mapping of common java types (eg. List, HashMap)
 */
// TODO: This class is getting large, create a serializer and deserializer
public class ObjectMapper {
    private final JsonParser jsonParser;
    private final ObjectMapperConfig config;
    private final ArrayList<Object> trace = new ArrayList<>();

    /**
     * Uses default {@link ObjectMapperConfig} values
     */
    public ObjectMapper(){
        jsonParser = new JsonParser();
        this.config = new ObjectMapperConfig.Builder().build();
    }

    public ObjectMapper(ObjectMapperConfig config){
        jsonParser = new JsonParser();
        this.config = config;
    }

    // Object

    /**
     * Only this method can return both Object and Array
     */
    public <T> T jsonToObject(String json, TypeContainer<T> type) throws CannotMapToObjectException{
        try {
            Class<?> objectType = type.getType() instanceof ParameterizedType?
                    (Class<?>) ((ParameterizedType)type.getType()).getRawType() : (Class<?>) type.getType();
            Class<?> objectSuperClass = objectType.getSuperclass();
            if(objectType.isArray()){
                Object arrayObject = Array.newInstance(objectType.getComponentType(), 0);
                return (T)jsonToArray(json, objectType.getComponentType()).toArray((Object[]) arrayObject);
            }else if(List.class.isAssignableFrom(objectSuperClass == null? objectType : objectSuperClass)){
                Type[] genericTypes = ((ParameterizedType)type.getType()).getActualTypeArguments();
                return (T)jsonToArray(json, (Class<T>) genericTypes[0]);
            }else if(Set.class.isAssignableFrom(objectSuperClass == null? objectType : objectSuperClass)){
                Type[] genericTypes = ((ParameterizedType)type.getType()).getActualTypeArguments();
                return (T)new HashSet<>(jsonToArray(json, (Class<T>) genericTypes[0]));
            }else if(Map.class.isAssignableFrom(objectSuperClass == null? objectType : objectSuperClass)){
                Type[] genericTypes = ((ParameterizedType)type.getType()).getActualTypeArguments();
                return (T) jsonToHashMapUnsafe(new JsonObject(jsonParser.parseJson(json)), (Class<?>)genericTypes[0], (Class<?>) genericTypes[1]);
            }
            JsonObject jsonObject = new JsonObject(jsonParser.parseJson(json));
            return jsonToObject(jsonObject, type);
        }catch (UnexpectedToken e){
            throw new CannotMapToObjectException(e);
        }
    }

    /**
     * @return Array will not be not returned / processed correctly
     */
    public <T> T jsonToObject(JsonObject jsonObject, TypeContainer<T> type) throws CannotMapToObjectException{
        // may make reference to test_json_to_person_object()
        return jsonToObject(jsonObject, (Class<T>) type.getType());
    }
    /**
     * Field names inside a class are used as json property names by default.
     * @return Array will not be not returned / processed correctly (use {@link ObjectMapper#jsonToArray(String, Class)} instead)
     */
    public <T> T jsonToObject(String json, Class<T> type) throws CannotMapToObjectException {
        try {
            JsonObject jsonObject = new JsonObject(jsonParser.parseJson(json));
            return jsonToObject(jsonObject, type);
        }catch (UnexpectedToken e){
            throw new CannotMapToObjectException(e);
        }
    }
    //Safe
    public <T> T jsonToObject(JsonObject jsonObject, Class<T> type) throws CannotMapToObjectException{
        reset();
        if(!config.isSerializableAnnotationRequired())
            return jsonToObjectUnsafe(jsonObject, type);
        else if(config.isSerializableAnnotationRequired() && type.isAnnotationPresent(JsonSerializable.class))
            return jsonToObjectUnsafe(jsonObject, type);
        throw new CannotMapToObjectException("JsonSerializable annotation is required on type '" + type + "'");
    }

    /**
     * @return Array will not be not returned / processed correctly (use {@link ObjectMapper#jsonToArray(String, Class)} instead)
     */
    private <T> T jsonToObjectUnsafe(JsonObject jsonObject, Class<T> type) throws CannotMapToObjectException{
        Class<?> fieldType = null;
        Class<?> fieldTypeSuperclass;
        String fieldName = null;
        T obj = null;
        try {
            // default constructor is needed
            Constructor<T> cons = type.getDeclaredConstructor();
            obj = cons.newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                fieldType = field.getType();
                fieldTypeSuperclass = fieldType.getSuperclass();
                fieldName = field.getName();

                pushTraceItem(type + "[" + fieldName + "]");
                Object newFieldValue;
                try {
                    if (isNumber(fieldType)) {
                        newFieldValue = toProperNumber(fieldType, jsonObject.getNumber(fieldName));
                    } else if (isBoolean(fieldType)) {
                        newFieldValue = jsonObject.getBoolean(fieldName);
                    } else if (isChar(fieldType)) {
                        newFieldValue = jsonObject.getString(fieldName).charAt(0);
                    } else if (isString(fieldType)) {
                        newFieldValue = jsonObject.getString(fieldName);
                    } else if (fieldType.isArray()) {
                        // slower?
                        Object arrayObject = Array.newInstance(fieldType.getComponentType(), 0);
                        newFieldValue = jsonToArrayUnsafe(jsonObject.getArray(fieldName), fieldType.getComponentType()).toArray((Object[]) arrayObject);
                    } else if (List.class.isAssignableFrom(fieldTypeSuperclass == null ? fieldType : fieldTypeSuperclass)) {
                        Class<?> listGenericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        newFieldValue = jsonToArrayUnsafe(jsonObject.getArray(fieldName), listGenericType);
                    } else if (Set.class.isAssignableFrom(fieldTypeSuperclass == null ? fieldType : fieldTypeSuperclass)) {
                        Class<?> listGenericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        newFieldValue = new HashSet<>(jsonToArrayUnsafe(jsonObject.getArray(fieldName), listGenericType));
                    } else if (Map.class.isAssignableFrom(fieldTypeSuperclass == null ? fieldType : fieldTypeSuperclass)) {
                        Type[] genericTypes = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                        newFieldValue = jsonToHashMapUnsafe(jsonObject.getObject(fieldName), (Class<?>) genericTypes[0], (Class<?>) genericTypes[1]);
                    } else if (fieldType.equals(Object.class)) {
                        newFieldValue = jsonObject.getRawObject(fieldName);
                    } else if (fieldType.equals(JsonObject.class)) {
                        newFieldValue = jsonObject.getObject(fieldName);
                    } else if (fieldType.equals(JsonArray.class)) {
                        newFieldValue = jsonObject.getArray(fieldName);
                    } else {
                        newFieldValue = jsonToObjectUnsafe(jsonObject.getObject(fieldName), fieldType);
                    }
                    field.set(obj, newFieldValue);

                }catch (NullPointerException e) {
                    if (!config.isAllowMissingProperty())
                        throw new CannotMapToObjectException("Field with '" + fieldName + "' of type '" + fieldType + "' " +
                                "does not have its corresponding json property. " +
                                "The trace starts from: " + (config.isDebugModeOn() ? trace : "Debug mode is not enabled"), e);
                }
                popTraceItem();
            }
        }catch (NoSuchMethodException ex){
            throw new CannotMapToObjectException("The class being mapped does not have a default constructor or it is an inner class", ex);
        }catch (InstantiationException |
                IllegalAccessException |
                IllegalArgumentException |
                InvocationTargetException e){
            throw new CannotMapToObjectException(e);
        }catch(ConversionException e){
            throw new CannotMapToObjectException("Error occured with a field with '" + fieldName + "' of type '" + fieldType + "' " +
                    "The trace starts from: " + (config.isDebugModeOn()? trace : "Debug mode is not enabled"), e);
        }
        // when obj is returned, the property which cannot be
        // processed becomes null / 0 (defaulted to unassigned value)
        return obj;
    }

    // Array
    /**
     * The array must be in the same type. (For now, nested arrays are not supported...)
     * Nested types with generic types are not supported (since class "Class" does not
     * hold generic type information in runtime)
     * @param type array element type
     */
    public <T> List<T> jsonToArray(String json, Class<T> type) throws CannotMapToObjectException {
        try {
            JsonArray jsonArray = new JsonArray(jsonParser.parseJsonArray(json));
            return jsonToArray(jsonArray, type);
        }catch (UnexpectedToken e){
            throw new CannotMapToObjectException(e);
        }
    }
    //Safe
    public <T> List<T> jsonToArray(JsonArray jsonArray, Class<T> type) throws CannotMapToObjectException{
        reset();
        if(!config.isSerializableAnnotationRequired())
            return jsonToArrayUnsafe(jsonArray, type);
        else if(config.isSerializableAnnotationRequired() && type.isAnnotationPresent(JsonSerializable.class))
            return jsonToArrayUnsafe(jsonArray, type);
        throw new CannotMapToObjectException("JsonSerializable annotation is required on type '" + type + "'");
    }
    /**
     * @param type array element type
     */
    private <T> List<T> jsonToArrayUnsafe(JsonArray jsonArray, Class<T> type) throws CannotMapToObjectException{
        ArrayList<T> list = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++){
            pushTraceItem(type + "[" + i + "]");
            Object newValue;
            if (isNumber(type)) {
                newValue = toProperNumber(type, jsonArray.getNumber(i));
            } else if (isBoolean(type)) {
                newValue = jsonArray.getBoolean(i);
            } else if (isChar(type)) {
                newValue = jsonArray.getString(i).charAt(0);
            } else if (isString(type)) {
                newValue = jsonArray.getString(i);
            } else if (type.isArray()) {
                Object arrayObject = Array.newInstance(type.getComponentType(), 0);
                newValue = jsonToArrayUnsafe(jsonArray.getArray(i), type.getComponentType()).toArray((Object[]) arrayObject);
            } else if (type.equals(Object.class)) {
                newValue = jsonArray.getRawObject(i);
            }else if(type.equals(JsonObject.class)) {
                newValue = jsonArray.getObject(i);
            }else if(type.equals(JsonArray.class)){
                newValue = jsonArray.getArray(i);
            } else {
                newValue = jsonToObjectUnsafe(jsonArray.getObject(i), type);
            }
            list.add((T)newValue);
            popTraceItem();
        }
        return list;
    }

    /**
     * @param key key (it must be string currently)
     * @param valueType json value
     * @param <T> value type
     */
    private <T> HashMap<String, T> jsonToHashMapUnsafe(JsonObject jsonObject, Class<?> key, Class<T> valueType) throws CannotMapToObjectException{
        if(!(key.equals(String.class))){
            throw new CannotMapToObjectException("Currently only String can be the key type of a Map");
        }
        HashMap<String, T> map = new HashMap<>();
        HashMap<String, Object> objectHashMap = jsonObject.get();
        for(Map.Entry<String, Object> entry : objectHashMap.entrySet()){
            pushTraceItem(valueType + "[" + entry.getKey() + "]");
            Object newValue;
            if (isNumber(valueType)) {
                newValue = toProperNumber(valueType, jsonObject.getNumber(entry.getKey()));
            } else if (isBoolean(valueType)) {
                newValue = jsonObject.getBoolean(entry.getKey());
            } else if (isChar(valueType)) {
                newValue = jsonObject.getString(entry.getKey()).charAt(0);
            } else if (isString(valueType)) {
                newValue = jsonObject.getString(entry.getKey());
            } else if (valueType.isArray()) {
                Object arrayObject = Array.newInstance(valueType.getComponentType(), 0);
                newValue = jsonToArrayUnsafe(jsonObject.getArray(entry.getKey()), valueType.getComponentType()).toArray((Object[]) arrayObject);
            } else if (valueType.equals(Object.class)){
                newValue = jsonObject.getRawObject(entry.getKey());
            } else {
                newValue = jsonToObjectUnsafe(jsonObject.getObject(entry.getKey()), valueType);
            }
            map.put(entry.getKey(), (T)newValue);
            popTraceItem();
        }
        return map;
    }


    // TO JSON
    // TODO: add the support to convert JsonObject, JsonArray to json as well.
    /**
     * Currently, HashMap is not usable
     */
    public String objectToJson(Object obj) throws CannotMapFromObjectException{
        reset();
        Class<?> type = obj.getClass();
        Class<?> superClass = obj.getClass().getSuperclass();
        if(obj instanceof String)
            return (String)obj;
        if(!config.isSerializableAnnotationRequired()
                || type.isArray()
                || Collection.class.isAssignableFrom(superClass == null? type : superClass))
            return objectToJsonUnsafe(obj);
        if(config.isSerializableAnnotationRequired() && type.isAnnotationPresent(JsonSerializable.class))
            return objectToJsonUnsafe(obj);
        throw new CannotMapFromObjectException("JsonSerializable annotation is required on type '" + type + "'");
    }
    /**
     * @return arrays as well.
     */
    private String objectToJsonUnsafe(Object obj) throws CannotMapFromObjectException{
        Class<?> type = obj.getClass();
        if(type.isArray()){
            return arrayObjectToJson((Object[]) obj);
        }else if(Collection.class.isAssignableFrom(type)){
            return arrayObjectToJson(((Collection<?>) obj).toArray());
        }
        StringBuilder jsonRaw = new StringBuilder("{");
        Class<?> fieldType = null;
        Class<?> fieldTypeSuperclass;
        String fieldName = "";
        try {
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                fieldName = field.getName();
                //fieldType = field.getType();

                if(field.get(obj) == null){
                    jsonRaw.append("\"").append(fieldName).append("\":").append("null").append(",");
                    continue;
                }

                fieldType = field.get(obj).getClass(); // we need underlying type (actual)
                fieldTypeSuperclass = fieldType.getSuperclass();

                pushTraceItem(type + "[" + fieldName + "]");
                if (isNumber(fieldType) || isBoolean(fieldType)) {
                    jsonRaw.append("\"").append(fieldName).append("\":").append(field.get(obj));
                } else if (isChar(fieldType) || isString(fieldType)) {
                    jsonRaw.append("\"").append(fieldName).append("\":\"").append(field.get(obj)).append("\"");
                } else if (fieldType.isArray()) {
                    Object[] objectArr = primitiveArrayToObjectArray(fieldType.getComponentType(), field.get(obj));
                    jsonRaw.append("\"").append(fieldName).append("\":").append(arrayObjectToJson(objectArr));
                } else if (Collection.class.isAssignableFrom(fieldTypeSuperclass == null? fieldType : fieldTypeSuperclass)) {
                    jsonRaw.append("\"").append(fieldName).append("\":").append(arrayObjectToJson(((List<?>) field.get(obj)).toArray()));
                } else {
                    jsonRaw.append("\"").append(fieldName).append("\":").append(objectToJsonUnsafe(field.get(obj)));
                }
                popTraceItem();

                jsonRaw.append(",");
            }
            if(jsonRaw.charAt(jsonRaw.length()-1) == ',')
                jsonRaw.deleteCharAt(jsonRaw.length()-1);
        }catch (Exception e){
            throw new CannotMapFromObjectException("Error occured at field '"+fieldName+"' with tyoe '"+fieldType+"'. Recorded trace: " + trace, e);
        }
        return jsonRaw.append("}").toString();
    }

    private String arrayObjectToJson(Object[] objects) throws CannotMapFromObjectException{
        StringBuilder jsonRaw = new StringBuilder("[");
        for(Object obj : objects){
            if(obj == null){
                jsonRaw.append("null").append(",");
                continue;
            }

            Class<?> type = obj.getClass();
            if (isNumber(type) || isBoolean(type)) {
                jsonRaw.append(obj);
            } else if (isChar(type) || isString(type)) {
                jsonRaw.append("\"").append(obj).append("\"");
            } else if (type.isArray()) {
                Object nestedArrayObject = Array.newInstance(type.getComponentType(), 0);
                jsonRaw.append(java.util.Arrays.toString((Object[]) nestedArrayObject));
            } else {
                jsonRaw.append(objectToJsonUnsafe(obj));
            }
            jsonRaw.append(",");
        }
        if(jsonRaw.charAt(jsonRaw.length()-1) == ',')
            jsonRaw.deleteCharAt(jsonRaw.length()-1);
        return jsonRaw.append("]").toString();
    }

    private void pushTraceItem(Object obj){
        if(config.isDebugModeOn())
            trace.add(obj);
    }

    private void popTraceItem(){
        if(config.isDebugModeOn() && trace.size() > 0)
            trace.remove(trace.size()-1);
    }

    /**
     * This method is called for all "safe" methods.
     * This should be called once you are done with an object.
     */
    public void reset(){
        trace.clear();
    }
}
