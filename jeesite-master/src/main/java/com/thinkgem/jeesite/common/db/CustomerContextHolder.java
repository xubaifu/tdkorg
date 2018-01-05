package com.thinkgem.jeesite.common.db;

public class CustomerContextHolder {  
    public static final String DATA_SOURCE_A = "dataSource";//3A系统数据库
    public static final String DATA_SOURCE_B = "dataSource2";//人事数据库
    /*public static final String DATA_SOURCE_C = "dataSource3";//考勤数据库
    public static final String DATA_SOURCE_D = "dataSource4";//门禁数据库
    public static final String DATA_SOURCE_E = "dataSource5";//餐卡数据库
*/    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    public static void setCustomerType(String customerType) {  
        contextHolder.set(customerType);  
    }  
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
}  
