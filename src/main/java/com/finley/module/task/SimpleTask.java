package com.finley.module.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 * @date 2017/8/1
 */


@Component
public class SimpleTask {

    @Scheduled(fixedDelay =1 * 1000)
    public void print(){
//        System.out.println("--------Hello Task-----------");
    }
}
