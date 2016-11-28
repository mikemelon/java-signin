package cn.lynu.lyq.signin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.Assignment;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.AssignmentService;
import cn.lynu.lyq.signin.util.DateUtil;
import cn.lynu.lyq.signin.util.Settings;
@Component("assignmentService")
public class AssignmentServiceImpl implements AssignmentService {
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	public boolean saveAssignmentWithCurDate(String regNo, String filePath, String comments){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();		
		
		try{
			Student curStudent=null;
			Query query = s.createQuery("from Student where regNo=?");
			query.setString(0, regNo);
			@SuppressWarnings("unchecked")
			List<Student> list  = (List<Student>)query.list();
			if(list==null || list.size()==0){
				System.out.println("saveAssignmentWithCurDate:没有找到学生");
				return false;
			}else{
				curStudent=(Student)(list.get(0));
			}			
			
			Assignment assignment = new Assignment();
			assignment.setStudent(curStudent);
			assignment.setSubmitdate(new Date());
			assignment.setFilepath(filePath);
			assignment.setComments(comments);
			
			s.save(assignment);
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
	public List<Assignment> getAssignmentList(){
		Session s = HibernateSessionFactory.getSession();
//		s.clear();
		
		String curClassStr = Settings.load(Settings.CURRENT_CLASS_KEY);
		
		String queryString = null;
		queryString="from Assignment where student.className=? and submitdate between :aa and :bb";
		Query query = s.createQuery(queryString);
		query.setString(0, curClassStr);
		String dateStr = Settings.load(Settings.CURRENT_DATE_KEY);
		
		Date[] dates = DateUtil.getBetweenDates(dateStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		query.setString("aa",sdf.format(dates[0]) );
		query.setString("bb",sdf.format(dates[1]) );	
		
		@SuppressWarnings("unchecked")
		List<Assignment> list  = (List<Assignment>)query.list();
		if(list==null || list.size()==0){
			System.out.println("getAssignmentList:没有找到作业");
			return null;
		}else{
			return list;
		}
	}		
}
