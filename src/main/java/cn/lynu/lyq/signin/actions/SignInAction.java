package cn.lynu.lyq.signin.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.lynu.lyq.signin.model.SeatAvailable;
import cn.lynu.lyq.signin.model.SignRecord;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.AbsentRequestService;
import cn.lynu.lyq.signin.service.SeatAvailableService;
import cn.lynu.lyq.signin.service.SignRecordService;
import cn.lynu.lyq.signin.service.StudentService;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class SignInAction extends ActionSupport{
	private static final long serialVersionUID = 4352261317606336838L;
	private static Logger logger = LoggerFactory.getLogger(SignInAction.class);
	
	private Integer rowIndex; //提交在线学生的行号（1开始） 
	private Integer columnIndex; //提交在线学生的列号（1开始）
	private String name1; //提交在线学生的姓名
	private String studentNo1;//提交在线学生的学号
	private static String[][] name=new String[10][16];	//在线学生姓名 列表
	private static String[][] studentNo=new String[10][16]; //在线学生学号 列表
	private boolean[][] availableArray=new boolean[10][16]; //座位可用状态 列表
	private static boolean[][] online=new boolean[10][16]; //在线状态 列表
	private int rowNum=6; //系统设置的行数，默认有6行8列座位可用（注意“列数”必须是偶数）
	private int colNum=8; //系统设置的列数
	private boolean inverseView=false; //显示时是否颠倒(正方向是讲台上，学生在下）
	
	private String errorMsg;
	private List<String> nameList; //当前班级 所有学生姓名
	private List<String> regNoList;	//当前班级 所有学生学号
	private String isPost="0";
	private List<Student> absentRequestStudentList;
	private String currentClassName ="2009网络工程";
	
	@Resource private StudentService studentService;
	@Resource private AbsentRequestService absentRequestService;
	@Resource private SeatAvailableService seatAvailableService;
	@Resource private SignRecordService signRecordService;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getStudentNo1() {
		return studentNo1;
	}

	public void setStudentNo1(String studentNo1) {
		this.studentNo1 = studentNo1;
	}
	
	public boolean[][] getAvailableArray() {
		return availableArray;
	}

	public void setAvailableArray(boolean[][] availableArray) {
		this.availableArray = availableArray;
	}
	
	public boolean[][] getOnline() {
		return online;
	}

	public void setOnline(boolean[][] online) {
		SignInAction.online = online;
	}

	public static String[][] getName() {
		return name;
	}

	public static void setName(String[][] name) {
		SignInAction.name = name;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public boolean isInverseView() {
		return inverseView;
	}

	public void setInverseView(boolean inverseView) {
		this.inverseView = inverseView;
	}

	public static String[][] getStudentNo() {
		return studentNo;
	}

	public static void setStudentNo(String[][] studentNo) {
		SignInAction.studentNo = studentNo;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}
	
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	public Integer getColumnIndex() {
		return columnIndex;
	}
	
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public List<String> getRegNoList() {
		return regNoList;
	}

	public void setRegNoList(List<String> regNoList) {
		this.regNoList = regNoList;
	}

	public String getIsPost() {
		return isPost;
	}

	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}

	public List<Student> getAbsentRequestStudentList() {
		return absentRequestStudentList;
	}

	public void setAbsentRequestStudentList(List<Student> absentRequestStudentList) {
		this.absentRequestStudentList = absentRequestStudentList;
	}

	public void loadStudentList() {
		List<Student> studentList = studentService.getAllStudent(currentClassName);
		nameList = new ArrayList<String>();
		regNoList = new ArrayList<String>();
		nameList.add("---选择姓名---");
		regNoList.add("---选择学号---");
		
		for(int i=0;i<10;i++){
			for(int j=0;j<16;j++){
				online[i][j]=false;
				name[i][j]="";
				studentNo[i][j]="";				
			}
		}
		
		for(Student stu:studentList){
			nameList.add(stu.getName());
			regNoList.add(stu.getRegNo());
			// 设置在线学生所在位置的姓名、学号、在线状态
			if(stu.isOnline()!=null && stu.isOnline()==true){
//				logger.info("在线中:"+stu.getName()+"/"+stu.isOnline());
				SignRecord signRecord = signRecordService.getSignRecordByStuId(stu.getId());
				if(signRecord!=null){
					int rowOnline1=signRecord.getRowIndex();
					int columnOnline1 = signRecord.getColumnIndex();
					online[rowOnline1-1][columnOnline1-1]=true;
					name[rowOnline1-1][columnOnline1-1]=stu.getName();
					studentNo[rowOnline1-1][columnOnline1-1]=stu.getRegNo();	
				}
			}
		}		
	}
	
	private void getAvailableSeatList(){
		List<SeatAvailable> seatList=seatAvailableService.findAllSeatAvailable();
		if(seatList!=null){
			for(SeatAvailable s:seatList){
				availableArray[s.getRow()-1][s.getCol()-1]=true;
			}
		}
		//2014-10-26 add 根据IP地址，设置该座位是否可以登录
		String ip =ServletActionContext.getRequest().getRemoteAddr();
//		ip="172.19.12.219";//test, 7#机房 第5排右边第5座
		
		//2014-11-10 add 根据“上课地点”判断使用哪个ip表文件
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath() ;
		String currentLocation = Settings.load(Settings.CURRENT_LOCATION_KEY);
		String ipTableFileName="1_7#_ip.properties";
		if(currentLocation!=null && currentLocation.equals("1号楼7号机房")){
			ipTableFileName="1_7#_ip.properties";
		}else if(currentLocation!=null && currentLocation.equals("1号楼8号机房")){
			ipTableFileName="1_8#_ip.properties";
		}else if(currentLocation!=null && currentLocation.equals("1号楼9号机房")){
			ipTableFileName="1_9#_ip.properties";
		}else if(currentLocation!=null && currentLocation.equals("1号楼1号机房")){
			ipTableFileName="1_1#_ip.properties";
		}else if(currentLocation!=null && currentLocation.equals("G5教学楼110机房")){
			ipTableFileName="G5_110_ip.properties";
		}
		String ipTableFilePathName=getClass().getResource("/").getPath()+"config/"+ipTableFileName; 
//		2014-11-10 add 根据“上课地点”判断使用哪个ip表文件 end
		
		Properties prop=new Properties();
		try{
			prop.load(new FileInputStream(ipTableFilePathName));
			String seatPair=(String)prop.get(ip.trim());
			logger.info("登录IP是："+ip + ",在当前IP座位对照表（"+ ipTableFileName +
					 "）中查询的结果为："+seatPair);
			int ipRow=0,ipColumn=0;
			if(seatPair!=null && "".equals(seatPair)==false){
				seatPair = seatPair.substring(seatPair.indexOf('(')+1,seatPair.indexOf(')'));
				String[] rowAndColumn=seatPair.split(",");
				 ipRow=Integer.parseInt(rowAndColumn[0]);
				 ipColumn=Integer.parseInt(rowAndColumn[1]);	
			}
			//2014-11-3 add 同时判断是否该座位允许登录（需先设置允许登录的座位范围）
//			availableArray[ipRow-1][ipColumn-1]=true;
			if(seatList!=null){
				for(SeatAvailable s:seatList){
					if((s.getRow()-1)==(ipRow-1) && (ipColumn-1)==(s.getCol()-1)){
						availableArray[ipRow-1][ipColumn-1]=true;
					}else{
						availableArray[s.getRow()-1][s.getCol()-1]=false;
					}
				}
			}
			//2014-11-3 add end
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		//2014-10-26 add end
	}
	
	private void getAbsentRequestList(String className,String dateStr){
		absentRequestStudentList = absentRequestService.getAbsentRequestStudentsForClassAndDate(className,dateStr);
	}
	
	public String execute(){
//		ServletContext servletContext = ServletActionContext.getServletContext();
//		String realPath = servletContext.getRealPath("/");
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath() ;
		currentClassName = Settings.load(Settings.CURRENT_CLASS_KEY);
		String currentDate=Settings.load(Settings.CURRENT_DATE_KEY);
		rowNum=Integer.parseInt(Settings.load(Settings.SIGNIN_ROW_NUMBERS_KEY));
		colNum=Integer.parseInt(Settings.load(Settings.SIGNIN_COLUMN_NUMBERS_KEY));
		
		getAvailableSeatList();
		loadStudentList();
		getAbsentRequestList(currentClassName,currentDate);
		
		String ip =ServletActionContext.getRequest().getRemoteAddr();
		
		if(isPost==null || isPost.equals("0")){ //非通过登录场合（直接刷新页面）
			
			Student stuLogin=signRecordService.getStudentByRegDateAndIpAndClassName(currentDate,ip,currentClassName);
			if(stuLogin!=null){
				studentNo1 = stuLogin.getRegNo();
				name1 = stuLogin.getName();
				logger.info("从数据库中判断，当前登录的用户为：studentNo1=" + studentNo1 + ",  name1=" + name1 );
			}
		}else if(isPost!=null && isPost.equals("1")){ //登录场合
			
			if(name1==null || name1.equals("")){
				errorMsg="必须输入姓名!";
				return "success";
			}
			if(studentNo1==null || studentNo1.equals("")){
				errorMsg="必须输入学号!";
				return "success";
			}
			Student stu1 = studentService.validateStudent(studentNo1,  name1);
			if(stu1== null ){
				errorMsg="姓名学号输入错误，你可能不是本班学生";
				logger.info("姓名学号输入错误，你可能不是本班学生, regNo={}, name={}",studentNo1,name1);
				return "success";
			}
			if(stu1.isOnline()!=null && stu1.isOnline()==true){
				errorMsg="该学生已经登陆，如有问题请联系管理员";
				logger.info("该学生 - {}  已经登陆，如有问题请联系管理员，(原因可能是student表的登录状态true)",stu1.getName());
				return "success";			
			}
			//判断当日的重复IP登陆
			Student stu_tmp = studentService.getIPForCurDate(ip);
			if(stu_tmp!=null){
				logger.info(stu1.getName()+"--->重复IP登陆");
				errorMsg="已经有" + stu_tmp.getName() + 
						 "（学号:"+stu_tmp.getRegNo()+")用这个IP登陆，疑似代人签到，如有问题请联系管理员";
				return "success";					
			}
			
			online[rowIndex-1][columnIndex-1]=true;
			name[rowIndex-1][columnIndex-1]=name1;
			studentNo[rowIndex-1][columnIndex-1]=studentNo1;		
			logger.info("-----------------------------------");
			logger.info("ip="+ip+"/rowIndex="+rowIndex+"/columnIndex="+columnIndex);
			logger.info("name["+(rowIndex-1)+"]"+"["+(columnIndex-1)+"]="+name[rowIndex-1][columnIndex-1]);
			logger.info("studentNo["+(rowIndex-1)+"]"+"["+(columnIndex-1)+"]="+studentNo[rowIndex-1][columnIndex-1]);
			logger.info("online["+(rowIndex-1)+"]"+"["+(columnIndex-1)+"]="+online[rowIndex-1][columnIndex-1]);
			logger.info("-----------------------------------");
			
			stu1.setClassName(currentClassName);
			stu1.setOnline(true);
			studentService.updateStudent(stu1, ip, rowIndex, columnIndex);
		}
		
		ActionContext ctx=ActionContext.getContext();
		ctx.getSession().put("CURRENT_USER_REGID", studentNo1);
		ctx.getSession().put("CURRENT_USER_NAME", name1);
		
		return "success";
	}
	
	public String reverseView(){
//		ServletContext servletContext = ServletActionContext.getServletContext();
//		String realPath = servletContext.getRealPath("/");
//		Settings.PROJECT_REAL_PATH = realPath ;
//		rowNum=Integer.parseInt(Settings.load(Settings.SIGNIN_ROW_NUMBERS_KEY));
//		colNum=Integer.parseInt(Settings.load(Settings.SIGNIN_COLUMN_NUMBERS_KEY));		
		
		inverseView=!inverseView;
//		logger.info("method reverseView::: rowNum="+rowNum+"/ colNum="+colNum+"/ inverseView="+inverseView);
		return "success";
	}
	
	/*
	 *  功能：得到页面上用于循环行序号的整数列表
	 *  @params
	 *  int rowNum 当前设置的总行数
	 *  boolean inverse =true表示反方向
	 *                  =false表示正方向 (正方向是讲台上，学生在下）
	 */
	public static List<Integer> getRowIndexList(int rowNum, boolean inverse){
		ArrayList<Integer> indexList=new ArrayList<Integer>();
		if(inverse==false){
			for(int i=1; i<=rowNum; i++){
				indexList.add(i);
			}
		}else{
			for(int i=rowNum; i>=1; i--){
				indexList.add(i);
			}			
		}
//		logger.info("row indexList="+indexList.toString());
		return indexList;
	}	
	
	/*
	 *  功能：得到页面上用于循环某行中所有列座位序号的整数列表
	 *  
	 *  @params
	 *  int colNum 当前设置的总列数（必须是偶数）
	 *  boolean leftOrRight 值true表示左半边，false表示右半边 (正方向是讲台上，学生在下）
	 *  boolean inverse =true表示反方向
	 *                  =false表示正方向  (正方向是讲台上，学生在下）
	 *                  
	 *  例如： colNum=8, leftOrRight=true时，返回数组{5,6,7,8}（正方向），或{8,7,6,5}（反方向）
	 *  本系统每行固定最多座位16个，列表是{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}
	 */
	public static List<Integer> getColumnIndexList(int colNum, boolean leftOrRight, boolean inverse){
		int startIndex;
		if( inverse==true ) leftOrRight=!leftOrRight;//如果是反方向，则左右也要交换
		if( leftOrRight == true ){ startIndex=8-colNum/2+1; } else { startIndex=9; }
		ArrayList<Integer> indexList=new ArrayList<Integer>();
		if(inverse==false){
			for(int i=startIndex; i<startIndex+colNum/2; i++){
				indexList.add(i);
			}
		}else{
			for(int i=startIndex+colNum/2-1; i>=startIndex; i--){
				indexList.add(i);
			}
		}
//		logger.info("column indexList="+indexList.toString());
		return indexList;
	}	
}
