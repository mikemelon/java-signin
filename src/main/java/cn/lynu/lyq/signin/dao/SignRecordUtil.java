package cn.lynu.lyq.signin.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class SignRecordUtil {

	public static SignRecord getSignRecordByStuId(int studentId){
		Session s = HibernateSessionFactory.getSession();
		Query query = s.createQuery("from SignRecord where stu_id=? and regDate between :aa and :bb");
		query.setInteger(0, studentId);

		String dateStr = Settings.load(Settings.CURRENT_DATE_KEY);
		Date[] dates = DateUtil.getBetweenDates(dateStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );		
		
		@SuppressWarnings("unchecked")
		List<SignRecord> list  = (List<SignRecord>)query.list();
		
		if(list==null || list.size()==0){
			//System.out.println("getSignRecordByStuId:没有找到学生");
			return null;
		}else{
			return (SignRecord)(list.get(0));
		}
		
	}	

	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	public static List<SignRecord> getSignRecordByRegDate(String dateStr){
		Date[] dates = DateUtil.getBetweenDates(dateStr);

		Session s = HibernateSessionFactory.getSession();
		Query query = s.createQuery("from SignRecord where regDate between :aa and :bb");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		
		@SuppressWarnings("unchecked")
		List<SignRecord> list  = (List<SignRecord>)query.list();
		
		if(list==null || list.size()==0){
			System.out.println("getSignRecordByRegDate:没有找到学生");
			return null;
		}else{
			return list;
		}
	}
	
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	public static boolean updateStudentOnlineByRegDate(String dateStr, String className){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		
		// Step 1: 从SignRecord表中查出指定日期的记录，并获取学生ids
		Date[] dates = DateUtil.getBetweenDates(dateStr);	
		Query query = s.createQuery("from SignRecord where regDate between :aa and :bb");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		
		@SuppressWarnings("unchecked")
		List<SignRecord> signRecordlist  = (List<SignRecord>)query.list();
		
		List<Integer> signStuIdList = new ArrayList<>();
		for(SignRecord signRecord:signRecordlist){
			signStuIdList.add(signRecord.getStudent().getId());
		}
		
		// Step 2: 寻找Student表中所有该班级的学生
		Query query2 = s.createQuery("from Student where className=?");
		query2.setString(0, className);
		@SuppressWarnings("unchecked")
		List<Student> stuList = (List<Student>)query2.list();
		
		// Step 3: 根据SignRecord表中获取的ids按照日期更新Student表
		for(Student stu:stuList){
			if(signStuIdList.contains(stu.getId())){
				stu.setOnline(Boolean.TRUE);
			}else{
				stu.setOnline(Boolean.FALSE);
			}
			s.saveOrUpdate(stu);
		}
		
		trans.commit();
		return true;
	}	
	
	/*
	 * 日期格式必须为"yyyy-MM-dd"
	 * 
	 * 参照如下HQL语句：
	 * select stu as ip from SignRecord as record join record.student as stu 
		where record.regDate between '2014-12-22 00:00:00' and '2014-12-22 23:59:59'
				and record.ip='127.0.0.1' and stu.className='2013级软件工程（移动方向）一班'
	 */
	public static Student getStudentByRegDateAndIpAndClassName(String dateStr, String ip, String className){
		Date[] dates = DateUtil.getBetweenDates(dateStr);

		Session s = HibernateSessionFactory.getSession();
		String queryString ="select stu as ip from SignRecord as record join record.student as stu " +
							"where record.regDate between :aa and :bb " +
							"and record.ip=:cc and stu.className=:dd";
		Query query = s.createQuery(queryString);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		query.setString("cc", ip);
		query.setString("dd", className);
		
		@SuppressWarnings("unchecked")
		List<Student> list  = (List<Student>)query.list();
		
		if(list==null || list.size()==0){
			System.out.println("getStudentByRegDateAndIpAndClassName:没有找到学生");
			return null;
		}else if(list.size()>1){
			System.out.println("getStudentByRegDateAndIpAndClassName:找到了多个学生（在同一天同一班级同一IP时，有多个记录）");
			return null;
		}else{
			return (Student)list.get(0);
		}
	}
	
}
