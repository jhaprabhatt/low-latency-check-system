package com.mumz.test.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import com.mumz.test.disruptor.event.pojo.Request;

import java.util.stream.IntStream;

public class RequestProducer {

    private final RingBuffer<Request> requestRingBuffer;

    public RequestProducer(final RingBuffer<Request> ringBuffer) {
        this.requestRingBuffer = ringBuffer;
    }

    private void produce(final int id, final String name) {
        long sequence = this.requestRingBuffer.next();
        try {
            Request request = this.requestRingBuffer.get(sequence);
            request.setId(id);
            request.setName(name);
        } finally {
            this.requestRingBuffer.publish(sequence);
        }
    }

    public void start() {
        IntStream.rangeClosed(1,1024).forEach(value -> {
            produce(value, "Name " + value);
        });
    }
}
