package com.example.springboot.demo.util;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * @author guo
 * @date 2021/8/12
 */
public class UrlBuilderUtil {

    public static void main(String[] args) throws MalformedURLException {
        uriComponentsBuilder();
        uriBuilder();
    }

    private static void uriComponentsBuilder() throws MalformedURLException {
        String templateUri = "http://example.com/hotels/{hotel}?q={q}";


        // UriComponentsBuilder#encode(): 首先对 URI 模板进行预编码，然后在扩展时，对URI 变量进行严格编码 (URI 变量值中的 http uri 保留字符会被进行转义)
        URI uri1 = UriComponentsBuilder.fromUriString(templateUri) // provides a uri template
                .encode()
                .buildAndExpand("Amazon Hotel", "A?B")
                .toUri();

        // 使用 UriComponentsBuilder#encode() 方式进行编码
        URI uri2 = UriComponentsBuilder.fromUriString(templateUri) // provides a uri template
                .build("Amazon Hotel", "123&"); // implies encoding

        System.out.println(uri1);
        System.out.println(uri2);

        // UriComponents#encode(): 对 URI 变量扩展后，对 URI 组件（component）进行编码
        // URI uri1 = uriComponents.encode().toUri();
        // System.out.println(uri.toString());
    }

    private static void uriBuilder() {
        // 使用 UriBuilderFactory 来定制化 URI，暴露一些共享的配置选项
        // 内部使用 UriComponentsBuilder 来对 URI 模板进行扩展和编码
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES); // 设置编码模式

        RestTemplate restTemplate1 = new RestTemplate();
        restTemplate1.setUriTemplateHandler(factory);

        String baseUri = "http://example.com/hotels/{hotel}?q={q}";

        // ? 是一个 http url 保留字符, TEMPLATE_AND_VALUES 模式下（使用 UriComponentsBuilder#encode()），首先会对 URI 模板进行预编码
        // 然后在进行扩展 url 时，会对变量的值进行严格的转义 URI 变量值中的 http uri 保留字符会被进行转义
        // ? 会被转义为 %3F，这时复合预期的，因为它是作为查询参数的值出现的，而不是作为一个分隔 url 中的路径与查询参数的字符
        String s1 = restTemplate1.getForObject(baseUri, String.class, "Amazon Hotel", "A?B");

        // 默认的 RestTemplate 编码模式为 EncodingMode.URI_COMPONENT（使用 UriComponents#encode()）， 首先进行扩展，然后对 URI component 值进行转义
        // 只对非 ASCII 字符以及当前 component 下的非法字符进行转义，保留字符不会进行转义
        // 当有意在 URI 变量中包含保留字符时，可以使用该模式
        // 默认使用该模式，是出于向后兼容性
        RestTemplate restTemplate2 = new RestTemplate();
        String s2 = restTemplate2.getForObject(baseUri, String.class, "Amazon Hotel", "A?B");
    }
}
