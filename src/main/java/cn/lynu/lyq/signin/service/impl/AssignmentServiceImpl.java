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

import cn.lynu.lyq.signin.model.Assignment;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.service.AssignmentService;
import cn.lynu.lyq.signin.util.DateUtil;
import cn.lynu.lyq.signin.util.Settings;
@Transactional
@Component("assignmentService")
public class AssignmentServiceImpl implements AssignmentService {
	private static Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);
	@Resource private SessionFactory sessionFactory;
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	@Override
	public boolean saveAssignmentWithCurDate(String regNo, String filePath, String comments){
		Session s = sessionFactory.getCurrentSession();
		
		Student curStudent=null;
		Query query = s.createQuery("from Student where regNo=?");
		query.setString(0, regNo);
		@SuppressWarnings("unchecked")
		List<Student> list  = (List<Student>)query.list();
		if(list==null || list.size()==0){
			logger.info("saveAssignmentWithCurDate:没有找到学生");
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
		return true;
	}	
	
	/* 
	 * 日期格式必须为"yyyy-MM-dd"
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Assignment> getAssignmentList(){
		Session s = sessionFactory.getCurrentSession();
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
			logger.info("getAssignmentList:没有找到作业");
			return null;
		}else{
			return list;
		}
	}		
}
