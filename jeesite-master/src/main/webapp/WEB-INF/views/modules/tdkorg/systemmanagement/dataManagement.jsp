<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据同步管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#startTime").val(getNowFormatDate());
		
	});
	var startOend = {
		
		startOrganization : function(type){
			var startTime = $("#startTime").val();
			var timeLag = $("#timeLag").val();
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {"startTime":startTime,"timeLag":timeLag,"type":type},
				url : "startOrganization",
				async : true,
				success : function(data) {
					console.log(data);
					
				},
				error : function(){
					alert("提交失败！");
				}
			});
		},
		
		end : {
			
		}
	};
	//获取当前时间
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate() + 1;
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " 00:00:00";
	            
	    return currentdate;
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/systemmanagement/systemManagement/dataManagement">数据同步管理</a></li>
	</ul>
	<div>
		<h5>数据信息同步（数据同步频率可配置）</h5><br><br><br>
		<!-- <span>（供参考）员工基本信息eHR人事系统同步到3A管理系统：</span> -->
		<div id="searchForm" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value=""/>
			<input id="pageSize" name="pageSize" type="hidden" value=""/>
			<ul class="ul-form" style="height: 70px">
				<li><label>开始时间：</label>
					<input id="startTime" type="text" maxlength="20" class="input-medium Wdate"
						value="<fmt:formatDate value="${testData.beginInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> 
						
				</li>
				<li><label>时间间隔：</label>
					<input id="timeLag"  maxlength="100" class="digits valid" value="24"/>(小时)
					<!-- <label for="digits" class="error">只能输入整数</label> -->
				</li>
				<li><label>启动状态：</label>
					<input id="btnStart" onclick="startOend.startOrganization('START');" class="btn btn-primary" type="button" value="启动"/>
					<input id="btnEnd" onclick="startOend.startOrganization('STOP');" class="btn btn-primary" type="button" value="停止"/>
				</li>
				<!-- <li class="clearfix"></li> -->
			</ul>
		</div>
	<%-- <div>
		<h5>系统之间数据同步关系如下：</h5>
		<img src="${pageContext.request.contextPath}/static/images/u19.jpg" style="width: 700px;height: 400px"></div>
	</div> --%>
</body>
</html>