package com.vincentcodes.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentcodes.json.entity.GenericContainer;
import com.vincentcodes.json.entity.HiddenGenericContainer;
import com.vincentcodes.json.parser.JsonParser;
import com.vincentcodes.json.parser.UnexpectedToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonTest {
    @Test
    public void testWithArrays() throws UnexpectedToken {
        JsonParser parser = new JsonParser();
        JsonObject json = new JsonObject(parser.parseJson(SampleJsons.rawJsonWithArray));

        assertNotNull(json.getObject("sample_array"));
        assertNotNull(json.getObject("sample_array").getArray("real_array"));
        assertNotNull(json.getObject("sample_array").getArray("real_array").getObject(1));
    }

    @Test
    public void testWithNestedObjects() throws UnexpectedToken{
        JsonParser parser = new JsonParser();
        JsonObject json = new JsonObject(parser.parseJson(SampleJsons.rawJsonWithObjects));

        assertNotNull(json.getObject("widget"));
        assertNotNull(json.getObject("widget").getObject("text"));
        assertEquals(json.getObject("widget").getObject("text").getString("name"), "text1");
    }

    @Test
    public void testWithJsonStartingWithArray() throws Exception{
        JsonParser parser = new JsonParser();
        JsonArray json = new JsonArray(parser.parseJsonArray(SampleJsons.rawJsonStartingWithArray));

        assertNotNull(json.getObject(0));
        assertNotNull(json.getObject(1));
        assertEquals("vincent", json.getObject(0).getString("name"));
        assertEquals("someone", json.getObject(1).getString("name"));
    }
}