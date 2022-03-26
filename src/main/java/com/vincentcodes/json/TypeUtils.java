package com.vincentcodes.json;

import com.vincentcodes.json.parser.ConversionException;

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
        throw new ConversionException("Cannot convert ");
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
}
