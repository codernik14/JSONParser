package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import static org.example.HelperFunctionsAndVariables.*;

public class JsonParser {
    private final String jsonString;
    private int index;
    private final HashMap<String,Object> jsonHashMap;

    JsonParser(String jsonString) throws JsonParserException {
        this.jsonString = jsonString.trim();
        this.index = 0;
        this.jsonHashMap = parseJsonHashMap();
    }
    private HashMap<String,Object> parseJsonHashMap() throws JsonParserException {
        if (jsonString.charAt(index++) == openingBrace) {
            index = ignoreWhiteSpaces(jsonString,index);
            HashMap<String,Object> objectHashMap = new HashMap<>();
            try {
                while (jsonString.charAt(index)!=closingBrace) {
                    String key = generateKey();
                    Object value = fetchValue();
                    objectHashMap.put(key,value);
                    if(jsonString.charAt(index) == closingBrace){
                        index++;
                        return objectHashMap;
                    }
                    index = find(comma,jsonString,index);
                }
            }
            catch (IndexOutOfBoundsException ex){
                throw new JsonParserException("Index Out of Bounds");
            }
            return objectHashMap;
        }
        else{
            throw new JsonParserException("Unable to parse object HashMap", index);
        }
    }

    private String generateKey() throws JsonParserException {
        index = ignoreWhiteSpaces(jsonString,index);
        if(jsonString.charAt(index) == doubleQuote){
            String key = parseString();
            index = find(colon,jsonString,index);
            return key;
        }
        else{
            throw new JsonParserException("Unable to parse key",index);
        }
    }
    private Object fetchValue() throws JsonParserException {
        index = ignoreWhiteSpaces(jsonString,index);
        Object value;
        switch (jsonString.charAt(index)){
            case doubleQuote:
                value = parseString();
                break;
            case openingBrace:
                value = parseJsonHashMap();
                break;
            case openingSquare:
                value = parseArray();
                break;
            case trueFirstCharacter:
                value = parseBooleanTrue();
                break;
            case falseFirstCharacter:
                value = parseBooleanFalse();
                break;
            case nullFirstCharacter:
                value = parseNull();
                break;
            default:
                value = parseNumeric();
                break;
        }
        index = ignoreWhiteSpaces(jsonString,index);
        return value;
    }

    private ArrayList<Object> parseArray() throws JsonParserException {
        if (jsonString.charAt(index++) == openingSquare) {
            ArrayList<Object> arrayField = new ArrayList<>();
            try {
                while (jsonString.charAt(index)!=closingSquare) {
                   arrayField.add(fetchValue());
                   if(jsonString.charAt(index) == closingSquare) {
                       index++;
                       return arrayField;
                   }
                   index = find(comma,jsonString,index);
                }
            }
            catch (IndexOutOfBoundsException ex){
                throw new JsonParserException("Index Out of Bounds");
            }
            return arrayField;
        }
        else{
            throw new JsonParserException("Unable to parse Array Field", index);
        }
    }
    private String parseString() throws JsonParserException {
        String str = "";
        if (jsonString.charAt(index++) == doubleQuote) {
            try {
                while (jsonString.charAt(index) != doubleQuote || jsonString.charAt(index - 1) == backslash) {
                    str += jsonString.charAt(index++);
                }
                index++;
                return str;
            } catch (IndexOutOfBoundsException ex) {
                throw new JsonParserException("Index Out of Bounds");
            }
        }
        else{
            throw new JsonParserException("Unable to parse a String type", index);
        }
    }
    private Boolean parseBooleanTrue() throws JsonParserException {
        index = isValuePresentAtIndex(jsonString,"true", index);
        return true;
    }
    private Boolean parseBooleanFalse() throws JsonParserException {
        index = isValuePresentAtIndex(jsonString,"false", index);
        return false;
    }

    private Object parseNull() throws JsonParserException {
        index = isValuePresentAtIndex(jsonString,"null",index);
        return null;
    }
    private Object parseNumeric() throws JsonParserException {
        StringBuilder numericValueString = new StringBuilder();
        try{
            while(isPartOfNumericValueString(jsonString.charAt(index))){
                numericValueString.append(jsonString.charAt(index++));
            }
            return stringToNumeric(numericValueString.toString());
        }
        catch (IndexOutOfBoundsException ex){
            throw new JsonParserException("Index Out of Bounds");
        }

    }
    public HashMap<String, Object> getJsonHashMap() {
        return jsonHashMap;
    }

}
