package com.mumz.test.disruptor.service;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.mumz.test.disruptor.consumer.RequestConsumer;
import com.mumz.test.disruptor.consumer.ResponseConsumer;
import com.mumz.test.disruptor.event.factory.RequestFactory;
import com.mumz.test.disruptor.event.factory.ResponseFactory;
import com.mumz.test.disruptor.event.pojo.Request;
import com.mumz.test.disruptor.event.pojo.Response;
import com.mumz.test.disruptor.producer.RequestProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LowLatencyService {

    public static void main(String[] args) {
        int bufferSize = 1024;
        RingBuffer<Response> responseRingBuffer = buildResponseDisruptor(bufferSize);
        buildRequestDisruptor(bufferSize, responseRingBuffer);
    }

    private static void buildRequestDisruptor(int bufferSize, RingBuffer<Response> responseRingBuffer) {
        final RequestFactory requestFactory = new RequestFactory();
        final RequestConsumer requestHandler = new RequestConsumer(responseRingBuffer);
        final Disruptor<Request> requestDisruptor = new Disruptor<>(requestFactory, bufferSize, DaemonThreadFactory.INSTANCE);
        requestDisruptor.handleEventsWith(requestHandler);
        final RequestProducer requestProducer = new RequestProducer(requestDisruptor.getRingBuffer());
        requestDisruptor.start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> requestProducer.start());
    }

    private static RingBuffer<Response> buildResponseDisruptor(int bufferSize) {
        final ResponseFactory responseFactory = new ResponseFactory();
        final ResponseConsumer responseConsumer = new ResponseConsumer();
        final Disruptor<Response> responseDisruptor = new Disruptor<>(responseFactory, bufferSize, DaemonThreadFactory.INSTANCE);
        responseDisruptor.handleEventsWith(responseConsumer);
        responseDisruptor.start();
        return responseDisruptor.getRingBuffer();
    }
}
