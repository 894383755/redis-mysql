package com.kd.xxhyf.main;

import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

/**
 *  启动项
 * @author 刘景涛 E-mail:18615564548@163.com
 *         2018年9月11日 下午2:43:31
 *
 */
public class Main {
	
	public static void loadLoggerContext() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.setPrintStream(System.err);
		StatusPrinter.print(lc);
	}
	
	public static void main(String[] args) {
		loadLoggerContext();//加载日志
		new ClassPathXmlApplicationContext("classpath:conf/spring-*.xml");//动态扫描spring配置文件，装载类
	}
	
}
