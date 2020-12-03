package com.kd.xxhyf.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class demo {
	
	public static void main(String args[]) throws Exception {
	      
	      sendMessage();
	   }
	   
	   // 传递信息
	   public static void sendMessage() {
	      String ip = "192.100.5.3";        // 设置发送地址和端口号
	      int port = 9999;
	      Socket socket = null;
	      try {
	         // 连接服务器
	         socket = new Socket(ip, port);
	         // 获取输入流
	         InputStream in = socket.getInputStream();   //读取数据
	         // 获取输出流
	         OutputStream out = socket.getOutputStream(); // 发送数据
	         // 包装输入流，输出流，包装一下可以直接传输字符串，不包装的话直接使用InputStream和OutputStream只能直接传输byte[]类型数据
	         BufferedReader inRead = new BufferedReader(new InputStreamReader(in));
	   
	         while(true){
	            Scanner sc = new Scanner(System.in);
	            System.out.println("请输入DATA：");
	            String dataStr = sc.nextLine();
	            byte[] data = dataStr.getBytes();
	            // 发送数据
	            out.write(data);
	         }
	         
	         // 接受应答
	         //String result = inRead.readLine();  // 使用了包装后的输入流方便读取消息
	         
	         //System.out.println(result);
	         //return result;
	      } catch(UnknownHostException e) {
	         e.printStackTrace();
	      } catch(IOException e) {
	         e.printStackTrace();
	      }
	   }

}
