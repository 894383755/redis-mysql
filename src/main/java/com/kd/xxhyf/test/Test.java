package com.kd.xxhyf.test;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.kd.xxhyf.database.connection.Connection;


@Component
public class Test implements Runnable{
	
	@Autowired
	private Connection connection;
	
	
	//初始化方法
	@Async
	public void test(){
		System.err.println(11);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.err.println("Test:"+Thread.currentThread());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
