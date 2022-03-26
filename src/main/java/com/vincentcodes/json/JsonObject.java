package com.vincentcodes.json;

import com.vincentcodes.json.parser.ConversionException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * If you wanna enhance your ability to traverse a JSON object,
 * consider using this class.
 *
 * @author vincent ko
 */
public class JsonObject{
    private HashMap<String, Object> object;

    public JsonObject(HashMap<String, Object> object){
        this.object = object;
    }

    public JsonObject getObject(String property) throws ConversionException{
        Object valueFromProp = null;
        try {
            valueFromProp = object.get(property);
            @SuppressWarnings("unchecked")
            HashMap<String, Object> result = (HashMap<String, Object>) valueFromProp;
            return new JsonObject(result);
        }catch (ClassCastException e){
            throw new ConversionException("Property '"+ property +"' has value '"+ valueFromProp +"' that is not an object ", e);
        }
    }

    public JsonArray getArray(String property) throws ConversionException, NullPointerException{
        Object valueFromProp = null;
        try {
            valueFromProp = object.get(property);
            @SuppressWarnings("unchecked")
            ArrayList<Object> result = (ArrayList<Object>) valueFromProp;
            return new JsonArray(result);
        }catch (ClassCastException e){
            throw new ConversionException("Property '"+ property +"' has value '"+ valueFromProp +"' that is not an array ", e);
        }
    }

    public Object getRawObject(String property) throws ConversionException, NullPointerException{
        return object.get(property);
    }

    public String getString(String property) throws ConversionException, NullPointerException{
        Object valueFromProp = null;
        try {
            valueFromProp = object.get(property);
            return (String) valueFromProp;
        }catch (ClassCastException e){
            throw new ConversionException("Property '"+ property +"' has value '"+ valueFromProp +"' that is not a string ", e);
        }
    }

    public double getNumber(String property) throws ConversionException, NullPointerException{
        Object valueFromProp = null;
        try {
            valueFromProp = object.get(property);
            return (Double) valueFromProp;
        }catch (ClassCastException e){
            throw new ConversionException("Property '"+ property +"' has value '"+ valueFromProp +"' that is not a number ", e);
        }
    }

    public boolean getBoolean(String property) throws ConversionException, NullPointerException{
        Object valueFromProp = null;
        try {
            valueFromProp = object.get(property);
            return (Boolean) object.get(property);
        }catch (ClassCastException e){
            throw new ConversionException("Property '"+ property +"' has value '"+ valueFromProp +"' that is not a boolean ", e);
        }
    }

    public HashMap<String, Object> get(){
        return object;
    }
    public String toString(){
        return object.toString();
    }
}
