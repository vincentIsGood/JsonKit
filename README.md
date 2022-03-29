# Json Kit
JsonKit offers a json parser and a simple object mapper. Since Json Kit is a small project created by one person. You may not expect it to work as stable as Jackson or Gson. 

## Basic Usage
Examples are located inside `src/test/java/com/vincentcodes/json`. Main feature of Json Kit is the flexibility of accessing the Json Object itself and the object mapper.

### Simple Traversing
To traverse a Json Object, we can do the following:
```json
// Json to be parsed:
{
    "sample_array": {
        "real_array": [
            {
                "desc": "some kind of array element"
            },
            {
                "desc": "some kind of array element 2"
            },
            [-1, 2, 3, -4],
            [-5, 6, 7, -8],
            1234,
            5678,
            false,
            true,
            null,
            null,
            "string element",
            "string element 2"
        ],
        "description": "this is a sample array object"
    }
}
```

```java
public String rawJsonWithArray = "...";

JsonParser parser = new JsonParser();
JsonObject json = new JsonObject(parser.parseJson(rawJsonWithArray));

JsonObject sample_array = json.getObject("sample_array");
JsonArray real_array = sample_array.getArray("real_array");

/*
This should give us, in JS terms, `rawObj.sample_array.real_array[1]`
ie. We got:
{
    "desc": "some kind of array element 2"
}
*/
JsonObject objectIndex1 = real_array.getObject(1);
String desc = objectIndex = objectIndex1.getString("desc");
```

### Object Mapping
With object mapping, it is possible to map Json to Java object and the way round.

Let's say we have the following json:
```json
// rawJson
{
    "programmer": {
        "name": "shironeko",
        "age": 18,
        "desc": "A white cat"
    },
    "main_character": {
        "name": "ayanokouji",
        "age": 15,
        "desc": "the main character"
    },
    "senior_programmer": {
        "name": "vincent",
        "age": 19,
        "desc": "The programmer"
    }
}

// personJson
{
    "name": "vincent",
    "age": 19,
    "desc": "I cherish time, and I want to keep being young"
}
```

To use object mapper:
```java
class Person {
    public String name;
    public int age;
    public String desc;

    public Person(){}

    public Person(String name, int age, String desc) {
        this.name = name;
        this.age = age;
        this.desc = desc;
    }

    public String toString(){
        return String.format("Person{name: %s, age: %d, desc: %s}", name, age, desc);
    }
}
```

Json to Object:
```java
public String rawJson = "...";
public String personJson = "...";

// Simple Conversion
Person person = mapper.jsonToObject(personJson, Person.class);
assertEquals("vincent", person.name);
assertEquals(19, person.age);

// To JsonObject (JsonArray is also supported)
JsonObject person = mapper.jsonToObject(personJson, JsonObject.class);
assertEquals("vincent", person.getString("name"));
assertEquals(19, person.getNumber("age"));

// With Generic Type (You may not enter nested generic types. eg. HashMap<String, List<Person>>)
HashMap<String, Person> people = mapper.jsonToObject(rawJson, new TypeContainer<HashMap<String, Person>>(){});
assertEquals(people.size(), 3);
assertEquals(people.get("programmer").name, "shironeko");
```

Object to Json:
```java
// Simple Java Object
Person person = new Person("vincent", 19, "Hello");
assertEquals("{\"name\":\"vincent\",\"age\":19,\"desc\":\"Hello\"}", mapper.objectToJson(person));

// Using List
ArrayList<Person> list = new ArrayList<>();
list.add(new Person("vincent", 19, "Hello"));
assertEquals("[{\"name\":\"vincent\",\"age\":19,\"desc\":\"Hello\"}]", mapper.objectToJson(list));

// TODO: HashMap to Json is currently not possible
```