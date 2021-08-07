package com.example.springboot.demo.controller.async;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.StandardCharsets;

/**
 * Sometimes, it is useful to bypass message conversion and stream directly to the response OutputStream
 * (for example, for a file download). You can use the StreamingResponseBody return value type to do so.
 * <p>
 * You can use StreamingResponseBody as the body in a ResponseEntity to customize the status
 * and headers of the response.
 *
 * @author guo
 * @date 2021/8/7
 */
@RequestMapping("/raw")
@Controller
public class RawDataController {

    @GetMapping("download")
    public StreamingResponseBody handle() {
        return outputStream -> {
            outputStream.write("Hello World".getBytes(StandardCharsets.UTF_8));
        };
    }
}
