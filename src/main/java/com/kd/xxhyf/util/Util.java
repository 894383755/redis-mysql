package com.kd.xxhyf.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Util {
	
	public static Map<String, Object> orgdcc = new HashMap<String, Object>();
	
	static{
		orgdcc.put("000000", "PLATFORM");
		orgdcc.put("350000", "FUJIAN");
		orgdcc.put("350100", "FUZHOU");
		orgdcc.put("350200", "XIAMENG");
		orgdcc.put("350300", "PUTIAN");
		orgdcc.put("350400", "SANMING");
		orgdcc.put("350500", "QUANZHOU");
		orgdcc.put("350600", "ZHANGZHOU");
		orgdcc.put("350700", "NANPING");
		orgdcc.put("350800", "LONGYAN");
		orgdcc.put("350900", "NINGDE");
	}
	/**
	 * ��ȡsql
	 * @param tableName
	 * @param sqlMap
	 * @param isNodeid 
	 * @return
	 */
	 public static  String getSql(String tableName, Map<String, Object> sqlMap,String nodeid) {
		String sql="";
		String fild="";
		String value="";
		Iterator<Entry<String, Object>> it = sqlMap.entrySet().iterator();
        while (it.hasNext()) {
          Entry<String, Object> entry = it.next();
          String key=entry.getKey().toUpperCase();
          String val=entry.getValue()+"";
          fild+=key+",";
          value+="'"+val+"',";
        }
        if(nodeid.substring(6,12).equals("350000")){
        	//福建
        	sql="insert into  FUJIAN."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350100")){
        	//福州
        	sql="insert into FUZHOU."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350200")){
        	//厦门
        	sql="insert into XIAMENG."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350300")){
        	//莆田
        	sql="insert into PUTIAN."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350400")){
        	//三明
        	sql="insert into SANMING."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350500")){
        	//泉州
        	sql="insert into QUANZHOU."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350600")){
        	//漳州
        	sql="insert into ZHANGZHOU."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350700")){
        	//南平
        	sql="insert into NANPING."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350800")){
        	//龙岩
        	sql="insert into LONGYAN."+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }else if(nodeid.substring(6,12).equals("350900")){
           //宁德
        	sql="insert into "+tableName+" ( id, "+fild.substring(0, (fild.length()-1))+" ) "
        			+ "values ( '"+nodeid+"', "+value.substring(0, (value.length()-1))+" ) ";
        }
		return sql;
	}
	 
	    /**
	     * 获取到更新的sql
	     * @param tableName
	     * @param sqlMap
	     * @param device
	     * @param condition 
	     * @param condition 
	     * @return
	     */
		public static String getUpdateSql(String tableName,
			Map<String, Object> sqlMap, String device, String condition) {
			String sql="update  "+tableName+" set ";
			Iterator<Entry<String, Object>> it = sqlMap.entrySet().iterator();
	        while (it.hasNext()) {
	          Entry<String, Object> entry = it.next();
	          String key=entry.getKey().toUpperCase();
	          String val=entry.getValue()+"";
	          sql=sql+" "+key+"='"+val+"' ,";
	        }
	        sql=sql.substring(0,(sql.length()-1)) +" WHERE 1=1 AND ID='"+device+"' ";
	       /* String [] conditions=condition.split(",");
	        for(int i=0;i<conditions.length;i++){
	        	if(conditions[i]=="nodeid"){
	        		sql=sql+" AND  "+conditions[i]+"='"+device+"'";
	        	}else{
	        		
	        		sql=sql+" AND  "+conditions[i]+"='"+sqlMap.get(conditions[i])+"'";
	        	}
	        }*/
			return sql;
		}

		public static String orgdccName(String orgdcc){
			return Util.orgdcc.get(orgdcc)+"";
		}
		
		
		
		public static  String getMD5String(String str){

            try {
                MessageDigest md=MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
                // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
                //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
                return new BigInteger(1, md.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }


        }
}
