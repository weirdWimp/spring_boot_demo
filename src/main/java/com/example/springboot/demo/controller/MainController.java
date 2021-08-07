package com.example.springboot.demo.controller;

import com.example.springboot.demo.entity.Person;
import com.example.springboot.demo.service.impl.DataDealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;


/**
 * @RestController annotation from Spring MVC is composed of @Controller and @ResponseBody.
 */
@Controller
@RequestMapping("/home")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @RequestMapping
    @ResponseBody
    public String home() throws ParseException {
        System.out.println("applicationContext = " + applicationContext.getClass().getName());
        System.out.println("environment.getClass().getName() = " + environment.getClass().getName());

        MutablePropertySources propertySources = ((StandardServletEnvironment) environment).getPropertySources();
        propertySources.forEach(propertySource -> {
            System.out.println("propertySource = [" + propertySource.getClass().getName() + "]" + propertySource.getName());
        });

        Map<String, PropertySourcesPlaceholderConfigurer> beansOfType = applicationContext.getBeansOfType(PropertySourcesPlaceholderConfigurer.class);
        for (Map.Entry<String, PropertySourcesPlaceholderConfigurer> entry : beansOfType.entrySet()) {
            System.out.println("entry.getKey() = " + entry.getKey());
        }

        validatorTest();

        return "OK";
    }


    private void validatorTest() {
        // Person person = new Person();
        //
        // DataBinder dataBinder = new DataBinder(person);
        // dataBinder.setValidator(localValidatorFactoryBean);
        //
        // dataBinder.validate();
        // for (ObjectError objectError : dataBinder.getBindingResult().getAllErrors()) {
        //     System.out.println("objectError = " + objectError.getCode());
        // }
    }

    /**
     * String -> LocalDate 会存在两个 Converter， 一个用于标准转换，另一个用于与注解关联的转换(只有目标字段上使用的相关的注解才会使用)
     * 标准转换器默认使用的 {@code FormatStyle.SHORT} 格式
     * 与注解关联的转换器默认使用的 {@code FormatStyle.MEDIUM} 格式
     * <p>
     * DateTimeFormatter的locale是通过调用 {@link LocaleContextHolder#getLocale()} 获取的,
     * 当前为 {@link java.util.Locale#SIMPLIFIED_CHINESE}
     *
     * @see java.time.format.DateTimeFormatter
     * @see DateTimeFormatterRegistrar#registerFormatters(FormatterRegistry)
     * @see java.time.format.FormatStyle#SHORT
     * @see LocaleContextHolder#getLocale()
     * @see FormattingConversionService
     * @see org.springframework.format.support.DefaultFormattingConversionService
     * @see org.springframework.core.convert.support.ObjectToObjectConverter
     * <p>
     * <p>
     * Spring Boot 会使用 {@link ApplicationConversionService} 来设置 ConfigurableEnvironment；
     * {@link org.springframework.boot.SpringApplication#configureEnvironment(ConfigurableEnvironment, String[])}
     * <p>
     * 但是容器中注入的确是 {@link org.springframework.boot.autoconfigure.web.format.WebConversionService}
     * @see WebMvcAutoConfiguration.EnableWebMvcConfiguration#mvcConversionService()
     */
    private void conversionTest() {

        System.out.println("conversionService.hashCode() = " + conversionService.hashCode());

        LOG.info("conversionService instanceof WebConversionService = " + (conversionService instanceof WebConversionService));

        ConfigurableConversionService conServiceFromEnv = ((ConfigurableEnvironment) environment).getConversionService();
        LOG.info("conServiceFromEnv instanceof ApplicationConversionService = " + (conServiceFromEnv instanceof ApplicationConversionService));

        System.out.println("conversionService = " + conversionService);
        System.out.println("conServiceFromEnv = " + conServiceFromEnv);

        // String -> LocalDate, ParserConverter
        LocalDate localDate = conversionService.convert("19-12-25", LocalDate.class);
        System.out.println("localDate = " + localDate);

        /**
         * String -> Date, 没有自定义注册DateFormatter时，此处不会使用注册与注解关联的转换器，
         * 默认使用 new Date(String source) 进行转换
         *
         * @see Date#(String)
         */
        Date date = conversionService.convert("Jan 12, 1952", Date.class);
        System.out.println("date = " + date);
    }
}
