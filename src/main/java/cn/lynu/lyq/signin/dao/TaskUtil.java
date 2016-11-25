package cn.lynu.lyq.signin.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.Student;
import cn.lynu.lyq.signin.model.Task;

public class TaskUtil {

	public static List<Task> findAllTaskForClassName(String className){
		Session s = HibernateSessionFactory.getSession();
		s.clear();//清除一级缓存
		String queryString = "from Task where className=?";

		Query query = s.createQuery(queryString);
		query.setString(0, className);
		query.setCacheMode(CacheMode.IGNORE);//不从缓存中取数据，直接读取数据库!(关闭二级缓存)
		
		@SuppressWarnings("unchecked")
		List<Task> list  = (List<Task>)query.list();
		if(list==null || list.size()==0){
			System.out.println("findAllTaskForClassName:该班级没有找到任何任务可用");
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
	public static boolean updateTaskForSpecificStudentByRegNo(String regNo, int taskId){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans=s.beginTransaction();
		try{
			Query query=s.createQuery("from Student where regNo=?");
			query.setString(0, regNo);
			Student theStudent=(Student)query.uniqueResult();
			
			Task theTask=(Task)s.load(Task.class, new Integer(taskId));
			theTask.setStudent(theStudent);
			theTask.setIsselect(Boolean.TRUE);
			theTask.setSelectdate(new Date());
			
			s.update(theTask);
			trans.commit();
			System.out.println("["+theStudent.getClassName()
					+ "]---->[" + theStudent.getName() + "]任务分配成功！");
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
			trans.rollback();
			return false;
		}
	}
	
	/**
	 * 根据学号（regNo）检查学生是否已经分配了任务(Task)，每个学生只能分配一个任务
	 * @param regNo
	 * @return true 表示已经分配过了（任务数>0），false 表示未分配（任务数<=0，或查询过程出错）
	 */
	public static boolean checkStudentAlreadyAllocateTask(String regNo){
		Session s = HibernateSessionFactory.getSession();
		try{
			Query query=s.createQuery("from Student where regNo=?");
			query.setString(0, regNo);
			Student theStudent=(Student)query.uniqueResult();
			if(theStudent==null){
				System.out.println("checkStudentAlreadyAllocateTask:没有找到对应学生");
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
	
	public static boolean checkTaskNotAllocated(Integer taskId){
		Session s = HibernateSessionFactory.getSession();
		try{
			Query query=s.createQuery("from Task where id=? and stu_id is not null");
			query.setInteger(0, taskId);
			Task theTask=(Task)query.uniqueResult();
			if(theTask!=null){
//				System.out.println("checkTaskAlreadyAllocated:没有找到对应任务");
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
