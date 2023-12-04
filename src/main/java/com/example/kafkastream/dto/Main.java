package com.example.kafkastream.dto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String s = "Hello i'm java developer";

        //System.out.println(dupCharMap(s));
    }

/*    private static Map<Character, Long> dupCharMap(String str) {
     *//*   return  Arrays.stream(str.split("")).collect(Collectors.groupingBy(Object::toString, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()!=1)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));*//*
          *//*str.chars().mapToObj(it->(char)it)
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()!=1)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));*//*

     *//*   return  Arrays.stream(str.spli   t("")).map().collect(Collectors.groupingBy(Object::toString, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()!=1)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));*//*
    }*/
}
