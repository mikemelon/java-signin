package cn.lynu.lyq.signin.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.model.Task;
import cn.lynu.lyq.signin.service.TaskService;
@Transactional
@Component("taskService")
public class TaskServiceImpl implements TaskService {
	private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	@Resource private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Task> findAllTaskForClassName(String className){
		Session s = sessionFactory.getCurrentSession();
//		s.clear();//清除一级缓存
		String queryString = "from Task where className=?";

		Query query = s.createQuery(queryString);
		query.setString(0, className);
		query.setCacheMode(CacheMode.IGNORE);//不从缓存中取数据，直接读取数据库!(关闭二级缓存)
		
		@SuppressWarnings("unchecked")
		List<Task> list  = (List<Task>)query.list();
		if(list==null || list.size()==0){
			logger.info("findAllTaskForClassName:该班级没有找到任何任务可用");
			return null;
		}else{
			return list;
		}
	}
	
	/**
	 * 根据当前登录用户学号(regNo)更新其任务(Task)
	 * @param regNo 学号（注意，不是Student.id）
	 * @param taskId
	 * @return
	 */
	@Override
	public boolean updateTaskForSpecificStudentByRegNo(String regNo, int taskId){
		Session s = sessionFactory.getCurrentSession();
		Query query=s.createQuery("from Student where regNo=?");
		query.setString(0, regNo);
		Student theStudent=(Student)query.uniqueResult();
		
		Task theTask=(Task)s.load(Task.class, new Integer(taskId));
		theTask.setStudent(theStudent);
		theTask.setIsselect(Boolean.TRUE);
		theTask.setSelectdate(new Date());
		
		s.update(theTask);
		logger.info("["+theStudent.getClassName()
				+ "]---->[" + theStudent.getName() + "]任务分配成功！");
		return true;
			
	}
	
	/**
	 * 根据学号（regNo）检查学生是否已经分配了任务(Task)，每个学生只能分配一个任务
	 * @param regNo
	 * @return true 表示已经分配过了（任务数>0），false 表示未分配（任务数<=0，或查询过程出错）
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public boolean checkStudentAlreadyAllocateTask(String regNo){
		Session s = sessionFactory.getCurrentSession();
		try{
			Query query=s.createQuery("from Student where regNo=?");
			query.setString(0, regNo);
			Student theStudent=(Student)query.uniqueResult();
			if(theStudent==null){
				logger.info("checkStudentAlreadyAllocateTask:没有找到对应学生");
				return false;
			}
			Query query2=s.createQuery("from Task where student=?");
			query2.setEntity(0, theStudent);
			
			@SuppressWarnings("unchecked")
			List<Task> allocatedList=(List<Task>)query2.list();
			
			if(allocatedList!=null && allocatedList.size()>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public boolean checkTaskNotAllocated(Integer taskId){
		Session s = sessionFactory.getCurrentSession();
		try{
			Query query=s.createQuery("from Task where id=? and stu_id is not null");
			query.setInteger(0, taskId);
			Task theTask=(Task)query.uniqueResult();
			if(theTask!=null){
//				logger.info("checkTaskAlreadyAllocated:没有找到对应任务");
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
	
}
