package com.example.demo;

import feign.Client;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private Client client;

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String hello() {

        LOGGER.info("Inside hello");

        DemoClient bookClient = Feign.builder()
                .client(client)
//                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(DemoClient.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(DemoClient.class, "http://localhost:8200/demo");

        String goodbye = bookClient.callGoodbye();

        LOGGER.info("Received goodbye response: " + goodbye);

        return "Hello";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/goodbye")
    public String goodbye() {

        LOGGER.info("Inside goodbye");

        return "Goodbye";
    }
}
