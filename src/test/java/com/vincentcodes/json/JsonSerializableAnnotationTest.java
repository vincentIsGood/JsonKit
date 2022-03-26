package com.vincentcodes.json;

import com.vincentcodes.json.entity.ConvenienceStore;
import com.vincentcodes.json.entity.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSerializableAnnotationTest {
    @Test
    public void test_serializable_annotation_is_successful() throws CannotMapToObjectException, CannotMapFromObjectException{
        ObjectMapper mapper = new ObjectMapper(new ObjectMapperConfig.Builder()
                .setSerializableAnnotationRequired(true)
                .build());
        ConvenienceStore store = mapper.jsonToObject(SampleJsons.Contexualized.convenienceStoreJson, ConvenienceStore.class);
        mapper.objectToJson(store);
    }

    @Test
    public void test_absence_of_serializable_annotation_which_throws_error(){
        ObjectMapper mapper = new ObjectMapper(new ObjectMapperConfig.Builder()
                .setSerializableAnnotationRequired(true)
                .build());
        Exception ex = assertThrows(CannotMapToObjectException.class, ()-> mapper.jsonToObject(SampleJsons.Contexualized.personJson, Person.class));
        assertEquals("JsonSerializable annotation is required on type " +
                "'class com.vincentcodes.json.entity.Person'", ex.getMessage());
    }

    @Test
    public void test_normal_common_types() throws CannotMapFromObjectException{
        ObjectMapper mapper = new ObjectMapper(new ObjectMapperConfig.Builder()
                .setSerializableAnnotationRequired(true)
                .build());
        List<Person> people = new ArrayList<>();
        people.add(new Person("vincent", 19, "Hello World"));
        assertEquals("[{\"name\":\"vincent\",\"age\":19,\"desc\":\"Hello World\"}]", mapper.objectToJson(people));
    }
}
