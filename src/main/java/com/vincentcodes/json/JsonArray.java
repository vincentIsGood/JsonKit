package com.vincentcodes.json;

import com.vincentcodes.json.parser.ConversionException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will be used in JsonObject and it resembles
 * JsonObject. The only difference is that JsonArray uses
 * index to access its content.
 *
 * @author vincent ko
 */
public class JsonArray{
    private ArrayList<Object> arrayList;

    public JsonArray(ArrayList<Object> arrayList){
        this.arrayList = arrayList;
    }

    public JsonObject getObject(int i) throws ConversionException, NullPointerException{
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, Object> result = (HashMap<String, Object>) arrayList.get(i);
            return new JsonObject(result);
        }catch (ClassCastException e){
            throw new ConversionException("At index '" +  i + "', is it really an object?", e);
        }
    }

    public JsonArray getArray(int i) throws ConversionException, NullPointerException{
        try {
            @SuppressWarnings("unchecked")
            ArrayList<Object> result = (ArrayList<Object>) arrayList.get(i);
            return new JsonArray(result);
        }catch (ClassCastException e){
            throw new ConversionException("At index '" +  i + "', is it really an array?", e);
        }
    }

    public Object getRawObject(int i) throws ConversionException, NullPointerException{
        return arrayList.get(i);
    }

    public String getString(int i) throws ConversionException, NullPointerException{
        try {
            return (String) arrayList.get(i);
        }catch (ClassCastException e){
            throw new ConversionException("At index '" +  i + "', is it really a string?", e);
        }
    }

    public double getNumber(int i) throws ConversionException, NullPointerException{
        try {
            return (Double) arrayList.get(i);
        }catch (ClassCastException e){
            throw new ConversionException("At index '" +  i + "', is it really a number?", e);
        }
    }

    public boolean getBoolean(int i) throws ConversionException, NullPointerException{
        try {
            return (Boolean) arrayList.get(i);
        }catch (ClassCastException e){
            throw new ConversionException("At index '" +  i + "', is it really a boolean?", e);
        }
    }

    public ArrayList<Object> get(){
        return arrayList;
    }

    public int size(){
        return arrayList.size();
    }

    public String toString(){
        return arrayList.toString();
    }
}
