package cn.lynu.lyq.signin.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.AbsentRequest;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.AbsentRequestService;
import cn.lynu.lyq.signin.util.DateUtil;
@Component("absentRequestService")
public class AbsentRequestServiceImpl implements AbsentRequestService {
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	public boolean getRequestForStudentAndDate(String regNo,String dateStr){
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		Query query = s.createQuery("select count(*) " 
                  +"from AbsentRequest ar inner join ar.student s where s.regNo=? and ar.reqDate between :aa and :bb");
		query.setString(0, regNo);
		Date[] dates = DateUtil.getBetweenDates(dateStr);	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		
//		Long count  = (Long)query.uniqueResult();
//		int cnt =count.intValue();
//		Long count  = (Long)query.uniqueResult();
		Object count=query.uniqueResult();
		int cnt=0;
		if(count instanceof Long){
			cnt=((Long)count).intValue();
		}else{
			cnt =((Integer)count).intValue();
		}
		if(cnt==0){
			return false;
		}else{
			return true;
		}		
	}
	
	public boolean addAbsentReqeust(String stuId, Date date){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();		
		
		try{
			Query query=s.createQuery("from Student where id=?");
			query.setInteger(0, Integer.valueOf(stuId));
			Student student=(Student)query.uniqueResult();
			
			AbsentRequest ar = new AbsentRequest();
			ar.setStudent(student);
			ar.setReqDate(date);
			
			s.save(ar);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;
	}	

	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	public List<Student> getAbsentRequestStudentsForClassAndDate(String className, String dateStr){
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		Query query = s.createQuery(
                  "from AbsentRequest ar inner join ar.student s where s.className=? and ar.reqDate between :aa and :bb");
		query.setString(0, className);
		Date[] dates = DateUtil.getBetweenDates(dateStr);	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );
		
		@SuppressWarnings("unchecked")
		List<Object> list  = (List<Object>)query.list();
		List<Student> list2 = new ArrayList<Student>();
		if(list!=null){
			for(Object obj:list){
				Object[] objs=(Object[])obj;
//				AbsentRequest ar=(AbsentRequest)objs[0];
				Student student=(Student)objs[1];
				list2.add(student);
			}
		}
		return list2;
	}
}
