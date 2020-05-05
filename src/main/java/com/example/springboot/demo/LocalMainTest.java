package com.example.springboot.demo;

import com.example.springboot.demo.entity.Person;
import com.example.springboot.demo.entity.RequestPerson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class LocalMainTest {

    public static void main(String[] args) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"NAME\":\"Tom Cat\", \"AGE\":25, \"GENDER\":\"Male\", \"ADDRESS\":\"Ave 18\"}";
        String emptyJson = "{}";

        final RequestPerson requestPerson = objectMapper.readValue(json, RequestPerson.class);
        System.out.println("requestPerson = " + requestPerson.toString());

        final Person person = new Person("Tom Cat", 25, "Male", "Ave 18");
        System.out.println("person = " + (requestPerson.equals(person)));

        final String s = objectMapper.writeValueAsString(requestPerson);
        System.out.println("s = " + s);


        String listJson = "[{\"NAME\":\"Tom Cat\", \"AGE\":25, \"GENDER\":\"Male\", \"ADDRESS\":\"Ave 18\"}, {}]";
        String emptyList = "[]";
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, RequestPerson.class);
        List<RequestPerson> list = objectMapper.readValue(emptyList, javaType);
        System.out.println("list = " + list.size());
        System.out.println("list = " + list);

        distinctTest();

    }


    public static void distinctTest() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Jason", 25, "Male", "Ave 18"));
        people.add(new Person("Tom", 25, "Male", "Ave 17"));
        people.add(new Person("Jerry", 25, "Male", "Ave 15"));
        people.add(new Person("Marilyn Monroe", 25, "Female", "Ave 16"));

        List<Person> newPerson = people.stream().collect(
                collectingAndThen(groupingBy(Person::getAge, maxBy(Comparator.comparing(person -> person.getName().length()))),
                        map -> map.values().stream().filter(Optional::isPresent).map(Optional::get).collect(toList())));

        System.out.println("\n\n############## Distinct Test");
        for (Person person : newPerson) {
            System.out.println("person = " + person);
        }
    }

    public static void add(BiConsumer<List<String>, String> biConsumer) {


        List<? extends CharSequence> list1;
        List<String> list2;

        List<? super String> list3 = new ArrayList<CharSequence>();
        List<CharSequence> list4;

    }

}
