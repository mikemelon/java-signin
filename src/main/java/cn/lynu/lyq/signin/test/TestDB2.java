package cn.lynu.lyq.signin.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.lynu.lyq.signin.model.Task;
import cn.lynu.lyq.signin.service.TaskService;

public class TestDB2 {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		TaskService taskService = (TaskService)ctx.getBean("taskService");
		List<Task> list=taskService.findAllTaskForClassName("2013级软件工程（移动方向）一班");
		for(Task t:list){
			if(t.getStudent()!=null){
				System.out.println(t.getStudent());
				System.out.println(t.getStudent().getName());
			}
		}
		ctx.close();

	}

}
