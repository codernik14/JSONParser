package org.example;
import com.google.gson.Gson;
import java.util.HashMap;

import junit.framework.Assert;
//import org.junit.*;
public class Main {
    public static void main(String[] args) throws JsonParserException {
        String jsonString = "{\n" +
                "  \"string\": \"Hello, World!\",\n" +
                "  \"number\": 42,\n" +
                "  \"boolean\": true,\n" +
                "  \"nullValue\": null,\n" +
                "  \"array\": [\n" +
                "    1,\n" +
                "    \"two\",\n" +
                "    {\n" +
                "      \"nestedObject\": {\n" +
                "        \"key1\": \"value1\",\n" +
                "        \"key2\": \"value2\"\n" +
                "      },\n" +
                "      \"nestedArray\": [false, null]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"object\": {\n" +
                "    \"property1\": 3.14,\n" +
                "    \"property2\": [\"a\", \"b\", \"c\"],\n" +
                "    \"property3\": {\n" +
                "      \"nestedProperty\": \"nestedValue\"\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        JsonParser jsonParser = new JsonParser(jsonString);
        System.out.println(jsonParser.getJsonHashMap());
        Gson gsonObject = new Gson();
        HashMap gsonHashMap = gsonObject.fromJson(jsonString, HashMap.class);
        System.out.println(gsonHashMap);
//        Assert.assertEquals(gsonHashMap,jsonParser.getJsonHashMap());
    }
}