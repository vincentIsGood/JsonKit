package com.vincentcodes.json;

import com.vincentcodes.json.entity.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test_json_to_person_object() throws CannotMapToObjectException{
        Person person = mapper.jsonToObject(SampleJsons.Contexualized.personJson, Person.class);
        assertEquals("Person{name: vincent, age: 19, desc: I cherish time, and I want to keep being young}", person.toString());
    }

    @Test
    public void test_json_to_person_array_object() throws CannotMapToObjectException{
        List<Person> persons = mapper.jsonToArray(SampleJsons.rawJsonStartingWithArray, Person.class);
        assertEquals("[Person{name: vincent, age: 19, desc: I cherish time, and I want to keep being young}, Person{name: someone, age: -1, desc: A person class template}]", persons.toString());
    }

    @Test
    public void test_json_to_convenience_store_object() throws CannotMapToObjectException{
        ConvenienceStore store = mapper.jsonToObject(SampleJsons.Contexualized.convenienceStoreJson, ConvenienceStore.class);
        assertEquals("vincent", store.manager.name);
        assertEquals("ayanokouji", store.staff[1].name);
        assertEquals("VincentStore", store.name);
    }

    @Test
    public void test_json_to_new_convenience_store_object() throws CannotMapToObjectException{
        NewConvenienceStore store = mapper.jsonToObject(SampleJsons.Contexualized.convenienceStoreJson, NewConvenienceStore.class);
        assertEquals("vincent", store.manager.name);
        assertEquals("ayanokouji", store.staff.get(1).name);
        assertEquals("VincentStore", store.name);
    }

    @Test
    public void test_array_json_to_jsonobject() throws CannotMapToObjectException{
        List<JsonObject> persons = mapper.jsonToArray(SampleJsons.rawJsonStartingWithArray, JsonObject.class);
        assertEquals("vincent", persons.get(0).getString("name"));
        assertEquals(-1, persons.get(1).getNumber("age"));
    }

    @Test
    public void test_json_to_convenience_raw() throws CannotMapToObjectException{
        ConvenienceStoreRaw storeRaw = mapper.jsonToObject(SampleJsons.Contexualized.convenienceStoreJson, ConvenienceStoreRaw.class);
        assertEquals("vincent", storeRaw.manager.getString("name"));
        assertEquals("ayanokouji", storeRaw.staff.getObject(1).getString("name"));
        assertEquals("VincentStore", storeRaw.name);
    }

    @Test
    public void test_json_to_hashmap_person() throws CannotMapToObjectException{
        // Not done yet
        NewConvenienceStore store = mapper.jsonToObject(SampleJsons.Contexualized.convenienceStoreJson, NewConvenienceStore.class);
    }

    //@Test
    //public void test_on_optional_type() throws CannotMapToObjectException{
    //    ObjectMapper mapper = new ObjectMapper();
    //    Optional<String> store = mapper.jsonToObject(SampleJsons.TypeRelated.optionalTypeJson, Optional.class);
    //}

    @Test
    public void test_object_to_json_convenience_store_object() throws CannotMapToObjectException, CannotMapFromObjectException{
        ConvenienceStore store = mapper.jsonToObject(SampleJsons.Contexualized.convenienceStoreJson, ConvenienceStore.class);
        mapper.reset();
        assertEquals("{\"manager\":{\"name\":\"vincent\",\"age\":19,\"desc\":\"I cherish time, and I want to keep being young\"}," +
                "\"staff\":[{\"name\":\"shironeko\",\"age\":18,\"desc\":\"Alternate name of mine\"},{\"name\":\"ayanokouji\",\"age\":15,\"desc\":\"The main character in youjitsu\"}]," +
                "\"name\":\"VincentStore\"}", mapper.objectToJson(store));
    }

    @Test
    public void test_json_to_person_object_using_type_container() throws CannotMapToObjectException{
        Person person = mapper.jsonToObject(SampleJsons.Contexualized.personJson, new TypeContainer<Person>(){});
        assertEquals("vincent", person.name);
    }

    @Test
    public void test_json_to_person_array_object_using_type_container() throws CannotMapToObjectException{
        List<Person> people = mapper.jsonToObject(SampleJsons.rawJsonStartingWithArray, new TypeContainer<List<Person>>(){});
        assertEquals("[Person{name: vincent, age: 19, desc: I cherish time, and I want to keep being young}, " +
                "Person{name: someone, age: -1, desc: A person class template}]", people.toString());
    }

    @Test
    public void test_json_to_people_hash_map_using_type_container() throws CannotMapToObjectException{
        HashMap<String, Person> people = mapper.jsonToObject(SampleJsons.Contexualized.people, new TypeContainer<HashMap<String, Person>>(){});
        assertEquals(people.size(), 3);
        assertEquals(people.get("programmer").name, "shironeko");
    }

    @Test
    public void test_object_to_string_json() throws CannotMapFromObjectException{
        assertEquals("{\"name\":\"vincent\",\"age\":19,\"desc\":\"Hello\"}", mapper.objectToJson(new Person("vincent", 19, "Hello")));
    }

    @Test
    public void test_object_array_to_string_json() throws CannotMapFromObjectException{
        assertEquals("[{\"name\":\"vincent\",\"age\":19,\"desc\":\"Hello\"}]", mapper.objectToJson(new Person[]{new Person("vincent", 19, "Hello")}));
    }

    @Test
    public void test_object_list_to_string_json() throws CannotMapFromObjectException{
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("vincent", 19, "Hello"));
        assertEquals("[{\"name\":\"vincent\",\"age\":19,\"desc\":\"Hello\"}]", mapper.objectToJson(list));
    }

    @Test
    public void test_object_in_object_to_string_json() throws CannotMapFromObjectException {
        ImaginaryObject obj = new ImaginaryObject();
        obj.test = new Person[]{
            new Person("vincent", 19, "hello"),
            new Person("neko", 19, "cat")
        };
        assertEquals("{\"test\":[{\"name\":\"vincent\",\"age\":19,\"desc\":\"hello\"},{\"name\":\"neko\",\"age\":19,\"desc\":\"cat\"}]}", mapper.objectToJson(obj));
    }
    @Test
    public void test_object_with_null_val_to_string_json() throws CannotMapFromObjectException {
        ImaginaryObject obj = new ImaginaryObject();
        obj.test = new Person[2];
        assertEquals("{\"test\":[null,null]}", mapper.objectToJson(obj));
    }

    @Test
    public void test_object_to_empty_string_json() throws CannotMapFromObjectException{
        ArrayList<Person> list = new ArrayList<>();
        assertEquals("[]", mapper.objectToJson(list));
    }
}
