package com.example.controller.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class Test {

    private ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();

    private AtomicInteger cm = new AtomicInteger(0);

    private AtomicInteger cu = new AtomicInteger(0);

    public void setMap(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    public void changeMap(String key,String value){

        this.map.put(key,value);

    }

    public ConcurrentHashMap<String, String> getMap() {
        return map;
    }

    public AtomicInteger getCm() {
        return cm;
    }

    public void setCm(AtomicInteger cm) {
        this.cm = cm;
    }

    public AtomicInteger getCu() {
        return cu;
    }

    public void setCu(AtomicInteger cu) {
        this.cu = cu;
    }
}
