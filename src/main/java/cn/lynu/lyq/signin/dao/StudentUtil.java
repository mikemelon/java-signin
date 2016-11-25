package cn.lynu.lyq.signin.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.SignRecord;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.util.DateUtil;
import cn.lynu.lyq.signin.util.Settings;

public class StudentUtil {
	public static Student validateStudent(String regNo, String name){
		Session s = HibernateSessionFactory.getSession();
		Query query = s.createQuery("from Student where name=? and regNo=?");
		query.setString(0, name);
		query.setString(1, regNo);
		@SuppressWarnings("unchecked")
		List<Student> list  = query.list();
		
		if(list==null || list.size()==0){
			System.out.println("validateStudent:没有找到学生");
			return null;
		}else{
			Student stu1=list.get(0);
			//System.out.println(stu1.getName());
			return stu1;
		}
	}
	
	public static List<Student> findByOnline(Boolean online, String className){
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		String queryString = null;
		if(online.booleanValue()==false){
			queryString= "from Student where (online=? or online is null) and className=?";
		}else{
			queryString="from Student where online=? and className=?";
		}
		Query query = s.createQuery(queryString);
		query.setBoolean(0, online.booleanValue());
		query.setString(1, className);
		
		@SuppressWarnings("unchecked")
		List<Student> list  = (List<Student>)query.list();
		if(list==null || list.size()==0){
			System.out.println("validateStudent:没有找到学生");
			return null;
		}else{
			return list;
		}		
	}
	
	public static Student getIPForCurDate(String ip){
		Session s = HibernateSessionFactory.getSession();
		Query query = s.createQuery("from SignRecord where ip=? and regDate between :aa and :bb");
		query.setString(0, ip);
		String dateStr = Settings.load(Settings.CURRENT_DATE_KEY);
		
		Date[] dates = DateUtil.getBetweenDates(dateStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );		
		
		@SuppressWarnings("unchecked")
		List<SignRecord> list  = (List<SignRecord>)query.list();
		if(list==null || list.size()==0){
			return null;
		}else{
			SignRecord signRecord1= list.get(0);
			Student stuWithRepeatIP= signRecord1.getStudent();
			return stuWithRepeatIP;
		}		
	}
	
	public static List<String> findDistinctClassName(){
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		Query query = s.createQuery("select distinct s.className from Student s");
		
		@SuppressWarnings("unchecked")
		List<String> list  = (List<String>)query.list();
		if(list==null || list.size()==0){
			System.out.println("validateStudent:没有找到班级");
			return null;
		}else{
			return list;
		}		
	}	
	
	public static boolean updateStudent(Student stuToUpdate, String ipAddress, int rowIndex, int columnIndex){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		try{
		
			SignRecord signRecord = new SignRecord();
			signRecord.setRegDate(new Date());
			signRecord.setStudent(stuToUpdate);
			signRecord.setIp(ipAddress);
			signRecord.setRowIndex(rowIndex);
			signRecord.setColumnIndex(columnIndex);
			
			s.update(stuToUpdate);
			s.save(signRecord);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;
	}
	
	public static boolean updateStudentOnline(String className, boolean online){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		try{
			Query query = s.createQuery("update Student set online=? where className=?");
			query.setBoolean(0, online);
			query.setString(1, className);
			query.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;
	}	

	public static List<Student> getAllStudent(String className){
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		Query query = s.createQuery("from Student where className=?");
		query.setString(0, className);
		
		@SuppressWarnings("unchecked")
		List<Student> list  = (List<Student>)query.list();
		if(list==null || list.size()==0){
			System.out.println("getAllStudent:没有找到学生");
			return null;
		}else{
			return list;
		}
		
	}

	public static List<Object[]> getStatsList(String currentClassName) {
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		Query query = s.createQuery("select s.name, s.id, s.regNo, s.className,r.regDate " 
                  +"from SignRecord  r inner join r.student s where s.className=?");
		query.setString(0, currentClassName);
		@SuppressWarnings("unchecked")
		List<Object[]> list  = (List<Object[]>)query.list();
		if(list==null || list.size()==0){
			System.out.println("getStatsList:没有找到学生");
			return null;
		}else{
			return list;
		}
	}	
	
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	public static boolean getStatsListByRegNoAndRegDate(String currentClassName, String regNo, String dateStr) {
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		Query query = s.createQuery("select count(*) " 
                  +"from SignRecord  r inner join r.student s where s.className=? and s.regNo=? and r.regDate between :aa and :bb");
		query.setString(0, currentClassName);
		query.setString(1, regNo);
		Date[] dates = DateUtil.getBetweenDates(dateStr);	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		
//		Long count  = (Long)query.uniqueResult();
		Object count=query.uniqueResult();
		int cnt=0;
		if(count instanceof Long){
			cnt=((Long)count).intValue();
		}else{
			cnt =((Integer)count).intValue();
		}
//		System.out.println("getStatsListByRegNoAndRegDate-->cnt=["+cnt+"]");
		if(cnt==0){
			return false;
		}else{
			return true;
		}
	}	
	
}
