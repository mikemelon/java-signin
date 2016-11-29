package cn.lynu.lyq.signin.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.lynu.lyq.signin.db.HibernateSessionFactoryOld;
import cn.lynu.lyq.signin.model.Task;

public class InsertJavaTasksFromDocs {

	public static void main(String[] args) throws Exception{
		String realPath=new File("").getAbsolutePath()+"\\WebRoot\\JavaProblems\\";
		String[] fileList=new File(realPath).list(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				if(name.lastIndexOf(".doc")>0){
					return true;
				}
				return false;
			}
		});
		
		TreeSet<String> ts = new TreeSet<String>(new FileNameDigitComparator());
		for(String s:fileList){
			ts.add(s);
		}
//		System.out.println(ts.toString());
//		System.out.println("ts size="+ts.size());
		
		Session session = HibernateSessionFactoryOld.getSession();
		Transaction trans=session.beginTransaction();
		
		Iterator<String> it = ts.iterator();
		while(it.hasNext()){
			String docName = it.next();
			String taskName = docName.substring(0, docName.lastIndexOf("."));
			Task task=new Task(null,//学生编号（开始不设置）
					taskName, //任务标题
					"请点击任务网址下载说明-->", //任务详述
					"JavaProblems/"+docName, //网址
					false,null,  //选中标志，选择日期（一开始不设置）
					"电子商务学院2014级软件工程1班");//班级名称
			session.save(task);			
		}
		trans.commit();
		session.close();
	}

}

/**
 * 按照文件名里的数字排序
 * @author Administrator
 */
class FileNameDigitComparator implements Comparator<String>{
	public int compare(String s1, String s2) {
		int n1=Integer.parseInt(s1.substring(0,s1.indexOf("，")));//取出中文逗号"，"前前面的数字
		int n2=Integer.parseInt(s2.substring(0,s2.indexOf("，")));
		return n1-n2;
	}
}
