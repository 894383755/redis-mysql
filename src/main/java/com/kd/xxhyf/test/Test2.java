package com.kd.xxhyf.test;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.kd.xxhyf.database.connection.Connection;

/*@Configuration
@EnableAsync*/
@Component
public class Test2 {
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	//初始化方法
	@Async
	public void test(){
		System.err.println("====================");
		List<Map<String, Object>> ss = connection.findForDruid("SELECT * FROM PLATFORM.T_S_ROLE");
		System.err.println(ss.size()+"--------------------======="); 
		while (true) {
			System.err.println(111);
		}
	}
	
	//销毁后方法
	@Async
	public void ss(){
		while (true) {
			 taskExecutor.execute(new Test());
			 System.out.println("now threadpool active threads totalnum is " + taskExecutor.getActiveCount());
		}
	}
	
}
