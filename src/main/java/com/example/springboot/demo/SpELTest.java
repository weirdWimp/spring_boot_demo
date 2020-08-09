package com.example.springboot.demo;

import com.example.springboot.demo.entity.Person;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpELTest {

    public static void main(String[] args) {
        evaluation();
    }

    private static void evaluation() {
        SpelExpressionParser parser = new SpelExpressionParser();

        // literal String
        System.out.println(parser.parseExpression("'hello world'").getValue(String.class));

        // method invoke
        System.out.println(parser.parseExpression("'hello world'.concat('!')").getValue(String.class));

        // nested property access
        System.out.println(parser.parseExpression("'hello world'.concat('!').bytes.length").getValue(Integer.class));

        // constructor
        System.out.println(parser.parseExpression("new String('Hello there')").getValue(String.class));

        // evaluate against a root object
        Person person = new Person("Tom");
        Expression nameExp = parser.parseExpression("name");
        Expression equalExp = parser.parseExpression("name == 'Tom'");
        System.out.println(nameExp.getValue(person, String.class));
        System.out.println(equalExp.getValue(person, Boolean.class));

    }

}
