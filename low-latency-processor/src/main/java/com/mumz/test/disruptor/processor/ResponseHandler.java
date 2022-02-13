package com.mumz.test.disruptor.processor;

import com.lmax.disruptor.EventHandler;
import com.mumz.test.disruptor.event.pojo.Response;

public class ResponseHandler implements EventHandler<Response> {
    @Override
    public void onEvent(Response event, long sequence, boolean endOfBatch) throws Exception {

    }
}
