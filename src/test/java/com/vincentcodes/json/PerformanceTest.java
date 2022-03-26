package com.vincentcodes.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentcodes.json.entity.ShirabeJishoRoot;
import com.vincentcodes.json.parser.JsonParser;
import com.vincentcodes.json.parser.UnexpectedToken;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.HashMap;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerformanceTest {
    private String rawJson;

    @BeforeAll
    public void setup_data(){
        //File file = new File("G:\\Downloads\\ShirabeJisho\\database\\ブックマーク.shirabe");
        File file = new File("D:\\Downloads_D\\zPrograms\\Java\\0_OwnProjects\\0_SmallPrograms\\CentralServer\\docker_compose_build\\jishoapi\\メモ.shirabe");
        byte[] bytes = new byte[(int)file.length()];
        try(FileInputStream fis = new FileInputStream(file)){
            fis.read(bytes);
        }catch (IOException e){
            e.printStackTrace();
        }

        rawJson = new String(bytes);
    }

    @Test
    @Order(1)
    public void jsonkit_performance_test() throws UnexpectedToken {
        long initTime = System.currentTimeMillis();
        JsonParser parser = new JsonParser();
        HashMap<String, Object> jsonMapping = parser.parseJson(rawJson);
        JsonObject root = new JsonObject(jsonMapping);

        System.out.println("Time taken for JsonKit to parse the whole file: " + (System.currentTimeMillis() - initTime) + "ms");
    }

    @Test
    @Order(2)
    public void jackson_performance_test() throws IOException{
        long initTime = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        JsonNode node = mapper.readTree(rawJson);

        System.out.println("Time taken for Jackson to parse the whole file: " + (System.currentTimeMillis() - initTime) + "ms");
    }

    @Test
    @Order(3)
    public void jsonkit_objectmapping_performance_test() throws CannotMapToObjectException{
        long initTime = System.currentTimeMillis();
        ObjectMapperConfig config = new ObjectMapperConfig.Builder().setAllowMissingProperty(true).setDebugModeOn(true).build();
        com.vincentcodes.json.ObjectMapper mapper = new com.vincentcodes.json.ObjectMapper(config);
        mapper.jsonToObject(rawJson, ShirabeJishoRoot.class);

        System.out.println("Time taken for JsonKit to map the whole file: " + (System.currentTimeMillis() - initTime) + "ms");
    }

    // Since some fields are annotated with @JsonIgnore
    // I expect the number here is larger
    @Test
    @Order(4)
    public void jackson_objectmapping_performance_test() throws IOException{
        long initTime = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        mapper.readValue(rawJson, ShirabeJishoRoot.class);

        System.out.println("Time taken for Jackson to map the whole file: " + (System.currentTimeMillis() - initTime) + "ms");
    }

    @Test
    public void can_jackson_handle_unknown_object() throws IOException{
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        //System.out.println(mapper.writeValueAsString(new ByteArrayOutputStream()));
        //System.out.println(mapper.writeValueAsString(Optional.of("asd")));
        System.out.println(mapper.writeValueAsString("asd123"));
    }
}