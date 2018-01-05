/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.db.CustomerContextHolder;
import com.thinkgem.jeesite.common.db.DynamicDataSource;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SystemManagementEntity;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.service.SystemManagementService;

/**
 * 系统管理Controller
 * @author baifu
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/systemmanagement/systemManagement")
public class SystemManagementController extends BaseController {

	@Resource
	private SystemManagementService systemManagementService;
	
	private Timer timer = null; 
	
	/*@ModelAttribute
	public SystemManagement get(@RequestParam(required=false) String id) {
		SystemManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = systemManagementService.get(id);
		}
		if (entity == null){
			entity = new SystemManagement();
		}
		return entity;
	}*/
	
	
	/**
	 * 数据同步管理
	 * @param systemManagement
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("systemmanagement:datamanagement:view")
	@RequestMapping(value = {"dataManagement"})
	public String dataManagement(SystemManagementEntity systemManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*Page<SystemManagement> page = systemManagementService.findPage(new Page<SystemManagement>(request, response), systemManagement); 
		model.addAttribute("page", page);*/
		return "modules/tdkorg/systemmanagement/dataManagement";
	}

	
	/**
	 * 定时任务
	 * @param startTime
	 * @param timeLag
	 * @param type
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = {"startOrganization","endOrganization"})
	@ResponseBody
	public List<Object> start(Date startTime, int timeLag,String type) {  
		List<Object> listEHR = new ArrayList<Object>();
		//先执行一次同步
		
        Calendar calendar = Calendar.getInstance(); 
        calendar.set(Calendar.DAY_OF_MONTH, startTime.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 控制时  
        calendar.set(Calendar.MINUTE, 0);       // 控制分  
        calendar.set(Calendar.SECOND, 0);       // 控制秒  
   
        Date time = calendar.getTime();         // 得出执行任务的时间  
        
        if("STOP".equals(type)){//停止定时任务
        	if(timer != null){
        		timer.cancel();
        		timer = null;
        	}
        }else{//启动定时任务
        	if(timer == null){//首次启动
        		timer = new Timer();
        		System.out.println("首次启动需要先执行一次同步");
        		startOrEndOrganization();
        	}else{//重复启动
        		timer.cancel();
        		timer = null;
        		timer = new Timer();
        	}
        	//执行任务
        	timer.scheduleAtFixedRate(new TimerTask() {  
                public void run() {  
                	System.out.println(new Date()); 
                	startOrEndOrganization();
                }  
            }, time, 1000 * 60 * 60 * timeLag);// 这里设定将延时固定执行
        	
        }
        listEHR.add(0, 1);
        return listEHR;
    }
	/**
	 * 组织结构数据同步启动、停止(主要的数据同步业务逻辑)
	 * @param startTime
	 * @param timeLag
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public List<SystemManagementEntity> startOrEndOrganization() {
		SystemManagementEntity entity = new SystemManagementEntity();
		System.out.println("开始执行数据同步");
		//Map<String,Object> params = new HashMap<String,Object>();
		//切换到数据源2（连接ehr数据库）
		DynamicDataSource.setCurrentLookupKey(CustomerContextHolder.DATA_SOURCE_B);
		List<SystemManagementEntity> listEHR = new ArrayList<SystemManagementEntity>();
		List<SystemManagementEntity> list3A = new ArrayList<SystemManagementEntity>();
		//使用ehr系统数据库查询EHR系统的组织结构
		listEHR = systemManagementService.getOrganizationFromEHR(entity);
		
		
		
		//切换到数据源1（连接3A数据库）
		DynamicDataSource.setCurrentLookupKey(CustomerContextHolder.DATA_SOURCE_A);
		
		systemManagementService.addOrganizationAll(listEHR);
		
		
		
		
		
		//int length = listEHR.size();
		//String id = "";
		//切换到数据源1（连接3A数据库）
		//DynamicDataSource.setCurrentLookupKey(CustomerContextHolder.DATA_SOURCE_A);
		
		
		
		/*//遍历查询结果，跟3A数据库中的组织结构对比，若有对应的结构，则修改为最新的，若没有则新增
		String parent_ids = "";
		int grade;
		String[] pids = new String[10];
		if(length>0){
			for(int i = 0; i < length; i++){
				//id = listEHR.get(i).getCodeitemidEHR();
				entity.setId3A(listEHR.get(i).getCodeitemidEHR());
				
				entity.setCode3A(listEHR.get(i).getCorCodeEHR());
				entity.setCreateDate3A(listEHR.get(i).getStartDateEHR());
				entity.setGrade3A(listEHR.get(i).getGradeEHR());
				entity.setId(listEHR.get(i).getCodeitemidEHR());
				entity.setName3A(listEHR.get(i).getCodeitemdescEHR());
				entity.setParentId3A(listEHR.get(i).getParentIdEHR());
				entity.setType3A(listEHR.get(i).getGradeEHR());
				entity.setUpdateDate3A("");
				//获取组织结构的父级结构
				grade = Integer.parseInt(listEHR.get(i).getGradeEHR());
				if(grade == 1){
					parent_ids="-1,";
					pids[grade-1]=parent_ids;
				}else if(grade>1){
					parent_ids = pids[grade-2]+listEHR.get(i).getParentIdEHR()+",";
					pids[grade-1]=parent_ids;
				}
				entity.setParentIds3A(pids[grade-1]);
				
				list3A = systemManagementService.getOrganizationFrom3A(entity);
				
				System.out.println(list3A.size());
				if("0".equals(list3A.get(0).getNum())){
					systemManagementService.addOrganization(entity);
				}else{
					systemManagementService.updateOrganization(entity);
				}
			}
		}
       //根据当前日期查询人员信息，凡是更新日期在今日之前的删除
		List<SystemManagementEntity> listForDel = new ArrayList<SystemManagementEntity>();
		listForDel = systemManagementService.getListByUpdate(entity);
		if(listForDel.size()>0){
			for(int j = 0; j < listForDel.size(); j++){
				System.out.println("--------"+listForDel.get(j).getId());
				System.out.println("++++++++"+listForDel.get(j).getParentId());
				if("1001".equals(listForDel.get(j).getId()) || "-1".equals(listForDel.get(j).getParentId()) || "-1".equals(listForDel.get(j).getId())){
					System.out.println(0);
				}else{
					systemManagementService.delete(listForDel.get(j));
				}
			}
		}*/
				
		return listEHR;
	}

}