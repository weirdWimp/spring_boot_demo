package com.example.springboot.demo.controller.property;

import com.example.springboot.demo.config.CommonConfiguration;
import com.example.springboot.demo.entity.ComponentScanClass;
import com.example.springboot.demo.entity.PlaceHolder;
import com.example.springboot.demo.entity.PropertyOverrideClass;
import com.example.springboot.demo.properties.DisplayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private DisplayProperties displayProperties;

    @Autowired
    private PropertyOverrideClass overrideClass;

    @Autowired
    private PlaceHolder placeHolder;

    /**
     * Custom ComponentScan filter add this Class as a candidate component even though no stereotype annotation
     * like @Component added on this Class.
     *
     * @see CommonConfiguration
     */
    @Autowired
    private ComponentScanClass componentScanClass;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void service() {
        System.out.println("displayProperties = " + displayProperties);
        System.out.println("PropertyOverrideClass = " + overrideClass);
        System.out.println("PlaceHolder = " + placeHolder);
    }
}
