package com.mumz.test.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import com.mumz.test.disruptor.event.pojo.Response;

public class ResponseConsumer implements EventHandler<Response> {
    @Override
    public void onEvent(Response event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Incoming Response : " + event + " , Sequence is : " + sequence + " , EndofBatch : " + endOfBatch);
    }
}
