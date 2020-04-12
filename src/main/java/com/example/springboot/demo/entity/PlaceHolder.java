package com.example.springboot.demo.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.StringJoiner;

@Component
public class PlaceHolder {

    /**
     * Spring boot add a random propertySource to Spring Environment. It can be used to
     * obtain a random int, long(can specify max value) or uuid value
     *
     * @see org.springframework.boot.env.RandomValuePropertySource
     */
    @Value("${random.uuid}")
    private String uuid;

    @Value("${external.value.p1}")
    private String p1;

    @DateTimeFormat(style = "S-")
    @Value("${external.value.date}")
    private LocalDate localDate;

    /**
     * It will try to resolve the property value and if it cannot be resolved,
     * the property name (for example ${external.value.p2}) will be injected as the value.
     * If default value is not specified, p2 will equals '${external.value.p2}', else default value will
     * be injected.
     */
    @Value("${external.value.p2:defaultP2}")
    private String p2;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlaceHolder.class.getSimpleName() + "[", "]")
                .add("uuid='" + uuid + "'")
                .add("p1='" + p1 + "'")
                .add("localDate=" + localDate)
                .add("p2='" + p2 + "'")
                .toString();
    }
}
