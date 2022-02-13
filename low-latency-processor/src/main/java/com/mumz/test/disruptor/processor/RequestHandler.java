package com.mumz.test.disruptor.processor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.mumz.test.disruptor.event.pojo.Request;
import com.mumz.test.disruptor.event.pojo.Response;

public class RequestHandler implements EventHandler<Request> {
    private final RingBuffer<Response> responseRingBuffer;

    public RequestHandler(final RingBuffer<Response> responseRingBuffer) {
        this.responseRingBuffer = responseRingBuffer;
    }

    @Override
    public void onEvent(final Request request, final long sequence, final boolean endOfBatch) throws Exception {
        System.out.println("Incoming Request : " + request);
    }

    private void produce(final int id, final String name, final String resp) {
        long sequence = this.responseRingBuffer.next();
        try {
            Response response = this.responseRingBuffer.get(sequence);
            response.setId(id);
            response.setName(name);
            response.setResponse(resp);
        } finally {
            this.responseRingBuffer.publish(sequence);
        }
    }
}
