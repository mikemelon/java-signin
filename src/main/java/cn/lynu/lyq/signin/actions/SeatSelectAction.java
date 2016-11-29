package cn.lynu.lyq.signin.actions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.lynu.lyq.signin.model.SeatAvailable;
import cn.lynu.lyq.signin.service.SeatAvailableService;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class SeatSelectAction extends ActionSupport{
	private static final long serialVersionUID = -6670625574248848427L;
	private static Logger logger = LoggerFactory.getLogger(SeatSelectAction.class);
	
	private int rowIndex;
	private int columnIndex;
	private int rowNum=6; //默认有6行8列座位可用（注意“列数”必须是偶数）
	private int colNum=8;
	private String isPost="0";
	
	private boolean[][] availableArray=new boolean[10][16]; //全部（最大）座位可用状态 列表
	
	@Resource private SeatAvailableService seatAvailableService;

	public boolean[][] getAvailableArray() {
		return availableArray;
	}

	public void setAvailableArray(boolean[][] availableArray) {
		this.availableArray = availableArray;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
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
	
	public String getIsPost() {
		return isPost;
	}

	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}

	private void getAvailableList(){
		List<SeatAvailable> seatList=seatAvailableService.findAllSeatAvailable();
		if(seatList!=null){
			for(SeatAvailable s:seatList){
				availableArray[s.getRow()-1][s.getCol()-1]=true;
			}
		}
	}
	
	public String deleteAllSeats(){
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath();
		rowNum=Integer.parseInt(Settings.load(Settings.SIGNIN_ROW_NUMBERS_KEY));
		colNum=Integer.parseInt(Settings.load(Settings.SIGNIN_COLUMN_NUMBERS_KEY));
		
		seatAvailableService.deleteAllAvailableSeat();
		getAvailableList();
		return "success";
	}
	
	public String setMultipleSeats(){
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath() ;	
		
		Settings.save(Settings.SIGNIN_ROW_NUMBERS_KEY, String.valueOf(rowNum));
		Settings.save(Settings.SIGNIN_COLUMN_NUMBERS_KEY, String.valueOf(colNum));
		
		seatAvailableService.updateSeatForMultipleRowAndColumn(rowNum,colNum);
		getAvailableList();
		return "success";
	}
	
	/*
	 *  功能：得到页面上用于循环行序号的整数列表
	 *  @params
	 *  int rowNum 当前设置的总行数
	 */
	public static List<Integer> getRowIndexList(int rowNum){
		ArrayList<Integer> indexList=new ArrayList<Integer>();
		for(int i=1; i<=rowNum; i++){
			indexList.add(i);
		}
		return indexList;
	}	
	
	/*
	 *  功能：得到页面上用于循环某行中所有列座位序号的整数列表
	 *  
	 *  @params
	 *  int colNum 当前设置的总列数（必须是偶数）
	 *  boolean leftOrRight 值true表示左半边，false表示右半边  (正方向是讲台上，学生在下）
	 *  
	 *  例如： colNum=8, leftOrRight=true时，返回数组{5,6,7,8}，
	 *  本系统每行固定最多座位16个，列表是{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}
	 */
	public static List<Integer> getColumnIndexList(int colNum, boolean leftOrRight){
		int startIndex;
		if( leftOrRight == true ){ startIndex=8-colNum/2+1; } else { startIndex=9; }
		ArrayList<Integer> indexList=new ArrayList<Integer>();
		for(int i=startIndex; i<startIndex+colNum/2; i++){
			indexList.add(i);
		}
		return indexList;
	}
	
	public String execute(){
		if(checkPermission()==false) return "check"; 
		if(isPost!=null && isPost.equals("0")){
			
			Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath();
			rowNum=Integer.parseInt(Settings.load(Settings.SIGNIN_ROW_NUMBERS_KEY));
			colNum=Integer.parseInt(Settings.load(Settings.SIGNIN_COLUMN_NUMBERS_KEY));
		
		}else if(isPost!=null && isPost.equals("1")){ // 更改单个座位
			logger.info("更改座位("+rowIndex+","+columnIndex+")中");
			if(rowIndex!=0 && columnIndex!=0){
				seatAvailableService.updateSeatForRowAndColumn(rowIndex,columnIndex);
			}
		}
		getAvailableList();
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
