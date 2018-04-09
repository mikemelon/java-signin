package cn.lynu.lyq.signin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lynu.lyq.signin.model.SignRecord;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.StudentService;
import cn.lynu.lyq.signin.util.DateUtil;
import cn.lynu.lyq.signin.util.Settings;
@Transactional
@Component("studentService")
public class StudentServiceImpl implements StudentService {
	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	@Resource private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Student validateStudent(String regNo, String name){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("from Student where name=? and regNo=?");
		query.setString(0, name);
		query.setString(1, regNo);
		@SuppressWarnings("unchecked")
		List<Student> list  = query.list();
		
		if(list==null || list.size()==0){
			logger.info("validateStudent:没有找到学生");
			return null;
		}else{
			Student stu1=list.get(0);
			//logger.info(stu1.getName());
			return stu1;
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Student> findByOnline(Boolean online, String className){
		Session s = sessionFactory.getCurrentSession();
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
			logger.info("validateStudent:没有找到学生");
			return null;
		}else{
			return list;
		}		
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Student getStudentForCurrentClassAndIP(String className, String ip) {
//		Session s = HibernateSessionFactory.getSession();
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("from SignRecord sr where sr.student.className=? and ip=? and regDate between :aa and :bb");
		query.setString(0, className);
		query.setString(1, ip);
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
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Student getIPForCurDate(String ip){
//		Session s = HibernateSessionFactory.getSession();
		Session s = sessionFactory.getCurrentSession();
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
	
	@Override
	public List<String> findDistinctClassName(){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("select distinct s.className from Student s");
		@SuppressWarnings("unchecked")
		List<String> list  = (List<String>)query.list();
		if(list==null || list.size()==0){
			logger.info("validateStudent:没有找到班级");
			return null;
		}else{
			return list;
		}		
	}	
	
	@Override
	public boolean updateStudent(Student stuToUpdate, String ipAddress, int rowIndex, int columnIndex){
		Session s = sessionFactory.getCurrentSession();
		SignRecord signRecord = new SignRecord();
		signRecord.setRegDate(new Date());
		signRecord.setStudent(stuToUpdate);
		signRecord.setIp(ipAddress);
		signRecord.setRowIndex(rowIndex);
		signRecord.setColumnIndex(columnIndex);
		
		s.update(stuToUpdate);
		s.save(signRecord);
		return true;
	}
	
	@Override
	public boolean updateStudentOnline(String className, boolean online){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("update Student set online=? where className=?");
		query.setBoolean(0, online);
		query.setString(1, className);
		query.executeUpdate();
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Student> getAllStudent(String className){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("from Student where className=?");
		query.setString(0, className);
		@SuppressWarnings("unchecked")
		List<Student> list  = (List<Student>)query.list();
		if(list==null || list.size()==0){
			logger.info("getAllStudent:没有找到学生");
			return null;
		}else{
			return list;
		}
		
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Object[]> getStatsList(String currentClassName) {
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("select s.name, s.id, s.regNo, s.className,r.regDate " 
                  +"from SignRecord  r inner join r.student s where s.className=?");
		query.setString(0, currentClassName);
		@SuppressWarnings("unchecked")
		List<Object[]> list  = (List<Object[]>)query.list();
		if(list==null || list.size()==0){
			logger.info("getStatsList:没有找到学生");
			return null;
		}else{
			return list;
		}
	}	
	
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public boolean getStatsListByRegNoAndRegDate(String currentClassName, String regNo, String dateStr) {
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("select count(*) " 
                  +"from SignRecord  r inner join r.student s "
                  + "where s.className=? and s.regNo=? and r.regDate between :aa and :bb");
		query.setString(0, currentClassName);
		query.setString(1, regNo);
		Date[] dates = DateUtil.getBetweenDates(dateStr);	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		
		Object count=query.uniqueResult();
		int cnt=0;
		if(count instanceof Long){
			cnt=((Long)count).intValue();
		}else{
			cnt =((Integer)count).intValue();
		}
//		logger.info("getStatsListByRegNoAndRegDate-->cnt=["+cnt+"]");
		if(cnt==0){
			return false;
		}else{
			return true;
		}
	}

}
