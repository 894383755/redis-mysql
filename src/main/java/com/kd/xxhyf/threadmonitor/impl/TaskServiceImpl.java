package com.kd.xxhyf.threadmonitor.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.kd.xxhyf.threadmonitor.TaskService;


@Service("taskService")
public class TaskServiceImpl implements TaskService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor1;
	
	@Override
	@Scheduled(fixedDelay = 20000)
	public void work() {
		// TODO Auto-generated method stub

		ThreadPoolExecutor tpe = taskExecutor.getThreadPoolExecutor();
		
		int activeCount = tpe.getActiveCount();
		
		LOGGER.info("静态信息入库当前活动线程数：" + activeCount);
		
		int queueSize = tpe.getQueue().size();
		
		LOGGER.info("静态信息入库当前排队线程数：" + queueSize);
	    
	    long completedTaskCount = tpe.getCompletedTaskCount();
	    
	    LOGGER.info("静态信息入库执行完成线程数：" + completedTaskCount);
	 
	    long taskCount = tpe.getTaskCount();
	    
	    LOGGER.info("静态信息入库总线程数：" + taskCount);
	    
	    BlockingQueue<Runnable> list = tpe.getQueue();
	    
	    LOGGER.info("静态信息入库线程池线程数：" + list.size());
	    
	   /* for (Runnable runnable : list) {
			runnable.toString();
		}*/
	    LOGGER.info("------------------------------");
	    
	    ThreadPoolExecutor tpe1 = taskExecutor1.getThreadPoolExecutor();
		
		int activeCount1 = tpe1.getActiveCount();
		
		LOGGER.info("同步数据-待办任务当前活动线程数：" + activeCount1);
		
		int queueSize1 = tpe1.getQueue().size();
		
		LOGGER.info("同步数据-待办任务当前排队线程数：" + queueSize1);
	    
	    long completedTaskCount1 = tpe1.getCompletedTaskCount();
	    
	    LOGGER.info("同步数据-待办任务执行完成线程数：" + completedTaskCount1);
	 
	    long taskCount1 = tpe1.getTaskCount();
	    
	    LOGGER.info("同步数据-待办任务总线程数：" + taskCount1);
	    
	    BlockingQueue<Runnable> list1 = tpe1.getQueue();
	    
	    LOGGER.info("静态信息入库线程池线程数：" + list1.size());
	    
	    LOGGER.info("------------------------------");
		
	}
	
	
}
