package org.example;

public class JsonParserException extends Exception{
    JsonParserException(String message){
        System.out.println("JsonParserException: Invalid Json String : " + message);
    }

    JsonParserException(String message, int index){
        System.out.println("JsonParserException: Error: " + message + " at index " + index);
    }
}
