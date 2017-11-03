package com.finley.common.cache;

/**
 * Created by finley on 2017/5/11.
 */
public interface ApplicationCache {

     public <T> void addObject(String key, T obj) throws Exception ;

     public <T> void addObject(String key, T obj,long duration) throws Exception ;

     public <T> T getObject(String key) throws Exception  ;

     public void removeObject(String key) throws Exception ;

}
