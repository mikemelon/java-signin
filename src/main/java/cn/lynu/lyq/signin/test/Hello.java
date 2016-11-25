package cn.lynu.lyq.signin.test;

import java.util.List;

import cn.lynu.lyq.signin.dao.SignRecordUtil;
import cn.lynu.lyq.signin.model.SignRecord;
import cn.lynu.lyq.signin.model.Student;

public class Hello {
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String execute(){
		name="JAVA EE课程";
		return "success";
	}
	
	public static void main(String[] args){
//		Session s = HibernateSessionFactory.getSession();
//		Transaction trans = s.beginTransaction();
//		
//		Student stu1 = new Student();
//		stu1.setName("测试1");
//		stu1.setClassName("网络1班");
//		stu1.setRegNo("0001");
//		stu1.setOnline(true);
//		
//		SignRecord signRecord = new SignRecord();
//		signRecord.setRegDate(new Date());
//		signRecord.setStudent(stu1);
//		signRecord.setIp("12.122.11.11");
//		signRecord.setRowIndex(100);
//		signRecord.setColumnIndex(220);
//		
//		s.save(stu1);
//		s.save(signRecord);
//		
//		trans.commit();
//		Query query = s.createQuery("from Student where id='67'");
//		List list = query.list();
//		if(list!=null && list.size()>0){
//			Student stu2=(Student)(list.get(0));
//			System.out.println(stu2.isOnline());
//		}

		List<SignRecord> list = SignRecordUtil.getSignRecordByRegDate("2012-04-25");
		System.out.println("length==="+list.size());
		for(SignRecord record:list){
//			SignRecord record = (SignRecord)o;
			Student stu =record.getStudent();
			System.out.println(stu.getName());
		}
	}


	
}
