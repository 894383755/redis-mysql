package com.kd.xxhyf.test;

import org.springframework.stereotype.Component;

@Component
public class MultiThreadDemo implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.err.println("-------------------------------"+Thread.currentThread());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
