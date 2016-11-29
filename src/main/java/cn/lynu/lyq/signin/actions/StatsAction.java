package cn.lynu.lyq.signin.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.AbsentRequestService;
import cn.lynu.lyq.signin.service.StudentService;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class StatsAction extends ActionSupport{
	private static final long serialVersionUID = -1011953067569813358L;
	private static Logger logger = LoggerFactory.getLogger(StatsAction.class);
	
	private List<String> classNameList;
	private List<String> dateList;
	private List<Map<String,String>> studentList; 
	private String currentClassName="2009网络工程";
	private String isPost="0";
	
	@Resource private StudentService studentService;
	@Resource private AbsentRequestService absentRequestService;
	
	public String getCurrentClassName() {
		return currentClassName;
	}

	public void setCurrentClassName(String currentClassName) {
		this.currentClassName = currentClassName;
	}

	public List<String> getClassNameList() {
		return classNameList;
	}

	public void setClassNameList(List<String> classNameList) {
		this.classNameList = classNameList;
	}

	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public List<Map<String,String>> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Map<String,String>> studentList) {
		this.studentList = studentList;
	}
	
	public String getIsPost() {
		return isPost;
	}

	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}

	public String execute(){
		classNameList = studentService.findDistinctClassName();
		if(isPost!=null && isPost.equals("0")){
			currentClassName = Settings.load(Settings.CURRENT_CLASS_KEY);
		}else if(isPost!=null && isPost.equals("1")){
			
		}
		
		List<Object[]> studentListRaw = studentService.getStatsList(currentClassName);//join查询得到的是每条数据是Object[]
//		logger.info("aaa===="+studentList.get(0));
//		Object[] oo=(Object[])studentList.get(0);
//		logger.info("bbb===="+oo[4]);
		
		//列出查询的不重复的日期（精确到天）
		List<String> distinctDateList = new ArrayList<String>();
		for(int i=0; studentListRaw!=null && i<studentListRaw.size(); i++){
			Object[] studentItem =/*(Object[])*/studentListRaw.get(i);
			Date stuRegDate=(Date)studentItem[4];
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String strDate=sdf.format(stuRegDate);
			boolean containsDate=false;
			for(int j=0; j<distinctDateList.size(); j++){
				if(distinctDateList.get(j).equals(strDate))
					containsDate=true;
			}
			if(!containsDate) distinctDateList.add(strDate);
		}
		logger.debug("distinctDateList:"+distinctDateList.toString());
		Collections.sort(distinctDateList);
		dateList=distinctDateList;
		
		//生成统计列表
		List<Student> allStudentList = studentService.getAllStudent(currentClassName);
		List<Map<String,String>> list2=new ArrayList<Map<String,String>>();
		
		for(Student stu:allStudentList){
			
			String regNo=stu.getRegNo();
			Map<String,String> stuItem=new HashMap<String,String>();
			stuItem.put("regNo", regNo);
			stuItem.put("name", stu.getName());
			
			for(String dateItem:distinctDateList){
				boolean isReg = studentService.getStatsListByRegNoAndRegDate(currentClassName,regNo,dateItem);
				stuItem.put(dateItem, String.valueOf(isReg));
//				请假学生
				if(false==isReg){
					boolean isRequestForAbsent=absentRequestService.getRequestForStudentAndDate(regNo,dateItem);
					if(isRequestForAbsent){
						stuItem.put(dateItem, "REQUEST_FOR_ABSENT");
					}
				}
			}
			list2.add(stuItem);
		}
		studentList = list2;
		return "success";
	}
}
