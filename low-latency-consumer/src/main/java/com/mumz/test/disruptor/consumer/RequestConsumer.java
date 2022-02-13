package com.mumz.test.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.mumz.test.disruptor.event.pojo.Request;
import com.mumz.test.disruptor.event.pojo.Response;

public class RequestConsumer implements EventHandler<Request> {

    private final RingBuffer<Response> responseRingBuffer;

    public RequestConsumer(final RingBuffer<Response> responseRingBuffer) {
        this.responseRingBuffer = responseRingBuffer;
    }
    @Override
    public void onEvent(Request event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Incoming Request : " + event + " , Sequence is : " + sequence + " , EndofBatch : " + endOfBatch);
        produce(event.getId(), event.getName(), endOfBatch ? "Before Batch" : "End of Batch");
    }

    private void produce(final long id, final String name, final String resp) {
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
