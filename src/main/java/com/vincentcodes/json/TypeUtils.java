package com.vincentcodes.json;

import com.vincentcodes.json.parser.ConversionException;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeUtils {
    public static boolean isNumber(Class<?> clazz){
        if(clazz.equals(int.class) || clazz.equals(Integer.class)){
            return true;
        }else if(clazz.equals(double.class) || clazz.equals(Double.class)){
            return true;
        }else if(clazz.equals(byte.class) || clazz.equals(Byte.class)){
            return true;
        }else if(clazz.equals(short.class) || clazz.equals(Short.class)){
            return true;
        }else if(clazz.equals(long.class) || clazz.equals(Long.class)){
            return true;
        }else return clazz.equals(float.class) || clazz.equals(Float.class);
    }
    public static Object toProperNumber(Class<?> clazz, double value){
        if(clazz.equals(int.class) || clazz.equals(Integer.class)){
            return (int)value;
        }else if(clazz.equals(double.class) || clazz.equals(Double.class)){
            return value;
        }else if(clazz.equals(byte.class) || clazz.equals(Byte.class)){
            return (byte)value;
        }else if(clazz.equals(short.class) || clazz.equals(Short.class)){
            return (short)value;
        }else if(clazz.equals(long.class) || clazz.equals(Long.class)){
            return (long)value;
        }else if(clazz.equals(float.class) || clazz.equals(Float.class)) {
            return (float)value;
        }
        throw new ConversionException("Cannot convert '" + value + "' to object");
    }
    public static boolean isBoolean(Class<?> clazz){
        return clazz.equals(boolean.class) || clazz.equals(Boolean.class);
    }
    public static boolean isChar(Class<?> clazz){
        return clazz.equals(char.class) || clazz.equals(Character.class);
    }
    public static boolean isString(Class<?> clazz){
        return clazz.equals(String.class);
    }

    public static Object[] primitiveArrayToObjectArray(Class<?> elementType, Object array){
        if(elementType.equals(byte.class) || elementType.equals(short.class) || elementType.equals(int.class))
            return Arrays.stream((int[]) array).boxed().toArray();
        else if(elementType.equals(long.class))
            return Arrays.stream((long[]) array).boxed().toArray();
        else if(elementType.equals(double.class) || elementType.equals(float.class))
            return Arrays.stream((double[]) array).boxed().toArray();
        else if(elementType.equals(boolean.class)){
            boolean[] converted = (boolean[]) array;
            ArrayList<Boolean> fools = new ArrayList<>();
            for(boolean bool : converted)
                fools.add(bool);
            return fools.toArray();
        }else if(elementType.equals(char.class)){
            char[] converted = (char[]) array;
            ArrayList<Character> fools = new ArrayList<>();
            for(char c : converted)
                fools.add(c);
            return fools.toArray();
        }
        return (Object[]) array;
    }
}
