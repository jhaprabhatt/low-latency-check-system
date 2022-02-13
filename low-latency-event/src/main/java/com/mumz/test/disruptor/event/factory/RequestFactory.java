package com.mumz.test.disruptor.event.factory;

import com.lmax.disruptor.EventFactory;
import com.mumz.test.disruptor.event.pojo.Request;

public class RequestFactory implements EventFactory<Request> {

    @Override
    public Request newInstance() {
        return new Request();
    }
}
