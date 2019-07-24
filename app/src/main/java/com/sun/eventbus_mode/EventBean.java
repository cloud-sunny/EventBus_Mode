package com.sun.eventbus_mode;

import eventbu.sun.com.library.EventBus;

public class EventBean {
    private String msg;
    private EventBean(){}

    public String getMsg() {
        return msg;
    }

    public EventBean(String msg) {
        this.msg = msg;
    }
}
