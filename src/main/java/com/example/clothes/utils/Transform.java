package com.example.clothes.utils;

import com.example.clothes.dto.OtherAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Transform {
    public String objectToString (OtherAttribute ortherAttribute) {
        return ortherAttribute.toString();
    }
    public OtherAttribute stringToObject(String attribute) {
        List<String> arrayFromString = Arrays.asList(attribute.split("[{};]"));
        OtherAttribute ortherAttribute = new OtherAttribute();
        ortherAttribute.setName(arrayFromString.get(0));
        ortherAttribute.setValue(arrayFromString.get(1));
        return ortherAttribute;
    }
    public String arrayToString (List<OtherAttribute> ortherAttributeList) {
        List<String> arrayString = ortherAttributeList.stream().map(OtherAttribute::toString).collect(Collectors.toList());
        return String.join(";" , arrayString);
    }
    public List<OtherAttribute> stringToArray (String attributes) {
        List<String> arrOfStr = Arrays.asList(attributes.split(";"));
        return arrOfStr.stream().map(this::stringToObject).collect(Collectors.toList());
    }
}
