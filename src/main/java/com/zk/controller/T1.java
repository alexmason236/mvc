package com.zk.controller;

import com.zk.utils.CommonResponse;
import com.zk.utils.DeferredResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@Slf4j
public class T1 {

    static final LinkedBlockingQueue<DeferredResultWrapper<HttpServletRequest>> linkedBlockingQueue = new LinkedBlockingQueue();
    static AtomicBoolean isRun = new AtomicBoolean(false);
    static AtomicLong count = new AtomicLong(0);
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @RequestMapping("/t1")
    @ResponseBody
    public DeferredResult<String> t1(HttpServletRequest request) {
        log.info("DeferredResult t1");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        DeferredResultWrapper<HttpServletRequest> resultWrapper = new DeferredResultWrapper<>(request, deferredResult);
        linkedBlockingQueue.add(resultWrapper);
        return deferredResult;

    }

    @RequestMapping("/fullfileT1")
    @ResponseBody
    public String fullfileT1() {
        if (T1.isRun.get()) return "threadpool running,do not repeat!";
        executor.execute(() -> {
            try {
                T1.isRun.set(true);
                while (true) {
                    DeferredResultWrapper<HttpServletRequest> result = T1.linkedBlockingQueue.take();
                    DeferredResult target = result.getTarget();
                    long countInfo = T1.count.getAndIncrement();
                    try {
                        int i=1/0;
                    }catch (Exception e){
                        log.error(e.getMessage());
                        target.setResult(new CommonResponse(500, e.getMessage()));
                    }
//                    target.setResult(new CommonResponse(200, "OK " + countInfo));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return "executor run success";
    }

    @RequestMapping("/exception")
    @ResponseBody
    public String t1Exception(){
        int i=1/0;
        return "ex";
    }


}
