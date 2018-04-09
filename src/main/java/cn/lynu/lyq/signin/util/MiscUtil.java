package cn.lynu.lyq.signin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MiscUtil {
	/**
	 * 获得机房列表
	 * @return
	 * @throws IOException
	 */
	public static List<HashMap<String,String>> getLocationList() throws IOException{
		
		List<HashMap<String,String>> locationList = new ArrayList<HashMap<String,String>>();
		String ipTableFilePath=MiscUtil.class.getResource("/").getPath()+"config/";
		
		File path = new File(ipTableFilePath);
		if (path.exists() && path.isDirectory()){
			File[] locFiles = path.listFiles();
			for(File f: locFiles){
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
				String firstLine = br.readLine();
//				System.out.println(firstLine);
				String locDetailStr = "未知机房";//例如：1号楼8号机房
				if(firstLine.startsWith("﻿#")){
//					System.out.println(firstLine.substring(2));
					locDetailStr = firstLine.substring(2).trim();//从第2个字符取，是因为有的UTF-8文件有BOM，会被认成一个字符
				}
				br.close();
				HashMap<String,String> locationItem = new HashMap<String,String>();
				locationItem.put("key", locDetailStr);
				locationItem.put("value", locDetailStr);
				locationList.add(locationItem);
			}
		}
		
		return locationList;
		
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getLocationList());

	}

}
