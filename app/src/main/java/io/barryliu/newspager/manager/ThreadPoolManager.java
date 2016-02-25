package io.barryliu.newspager.manager;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Barry on 2016/2/25.
 */
public class ThreadPoolManager {
    private static ThreadPoolManager manager;
    ExecutorService service;
    private ThreadPoolManager(){
        //得到cpu 的数量
        int num = Runtime.getRuntime().availableProcessors();
        service = Executors.newFixedThreadPool(num);
    }
    public void execute(Runnable r){
        service.execute(r);
    }
    public static ThreadPoolManager getInstance(){
        if(manager==null){
            manager = new ThreadPoolManager();
        }
        return manager;
    }
}
