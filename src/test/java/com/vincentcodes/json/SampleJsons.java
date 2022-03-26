package com.vincentcodes.json;

import java.util.Optional;

public class SampleJsons {
    static class Contexualized {
        //public static String personJson =
        //        "    {\n" +
        //        "        \"name\": \"vincent\",\n" +
        //        "        \"age\": 19,\n" +
        //        "        \"desc\": \"I cherish time, and I want to keep being young\"\n" +
        //        "    }";
        public static String personJson = "{\"name\": \"vincent\", \"age\": 19, \"desc\": \"I cherish time, and I want to keep being young\"}";

        public static String convenienceStoreJson = "{\n" +
                "    \"manager\": {\n" +
                "        \"name\": \"vincent\",\n" +
                "        \"age\": 19,\n" +
                "        \"desc\": \"I cherish time, and I want to keep being young\"\n" +
                "    },\n" +
                "    \"staff\": [\n" +
                "        {\n" +
                "            \"name\": \"shironeko\",\n" +
                "            \"age\": 18,\n" +
                "            \"desc\": \"Alternate name of mine\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"ayanokouji\",\n" +
                "            \"age\": 15,\n" +
                "            \"desc\": \"The main character in youjitsu\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"name\": \"VincentStore\"\n" +
                "}";

        public static String people = "{\n" +
                "    \"programmer\": {\n" +
                "        \"name\": \"shironeko\",\n" +
                "        \"age\": 18,\n" +
                "        \"desc\": \"A white cat\"\n" +
                "    },\n" +
                "    \"main_character\": {\n" +
                "        \"name\": \"ayanokouji\",\n" +
                "        \"age\": 15,\n" +
                "        \"desc\": \"the main character\"\n" +
                "    },\n" +
                "    \"senior_programmer\": {\n" +
                "        \"name\": \"vincent\",\n" +
                "        \"age\": 19,\n" +
                "        \"desc\": \"The programmer\"\n" +
                "    }\n" +
                "}";

    }

    static class TypeRelated{
        public static String optionalTypeJson = "{\n" +
                "    \"value\": \"string value\"\n" +
                "}";
        public static String nestedOptionalTypeJson = "{\n" +
                "    \"value\": {\n" +
                "        \"value\": \"nested value\"\n" +
                "    }\n" +
                "}";
        public static String arrayPeople = "[\n" +
                "    {\n" +
                "        \"name\": \"shironeko\",\n" +
                "        \"age\": 18,\n" +
                "        \"desc\": \"A white cat\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"ayanokouji\",\n" +
                "        \"age\": 15,\n" +
                "        \"desc\": \"the main character\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"vincent\",\n" +
                "        \"age\": 19,\n" +
                "        \"desc\": \"The programmer\"\n" +
                "    }\n" +
                "]";
    }

    public static String rawJsonWithObjects = "{\"widget\": {\n" +
            "    \"debug\": \"on\",\n" +
            "    \"window\": {\n" +
            "        \"title\": \"Sample Konfabulator Widget\",\n" +
            "        \"name\": \"main_window\",\n" +
            "        \"width\": 500,\n" +
            "        \"height\": 500\n" +
            "    },\n" +
            "    \"image\": {\n" +
            "        \"src\": \"Images/Sun.png\",\n" +
            "        \"name\": \"sun1\",\n" +
            "        \"hOffset\": 250,\n" +
            "        \"vOffset\": 250,\n" +
            "        \"alignment\": \"center\"\n" +
            "    },\n" +
            "    \"text\": {\n" +
            "        \"data\": \"Click Here\",\n" +
            "        \"size\": 36,\n" +
            "        \"style\": \"bold\",\n" +
            "        \"name\": \"text1\",\n" +
            "        \"hOffset\": 250,\n" +
            "        \"vOffset\": 100,\n" +
            "        \"alignment\": \"center\",\n" +
            "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
            "    }\n" +
            "}}";

    public static String rawJsonWithArray = "{\n" +
            "    \"sample_array\": {\n" +
            "        \"real_array\": [\n" +
            "            {\n" +
            "                \"desc\": \"some kind of array element\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"desc\": \"some kind of array element 2\"\n" +
            "            },\n" +
            "            [-1, 2, 3, -4],\n" +
            "            [-5, 6, 7, -8],\n" +
            "            1234,\n" +
            "            5678,\n" +
            "            false,\n" +
            "            true,\n" +
            "            null,\n" +
            "            null,\n" +
            "            \"string element\",\n" +
            "            \"string element 2\"\n" +
            "        ],\n" +
            "        \"description\": \"this is a sample array object\"\n" +
            "    }\n" +
            "}";

    public static String rawJsonStartingWithArray = "[\n" +
            "    {\n" +
            "        \"name\": \"vincent\",\n" +
            "        \"age\": 19,\n" +
            "        \"desc\": \"I cherish time, and I want to keep being young\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"someone\",\n" +
            "        \"age\": -1,\n" +
            "        \"desc\": \"A person class template\"\n" +
            "    }\n" +
            "]";
}
