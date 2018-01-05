package com.thinkgem.jeesite.common.db;
 
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
/**
 * Mysql 多数据源切换
 *
 * @author baifu
 * @version V1.0
 * @Description:
 * @date 2017/03/02
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
       
    /** 
     *  
     * @author baifu
     * @date 2017-3-2 上午9:46:44 
     * @return the currentLookupKey 
     */  
    public static String getCurrentLookupKey() {  
        return (String) contextHolder.get();  
    }  
   
    /** 
     *  
     * @author baifu
     * @date 2017-3-2 上午9:46:44 
     * @param currentLookupKey 
     *            the currentLookupKey to set 
     */  
    public static void setCurrentLookupKey(String currentLookupKey) {  
        contextHolder.set(currentLookupKey);  
    }  
   
    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource# 
     * determineCurrentLookupKey() 
     */  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return getCurrentLookupKey();  
    }  
    //数据源切换方式
    //DynamicDataSource.setCurrentLookupKey("dataSource2");
    //DynamicDataSource.setCurrentLookupKey("dataSource");
}