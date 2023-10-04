package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class HelperFunctionsAndVariables{
    private final static char space = ' ';
    private final static char tab = '\t';
    private final static char endLine = '\n';
    private static final List<Character> whiteSpaceCharacters = new ArrayList<>(Arrays.asList(space,tab,endLine));
    public final static char doubleQuote = '"';
    public final static char openingBrace = '{';
    public final static char closingBrace = '}';
    public final static char comma = ',';
    public final static char colon = ':';
    public final static char backslash = '\\';
    public final static char openingSquare ='[';
    public final static char closingSquare = ']';
    public final static char trueFirstCharacter = 't';
    public final static char falseFirstCharacter = 'f';
    public final static char nullFirstCharacter = 'n';
    public final static String decimal = ".";
    public final static List<Character> terminatingCharacters = new ArrayList<Character>(Arrays.asList(comma,closingBrace,closingSquare));
    private HelperFunctionsAndVariables(){}
    public static int ignoreWhiteSpaces(String jsonString, int index) {
        while (index < jsonString.length() && whiteSpaceCharacters.contains(jsonString.charAt(index))){
            index++;
        }
        return index;
    }

    public static int find(char separationCharacter, String jsonString, int index) throws JsonParserException {
        index = ignoreWhiteSpaces(jsonString,index);
        if(jsonString.charAt(index) == separationCharacter){
            index++;
        }
        else{
            switch (separationCharacter){
                case colon:
                    throw new JsonParserException("Unable to find colon after a key", index);
                case comma:
                    throw new JsonParserException("Unable to find comma or end character after a value of complex data type", index);
            }
        }
        return index;
    }

    public static int isValuePresentAtIndex(String jsonString, String valueString, int index) throws JsonParserException {
        if (index + valueString.length() > jsonString.length()) {
            throw new JsonParserException("Unable to parse '" + valueString + "' value", index);
        }
        String substringAtIndex = jsonString.substring(index, index + valueString.length());
        if (substringAtIndex.equals(valueString)) {
            index += valueString.length();
            return index;
        }
        else {
            throw new JsonParserException("Unable to parse '" + valueString + "' value", index);
        }
    }

    public static Object stringToNumeric(String stringNumber) throws JsonParserException {
        Object numericValue;
        try{
            if(stringNumber.contains(decimal)){
                numericValue = Double.parseDouble(stringNumber);
            }
            else{
                numericValue = Long.parseLong(stringNumber);
            }
            return numericValue;
        }
        catch (NumberFormatException ex){
            throw new JsonParserException("Unable to parse String into numeric value");
        }
    }

    public static boolean isPartOfNumericValueString(char digit){
        return !(terminatingCharacters.contains(digit) || whiteSpaceCharacters.contains(digit));
    }
}

