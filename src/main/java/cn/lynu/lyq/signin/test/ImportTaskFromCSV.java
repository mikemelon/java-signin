package cn.lynu.lyq.signin.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.Task;

public class ImportTaskFromCSV {

	public static void main(String[] args) throws Exception{
//		String realPath=new ImportTaskFromCSV().getClass().getResource("/").getPath();
//		String realPath=System.getProperty("user.dir")+"\\src\\";
		String realPath=new File("").getAbsolutePath()+"\\src\\main\\resources\\import\\";
		BufferedReader bfr=new BufferedReader(new FileReader(new File(realPath+"task1.csv")));
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trans=session.beginTransaction();
		
		String line=null;
		while((line=bfr.readLine())!=null){
			if(line.startsWith("序号")) continue; //第一行是说明，忽略
			String[] splittedStr=line.split(",");
			// splittedStr[0]是 序号，忽略
			Task task=new Task(null,
					splittedStr[1],
					splittedStr[2],
					splittedStr[3],
					false,null,
					splittedStr[4]);
//			System.out.println("class="+splittedStr[4]);
//			System.out.println(task);
			session.save(task);		
		}
		trans.commit();
		session.close();
		bfr.close();
	}

}
