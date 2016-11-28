package cn.lynu.lyq.signin.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.AbsentRequestService;
import cn.lynu.lyq.signin.service.SignRecordService;
import cn.lynu.lyq.signin.service.StudentService;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class ManageAction extends ActionSupport {
	private static final long serialVersionUID = 9220420392643905716L;
	
	private List<Student> offlineStudentList;
	private List<String> classNameList;
	private String currentClassName;
	private String currentLocation;
	private int mycommand;
	private Date signDate;
	private String stu_id;
	private String isPost="0";
	@Resource private StudentService studentService;
	@Resource private AbsentRequestService absentRequestService;
	@Resource private SignRecordService signRecordService;

	public List<Student> getOfflineStudentList() {
		return offlineStudentList;
	}

	public void setOfflineStudentList(List<Student> offlineStudentList) {
		this.offlineStudentList = offlineStudentList;
	}

	public List<String> getClassNameList() {
		return classNameList;
	}

	public void setClassNameList(List<String> classNameList) {
		this.classNameList = classNameList;
	}

	public String getCurrentClassName() {
		return currentClassName;
	}

	public void setCurrentClassName(String currentClassName) {
		this.currentClassName = currentClassName;
	}
	
	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getMycommand() {
		return mycommand;
	}

	public void setMycommand(int mycommand) {
		this.mycommand = mycommand;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getStu_id() {
		return stu_id;
	}

	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}

	public String getIsPost() {
		return isPost;
	}

	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}
	
	private int[] getRowAndColumnNumsForLocation(String locationStr){
		int[] rowAndColumnNums=new int[2];
		if(locationStr!=null && locationStr.equals("1号楼7号机房")){
			rowAndColumnNums[0]=7;  //行
			rowAndColumnNums[1]=10; //列
		}else if(locationStr!=null && locationStr.equals("1号楼8号机房")){
			rowAndColumnNums[0]=6;
			rowAndColumnNums[1]=8;
		}else if(locationStr!=null && locationStr.equals("1号楼9号机房")){
			rowAndColumnNums[0]=5;
			rowAndColumnNums[1]=10;
		}else if(locationStr!=null && locationStr.equals("1号楼1号机房")){
			rowAndColumnNums[0]=5;
			rowAndColumnNums[1]=10;
		}else if(locationStr!=null && locationStr.equals("G5教学楼110机房")){
			rowAndColumnNums[0]=5;
			rowAndColumnNums[1]=12;
		}
		return rowAndColumnNums;
	}
	
	@Override
	public String execute() throws Exception {
		if(checkPermission()==false) return "check";
		
		classNameList = studentService.findDistinctClassName();
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath() ;
		
		if(isPost!=null && isPost.equals("0")){
			String loadedClassName = Settings.load(Settings.CURRENT_CLASS_KEY);
			String loadedLocation = Settings.load(Settings.CURRENT_LOCATION_KEY);
			
			currentClassName=loadedClassName;
			currentLocation=loadedLocation;
//			if(signDate==null) signDate=new Date();
			// 从settings.xml中读取日期，test用 2014-11-06 BEGIN
			String dateStr=Settings.load(Settings.CURRENT_DATE_KEY);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			signDate = sdf1.parse(dateStr);
			// 从settings.xml中读取日期，test用 2014-11-06 END			
		}else if(isPost!=null && isPost.equals("1")){
			
		}
		
		if(mycommand==1){  //设置日期，并根据日期更新当前学生状态
			System.out.println("============update online====BEGIN========");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(signDate);
			signRecordService.updateStudentOnlineByRegDate(dateStr,currentClassName);
			Settings.save(Settings.CURRENT_DATE_KEY, dateStr);
			System.out.println("============update online====END==========");
		}else if(mycommand==2){ //含班级和上课地点（教室），并根据教室情况自动调整总行列数
			
			Settings.save(Settings.CURRENT_CLASS_KEY, currentClassName);
			Settings.save(Settings.CURRENT_LOCATION_KEY, currentLocation);
			
			int[] rowsAndColumns = getRowAndColumnNumsForLocation(currentLocation);
			Settings.save(Settings.SIGNIN_ROW_NUMBERS_KEY, String.valueOf(rowsAndColumns[0]));
			Settings.save(Settings.SIGNIN_COLUMN_NUMBERS_KEY, String.valueOf(rowsAndColumns[1]));
			
			System.out.println("切换到班级：" + currentClassName + ", 上课地点：" + currentLocation +
								"此教室有" + rowsAndColumns[0] + "行" + rowsAndColumns[1] + "列");
			
		}else if(mycommand==3){ //请假
			System.out.println("请假，stu_id="+stu_id);
			absentRequestService.addAbsentReqeust(stu_id,new Date());
		}
		
		offlineStudentList = studentService.findByOnline(Boolean.valueOf(false),currentClassName);
		return "success";
	}

	public boolean checkPermission(){
		String ip =ServletActionContext.getRequest().getRemoteAddr();
		
		//2014-11-10 add 根据“上课地点”判断使用哪个ip表文件
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath() ;
		String currentLocation = Settings.load(Settings.CURRENT_LOCATION_KEY);
//		String ipTableFileName="1_7#_ip.properties";
		String teacherComputerIP="172.19.12.235";
		if(currentLocation!=null && currentLocation.equals("1号楼7号机房")){
//			ipTableFileName="1_7#_ip.properties";
			teacherComputerIP="172.19.12.235";
		}else if(currentLocation!=null && currentLocation.equals("1号楼8号机房")){
//			ipTableFileName="1_8#_ip.properties";
			teacherComputerIP="172.19.13.93";
		}else if(currentLocation!=null && currentLocation.equals("1号楼9号机房")){
//			ipTableFileName="1_9#_ip.properties";
			teacherComputerIP="172.19.13.36";
		}
		
		if(ip==null || !ip.equals("localhost") && !ip.equals("127.0.0.1")&& !ip.equals(teacherComputerIP)){
			return false;
		}else{
			return true;
		}
	}	
	
}
