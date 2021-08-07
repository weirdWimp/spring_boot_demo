package com.example.springboot.demo.controller.async;

import com.example.springboot.demo.entity.SimpleResponse;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author guo
 * @date 2021/8/7
 */
@RestController
@RequestMapping("/async")
public class BasicAsyncController {

    @GetMapping(value = "deferredResult", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<SimpleResponse<String>> deferredResult() {
        DeferredResult<SimpleResponse<String>> deferredResult = new DeferredResult<>();
        asyncProcess(deferredResult);
        return deferredResult;
    }

    /**
     * By default only a {@link SimpleAsyncTaskExecutor} is used to execute this returned callable task
     * asynchronously. In Spring Boot, you can easily configure a TaskExecutor implementation by declaring
     * a implementation bean named 'applicationTaskExecutor', such as {@link ThreadPoolTaskExecutor}.
     * <p>
     * In Spring Boot, it provide a default ThreadPoolTaskExecutor bean and leaves you just set the pool properties !!
     * <p>
     * This 'applicationTaskExecutor' also used to execute other async tasks outside of the web container,
     * like @Async tasks ?
     *
     * @see WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#configureAsyncSupport(org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer)
     * @see TaskExecutionAutoConfiguration
     * @see TaskExecutionProperties
     */
    @GetMapping(value = "callable", produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<SimpleResponse<String>> callable() {
        return () -> {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                // ignore
            }
            System.out.println("####### Thread " + Thread.currentThread().getName() + " executing callable task");
            return SimpleResponse.success("ok");
        };
    }

    /**
     * If you want to produce multiple asynchronous values and have those written to the response, use ResponseBodyEmitter.
     *
     * @return
     */
    @GetMapping(value = "streaming")
    public ResponseBodyEmitter handle() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        asyncObjectsStreamingProcess(emitter);
        return emitter;
    }

    /**
     * Try this in a browser.
     *
     * @return
     */
    @GetMapping(value = "sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseEmitter() {
        // Create a SseEmitter with a custom timeout value
        SseEmitter sseEmitter = new SseEmitter(30000L);
        serverSentEventsProcess(sseEmitter);
        return sseEmitter;
    }

    private void serverSentEventsProcess(SseEmitter sseEmitter) {
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sseEmitter.send("Hello " + (i + 1));
                    Thread.sleep(1000);
                }
                sseEmitter.complete();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void asyncObjectsStreamingProcess(ResponseBodyEmitter emitter) {
        new Thread(() -> {
            try {
                // each object is serialized with an HttpMessageConverter and written to the response
                emitter.send(SimpleResponse.success("Hello"));

                Thread.sleep(5000);

                emitter.send(SimpleResponse.success("World"));

                Thread.sleep(1000);
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void asyncProcess(DeferredResult<SimpleResponse<String>> deferredResult) {
        new Thread(() -> {
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                //ignore
            }
            deferredResult.setResult(SimpleResponse.success("OK"));
        }).start();
    }


}
