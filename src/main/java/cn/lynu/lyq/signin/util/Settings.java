package cn.lynu.lyq.signin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Settings {
	private static Logger logger = LoggerFactory.getLogger(Settings.class);
	
	public static final String CURRENT_CLASS_KEY="当前班级";
	public static final String CURRENT_DATE_KEY="当前日期";
	public static final String CURRENT_LOCATION_KEY="上课地点";
	public static final String UPLOAD_DIR_KEY="上传目录";
	public static final String SIGNIN_ROW_NUMBERS_KEY="sinin.row.numbers";
	public static final String SIGNIN_COLUMN_NUMBERS_KEY="signin.column.numbers";
	
	public static final String SETTINGS_FILE_NAME="settings.xml"; //不可更改
	
	public static String PROJECT_REAL_PATH = ""; //使用此类前必须先赋值
	
	public static void init(){
		if(!PROJECT_REAL_PATH.equals("")){
			Properties prop = new Properties();
			try{
				File propFile = new File(PROJECT_REAL_PATH + SETTINGS_FILE_NAME);
				if(!propFile.exists()) {
					logger.info("Settings初始化");
					propFile.createNewFile();
					FileOutputStream fos = new FileOutputStream(propFile);
					//设置默认值				
					prop.put(CURRENT_CLASS_KEY, "2010网络工程");
					prop.put(CURRENT_DATE_KEY, "2013-03-28");
					prop.put(CURRENT_LOCATION_KEY, "1号楼7号机房");
					prop.put(UPLOAD_DIR_KEY, "d:/uploads/");
					prop.put(SIGNIN_ROW_NUMBERS_KEY, 6);
					prop.put(SIGNIN_COLUMN_NUMBERS_KEY, 8);
					prop.storeToXML(fos, null);
				}
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
	
	public static void save(String key, String value) {
		init();
		Properties prop = new Properties();
		try{
			File propFile = new File(PROJECT_REAL_PATH + SETTINGS_FILE_NAME);
			if(!propFile.exists()) propFile.createNewFile();
			FileInputStream fis = new FileInputStream(propFile);
			prop.loadFromXML(fis);
			prop.put(key, value);
			prop.storeToXML(new FileOutputStream(propFile), null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String load(String key){
		init();
		Properties prop = new Properties();
		try {
			File propFile = new File(PROJECT_REAL_PATH + SETTINGS_FILE_NAME);
			if(!propFile.exists()) propFile.createNewFile();
//			logger.info("真实路径"+propFile.getCanonicalPath());
			FileInputStream fis = new FileInputStream(propFile);
			
			prop.loadFromXML(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String value =prop.getProperty(key);
		if(value!=null){
			return value;
		}else{
			return "";
		}
	}
	
	public static void main(String[] args){
		PROJECT_REAL_PATH ="";
		String value = load("test1key");
		System.out.println("value="+value);
	}
}
