package com.mumz.test.disruptor.producer;

import com.mumz.test.disruptor.event.factory.ResponseFactory;
import com.mumz.test.disruptor.event.pojo.Response;

public class ResponseProducer {

    private final ResponseFactory responseFactory;

    public ResponseProducer(final ResponseFactory requestFactory) {
        this.responseFactory = requestFactory;
    }

    public void produce() {
        Response request = this.responseFactory.newInstance();
    }
}
