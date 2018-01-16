package com.example.demo;

import feign.RequestLine;

public interface DemoClient {

    @RequestLine("GET /goodbye")
    String callGoodbye();

}
