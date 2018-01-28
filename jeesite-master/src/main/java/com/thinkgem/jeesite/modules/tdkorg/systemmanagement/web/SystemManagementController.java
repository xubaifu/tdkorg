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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.db.CustomerContextHolder;
import com.thinkgem.jeesite.common.db.DynamicDataSource;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SysUserDSEntity;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SystemManagementEntity;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.service.SysUserDSService;
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
	
	@Resource
	private SysUserDSService sysUserDSService;
	
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
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
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
        		startOrEndUser();
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
                	startOrEndUser();
                }  
            }, time, 1000 * 60 * 60 * timeLag);// 这里设定将延时固定执行
        	//}, time, 1000 * timeLag);// 这里设定将延时固定执行
        	
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
				
		return listEHR;
	}
	
	/**
	 * 人员信息数据同步启动、停止(主要的数据同步业务逻辑)
	 * @param startTime
	 * @param timeLag
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public List<SysUserDSEntity> startOrEndUser() {
		
		
		SysUserDSEntity entity = new SysUserDSEntity();
		System.out.println("开始执行数据同步");
		//Map<String,Object> params = new HashMap<String,Object>();
		//切换到数据源2（连接ehr数据库）
		DynamicDataSource.setCurrentLookupKey(CustomerContextHolder.DATA_SOURCE_B);
		List<SysUserDSEntity> listEHR = new ArrayList<SysUserDSEntity>();
		List<SysUserDSEntity> list3A = new ArrayList<SysUserDSEntity>();
		//使用ehr系统数据库查询EHR系统的人员信息
		listEHR = sysUserDSService.getUserFromEHR(entity);
		
		int length = listEHR.size();
		//切换到数据源2（连接3A数据库）
		DynamicDataSource.setCurrentLookupKey(CustomerContextHolder.DATA_SOURCE_A);
		
		sysUserDSService.startOrEndUser(listEHR);
		
		
		
		
		//遍历查询结果，跟3A数据库中的人员信息对比，若有对应的结构，则修改为最新的，若没有则新增
		/*if(length>0){
			for(int i = 0; i < length; i++){
				entity.setName(listEHR.get(i).getA0101());
				entity.setOfficeId(listEHR.get(i).getE0122());
				entity.setLoginName(listEHR.get(i).getE0127());
				entity.setNo(listEHR.get(i).getE0127());
				entity.setCreateTime(listEHR.get(i).getCreateTime());
				entity.setKQID(listEHR.get(i).getC01UG());
				entity.setPassword(SystemService.entryptPassword("admin"));
				entity.setKqDetailId(listEHR.get(i).getA0100());
				list3A = sysUserDSService.getUserFrom3A(entity);
				if("0".equals(list3A.get(0).getNum())){
					sysUserDSService.addUser(entity);
				}else{
					sysUserDSService.updateUser(entity);
				}
			}
		}
		//根据当前日期查询人员信息，凡是更新日期在今日之前的人员删除
		List<SysUserDSEntity> listForDel = new ArrayList<SysUserDSEntity>();
		listForDel = sysUserDSService.getAllUserByUpdate(entity);
		if(listForDel.size()>0){
			for(int j = 0; j < listForDel.size(); j++){
				if("-1".equals(listForDel.get(j).getCompanyId())){
					System.out.println(0);
				}else{
					entity.setNo(listForDel.get(j).getNo());
					sysUserDSService.deleteUser(entity);
				}
				
			}
		}*/
		
		return listEHR;
	}

}