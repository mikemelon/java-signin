package cn.lynu.lyq.signin.test;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.Task;

public class TestDB1 {

	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction trans=session.beginTransaction();
		for(int n=2; n<35; n++){
			Task task=new Task(null,"任务"+n,"任务描述"+n,"http://www.task"+n+".com",false,null,"2013级软件工程（移动方向）一班");
			session.save(task);
		}
		trans.commit();
		session.close();

	}

}
