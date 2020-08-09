package com.example.springboot.demo;

import com.example.springboot.demo.entity.Person;
import com.example.springboot.demo.entity.RequestPerson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.javassist.runtime.Inner;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class LocalMainTest {

    public static class InnerClass {

        private String desc;

        static {
            System.out.println("Static init code block");
        }

        {
            System.out.println("Init code Block");
        }

        public InnerClass() {
            System.out.println("Default Constructor");
        }

        public InnerClass(String desc) {
            this.desc = desc;
            System.out.println("Constructor with parameter");
        }
    }

    public static class SubInnerClass extends InnerClass {
        private String subDesc;

        static {
            System.out.println("Sub Static init code block");
        }

        public SubInnerClass(String subDesc) {
            super(subDesc);
        }
    }


    public static void main(String[] args) throws JsonProcessingException, UnsupportedEncodingException {

        InnerClass love = new SubInnerClass("Love");

        final byte[] decode = Base64.getDecoder().decode("JVLx6CdmYkXqjyLb5VTUBoAP0k-Zh2gkWOMGbKc-kU0".getBytes("utf-8"));
        System.out.println("basic validation info: " + new String(decode, StandardCharsets.UTF_8));


        // final ObjectMapper objectMapper = new ObjectMapper();
        // String json = "{\"NAME\":\"Tom Cat\", \"AGE\":25, \"GENDER\":\"Male\", \"ADDRESS\":\"Ave 18\"}";
        // String emptyJson = "{}";
        //
        // final RequestPerson requestPerson = objectMapper.readValue(json, RequestPerson.class);
        // System.out.println("requestPerson = " + requestPerson.toString());
        //
        // final Person person = new Person("Tom Cat", 25, "Male", "Ave 18");
        // System.out.println("person = " + (requestPerson.equals(person)));
        //
        // final String s = objectMapper.writeValueAsString(requestPerson);
        // System.out.println("s = " + s);
        //
        //
        // String listJson = "[{\"NAME\":\"Tom Cat\", \"AGE\":25, \"GENDER\":\"Male\", \"ADDRESS\":\"Ave 18\"}, {}]";
        // String emptyList = "[]";
        // JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, RequestPerson.class);
        // List<RequestPerson> list = objectMapper.readValue(emptyList, javaType);
        // System.out.println("list = " + list.size());
        // System.out.println("list = " + list);
        //
        // distinctTest();
        System.out.println("TimeZone.getDefault() = " + TimeZone.getDefault());
        System.out.println("ZoneId.systemDefault() = " + ZoneId.systemDefault());
        System.out.println("LocalTime.now() = " + LocalDateTime.now());
        System.out.println("TimeZone.getTimeZone = " + TimeZone.getTimeZone("CST"));
        System.out.println("TimeZone.getTimeZone = " + TimeZone.getTimeZone("GMT-5:00"));

        byte[] bytes = "郭".getBytes(StandardCharsets.UTF_8);
        for (byte aByte : bytes) {
            System.out.println(Byte.toUnsignedInt(aByte));
        }

        final Date date = new Date();


        System.out.println("date = " + date);

        byte b = -1;
        int a = 0xBF11ff11;

        System.out.println(Integer.MIN_VALUE);
        System.out.println("" + a);


        System.out.println("Integer.toBinaryString(-1) = " + Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(1.25f)));

        char c1 = 'A';
        char c2 = '郭';


        char c3 = 0x7c;
        char c4 = '|';
        char c5 = '\u007c';

        if (c3 == c4) {
            System.out.println(Integer.valueOf(c4));
        }


        System.out.println(c3);
        System.out.println(Short.MAX_VALUE);

        String[] strings = new String[100];
        Arrays.fill(strings, "Hello");

        for (String string : strings) {
            System.out.println("string = " + string);
        }

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
