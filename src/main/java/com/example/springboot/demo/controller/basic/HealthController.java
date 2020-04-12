package com.example.springboot.demo.controller.basic;

import com.example.springboot.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("modelAttributeInSession")
public class HealthController {


    public static final String INIT_PERSON = "initPerson";

    @Autowired
    private ConversionService conversionService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addCustomFormatter(new DateFormatter("yyyyMMdd"));

        final ConversionService dataBinderConversionService = webDataBinder.getConversionService();
        System.out.println("WebDataBinder ConversionService Class = " + dataBinderConversionService.getClass());
        System.out.println("WebDataBinder ConversionService is equal Autowired  ConversionService = "
                + (conversionService == dataBinderConversionService));

        if (dataBinderConversionService instanceof WebConversionService) {
            final WebConversionService webConversionService = (WebConversionService) dataBinderConversionService;

            // String <--> LocalDate : custom default pattern
            final DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            registrar.registerFormatters(webConversionService);
        }
    }

    /**
     * Initialize the model prior to any @RequestMapping method invocation.
     * Share between controllers through @ControllerAdvice
     */
    @ModelAttribute(INIT_PERSON)
    public Person initPerson() {
        return new Person("Tom Cat");
    }

    /**
     * A method with a void return type (or null return value) is considered
     * to have fully handled the response if it also has a ServletResponse,
     * an OutputStream argument, or an @ResponseStatus annotation.
     */
    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void checkHealth(@RequestHeader MultiValueMap<String, String> httpHeaders,
                            @RequestHeader("accept-encoding") List<String> acceptEncodings) {
        for (Map.Entry<String, List<String>> head : httpHeaders.entrySet()) {
            System.out.println(head.getKey() + " = " + head.getValue());
        }

        for (String acceptEncoding : acceptEncodings) {
            System.out.println("acceptEncoding = " + acceptEncoding);
        }
        System.out.println("#####################");
    }

    @GetMapping("/health/save_session_attribute")
    @ResponseStatus(HttpStatus.OK)
    public void saveSessionAttribute(Model model) {

        System.out.println("model contains initPerson = " + model.containsAttribute(INIT_PERSON));

        model.addAttribute("modelAttributeInSession", new Date());
    }

    @GetMapping("/health/access_session_attribute")
    @ResponseStatus(HttpStatus.OK)
    public void accessSessionAttribute(@SessionAttribute Date date) {
        System.out.println("date = " + date);
    }

    /**
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#setIgnoreDefaultModelOnRedirect(boolean)
     */
    @GetMapping("/health/{date}")
    public String date(@PathVariable String date, RedirectAttributes redirectAttributes) {
        // add attribute used in RedirectView and can be used in URI template variables and query parameters
        redirectAttributes.addAttribute("a1", "a1 value");
        redirectAttributes.addAttribute("a2", new Date());
        redirectAttributes.addAttribute("a3", new Person());

        // add flash attribute stored in session and can keep Object value not String
        redirectAttributes.addFlashAttribute("a4", new Person("Jason"));

        // URI template variables from the present request are automatically made available when expanding a redirect URL
        return "redirect:/health/redirect/{date}";
    }

    @GetMapping("/health/redirect/{date}")
    @ResponseStatus(HttpStatus.OK)
    public void redirect(@PathVariable Date date, @RequestParam Map<String, String> requestParams, Model model) {
        System.out.println("PathVariable = " + date);
        for (Map.Entry<String, String> queryParam : requestParams.entrySet()) {
            System.out.println(queryParam.getKey() + " = " + queryParam.getValue());
        }

        final Object o = model.getAttribute("a4");
        System.out.println("flash attribute = " + o);
    }

    @GetMapping("/health/convert/{strValue}")
    @ResponseStatus(HttpStatus.OK)
    public void convertTest(@PathVariable("strValue") LocalDate localDate) {
        System.out.println("strValue = " + localDate);
    }

    // RequestContextUtils


    @GetMapping("/health/header")
    public ResponseEntity httpHeaderTest() {
        System.out.println("Accept Request from /health/header");
        final HttpHeaders headers = new HttpHeaders();

        // 服务器端发往客户端的 Cookie 信息
        final String setCookie = ResponseCookie.from("SPRING_BOOT_COOKIE", "20200301171900")
                .path("/").maxAge(Duration.ofDays(10))
                .build().toString();
        headers.set(HttpHeaders.SET_COOKIE, setCookie);

        // 告知客户端应该在多久之后再次发送请求，一般配置 503-Service Unavailable 或 3xx Redirect 相应
        headers.set(HttpHeaders.RETRY_AFTER, "2");

        // 非 Http 1.1 标准，指定时间后，请求指定 url
        headers.set("Refresh", "2; https://www.baidu.com/");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .cacheControl(CacheControl.maxAge(Duration.ofDays(1)))
                .headers(headers)
                .body("Service Not Available Now...");
    }


    @GetMapping("/health/redirect")
    public ResponseEntity redirect() {
        System.out.println("Accept Request from /health/redirect");

        // 浏览器就收到包含首部 Location 的 相应后，会强制性地尝试对重定向资源访问
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .cacheControl(CacheControl.maxAge(Duration.ofDays(1)))
                .header(HttpHeaders.LOCATION, "https://www.baidu.com/")
                .body("Direct to baidu.com...");
    }

}
