package com.mumz.test.disruptor.event.factory;

import com.lmax.disruptor.EventFactory;
import com.mumz.test.disruptor.event.pojo.Request;
import com.mumz.test.disruptor.event.pojo.Response;

public class ResponseFactory implements EventFactory<Response> {

    @Override
    public Response newInstance() {
        return new Response();
    }
}
