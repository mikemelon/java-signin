package cn.lynu.lyq.signin.test;

import java.util.List;

import cn.lynu.lyq.signin.dao.TaskUtil;
import cn.lynu.lyq.signin.model.Task;

public class TestDB2 {

	public static void main(String[] args) {
		List<Task> list=TaskUtil.findAllTaskForClassName("2013级软件工程（移动方向）一班");
		for(Task t:list){
			if(t.getStudent()!=null){
				System.out.println(t.getStudent());
				System.out.println(t.getStudent().getName());
			}
		}

	}

}
