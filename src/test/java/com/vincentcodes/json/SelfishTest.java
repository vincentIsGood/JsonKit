package com.vincentcodes.json;

import com.vincentcodes.json.entity.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelfishTest {
    private final ObjectMapper mapper = new ObjectMapper(new ObjectMapperConfig.Builder()
            .setDebugModeOn(true).setAllowMissingProperty(true).build());

    @Test
    public void test_json_to_person_object() throws CannotMapToObjectException{
        JsonRpcRequestObject requestObject = mapper.jsonToObject("{\"id\":\"1\",\"method\":\"something.method\",\"params\":[]}", JsonRpcRequestObject.class);
    }
}

class JsonRpcRequestObject {
    public final String jsonrpc = "2.0"; // MUST BE "2.0"
    public String id;

    /**
     * Remote method name
     */
    public String method;

    /**
     * Can be named params or normal array
     */
    public Object params;

    public JsonRpcRequestObject(){}

    public JsonRpcRequestObject(String id, String method, Object params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }
}
