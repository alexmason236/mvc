package com.zk.utils;


import lombok.Data;
import org.springframework.web.context.request.async.DeferredResult;

@Data
public class DeferredResultWrapper<T> {
    T id;
    DeferredResult target;

    public DeferredResultWrapper(T id, DeferredResult target) {
        this.id = id;
        this.target = target;
    }
}
