package com.kd.xxhyf.test;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TestThread {
	
	public static void main(String[] args) {
		int i =0;
		byte b= (byte)i;
	    System.out.println(b);
	}
	
	@Autowired
	private Test2 test2;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Async
	public void test(){
		while (true) {
			  taskExecutor.execute(new MultiThreadDemo());
			  System.out.println("now threadpool active threads totalnum is " + taskExecutor.getActiveCount());
			
		}
	}
}
