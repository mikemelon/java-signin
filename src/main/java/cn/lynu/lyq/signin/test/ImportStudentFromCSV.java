package cn.lynu.lyq.signin.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.lynu.lyq.signin.db.HibernateSessionFactoryOld;
import cn.lynu.lyq.signin.model.Student;

public class ImportStudentFromCSV {

	public static void main(String[] args) throws Exception{
//		String realPath=new ImportTaskFromCSV().getClass().getResource("/").getPath();
//		String realPath=System.getProperty("user.dir")+"\\src\\";
		String realPath=new File("").getAbsolutePath()+"\\src\\main\\resources\\import\\";
		BufferedReader bfr=new BufferedReader(new FileReader(new File(realPath+"student3.csv")));
		
		Session session = HibernateSessionFactoryOld.getSession();
		Transaction trans=session.beginTransaction();
		int cnt = 0;
		String line=null;
		while((line=bfr.readLine())!=null){
			if(line.contains("序号")) continue; //第一行是说明，忽略
			String[] splittedStr=line.split(",");
			System.out.println(splittedStr.length);
			// splittedStr[0]是 序号，忽略
			Student stu = new Student(splittedStr[1],splittedStr[2],"2017电商3班",false);
//			Task task=new Task(null,
//					splittedStr[1],
//					splittedStr[2],
//					splittedStr[3],
//					false,null,
//					splittedStr[4]);
//			System.out.println("class="+splittedStr[4]);
//			System.out.println(task);
			session.save(stu);	
			cnt++;
		}
		trans.commit();
		System.out.println(cnt);
		session.close();
		bfr.close();
	}

}
