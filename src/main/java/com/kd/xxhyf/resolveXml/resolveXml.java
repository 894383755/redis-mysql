package com.kd.xxhyf.resolveXml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class resolveXml {
	private static final Logger LOGGER = LoggerFactory.getLogger(resolveXml.class);
	/**
	 * 解析告警的xml
	 * @param xml
	 * @return 
	 */
	public static Map<String, Object> alarmXml(String xml){
		Map<String,Object> map=new HashMap<>();
		try {
			//将xml字符串转换成xml
			Document doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			//当前节点的名称、文本内容和属性   
		    List<Attribute> listAttr=rootElt.attributes();//当前节点的所有属性的list  
		    for(Attribute attr:listAttr){//遍历当前节点的所有属性  
		        String name=attr.getName();//属性名称  
		        String value=attr.getValue();//属性的值  
		        map.put(rootElt.getName().toUpperCase(), value);
		    }  
		      
		    //递归遍历当前节点所有的子节点  
		    // 解析子节点的信息  
	        Iterator list = rootElt.elementIterator();  
	        //Map<String,Object> map=new HashMap<>();
	        while (list.hasNext()) {
	              Element listChild = (Element) list.next(); 
	              String cName=listChild.getName();
	              String cValue=listChild.getStringValue();
	              map.put(cName, cValue);
	          } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.err.println(xml);
			LOGGER.error(xml);
			LOGGER.error(e.getMessage());
			//e.printStackTrace();
		} // 将字符串转为XML
		
		return map;
	}
	
	/**
	 * 解析状态的xml
	 * @param xml
	 * @return
	 */
	public static Map<String, List<Map<String, Object>>> statusXml(String xml){
		Map<String,List<Map<String,Object>>> map=new HashMap<>();
		try {
			//将xml字符串转换成xml
			Document doc= DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			//获取到根节点下的所有list节点
			List<Element> listElement=rootElt.elements();
			int count = 0;
			for(Element e:listElement){
		    	List<Attribute> listAttr1=e.attributes();//当前节点的所有属性的list  
		    	String keyName="";
		    	 for(Attribute attr1:listAttr1){//遍历当前节点的所有属性   
				        String value=attr1.getValue();//属性的值  
				        if("".equals(keyName)){
				        	keyName=value+"_";
				        }else{
				        	keyName=keyName+value;
				        }
				        
				 }
		    	 List<Map<String, Object>> valueList=getValueList(e);
		    	 keyName = keyName+"_"+count;
		    	 map.put(keyName, valueList);
		    	 count ++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.err.println(xml);
			LOGGER.error(xml);
			LOGGER.error(e.getMessage());
		}
		return map;
	}



	private static List<Map<String, Object>> getValueList(Element e) {
		List<Map<String, Object>> valueList=new LinkedList<>();
		 // 解析子节点的信息  
        Iterator list = e.elementIterator();  
        //Map<String,Object> map=new HashMap<>();
        while (list.hasNext()) { 
              Element listChild = (Element) list.next(); 
              String cName=listChild.getName().toUpperCase();
              String cValue=listChild.getStringValue();
              String [] str=cValue.split(",");
              if(valueList.size()==0){
            	  for (int i = 0; i < str.length; i++) {
            		  Map<String,Object> map=new HashMap<>();
            		  map.put(cName, str[i]);
            		  valueList.add(map);
				}
              }else{
            	  for (int i = 0; i < str.length; i++) {
            		  valueList.get(i).put(cName, str[i]);
				}
              }  
          }
		return valueList;
	}
 

}
