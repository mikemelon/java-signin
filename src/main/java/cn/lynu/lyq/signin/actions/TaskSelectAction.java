package cn.lynu.lyq.signin.actions;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.lynu.lyq.signin.dao.TaskUtil;
import cn.lynu.lyq.signin.model.Task;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class TaskSelectAction extends ActionSupport {
	private static final long serialVersionUID = -3979300440889168352L;
	
	private List<Task> taskList;
	private Integer taskId;
	private Boolean taskAllocatedFlag;

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	public Boolean getTaskAllocatedFlag() {
		return taskAllocatedFlag;
	}

	public void setTaskAllocatedFlag(Boolean taskAllocatedFlag) {
		this.taskAllocatedFlag = taskAllocatedFlag;
	}

	@Override
	public String execute() throws Exception {
		Settings.PROJECT_REAL_PATH = getClass().getResource("/").getPath() ;
		String currentClassName = Settings.load(Settings.CURRENT_CLASS_KEY);
		taskList=TaskUtil.findAllTaskForClassName(currentClassName);
		
		ActionContext ctx=ActionContext.getContext();
		String stuRegNo=(String)ctx.getSession().get("CURRENT_USER_REGID");
		if(TaskUtil.checkStudentAlreadyAllocateTask(stuRegNo)){
//			System.out.println("学号："+stuRegNo+"任务已经分配过了");
			taskAllocatedFlag=true;
		}else{
			taskAllocatedFlag=false;
		}
		
		return SUCCESS;
	}
	
	public String confirmTask() throws Exception{
		ActionContext ctx=ActionContext.getContext();
		String stuRegNo=(String)ctx.getSession().get("CURRENT_USER_REGID");
		if(TaskUtil.checkStudentAlreadyAllocateTask(stuRegNo)){
			System.out.println("学号："+stuRegNo+"任务已经分配过了");
			return ERROR;
		}
		if(TaskUtil.checkTaskNotAllocated(taskId)){
			System.out.println("编号为"+taskId+"的任务已经被分配了！");
		}
		boolean updateFlg=false;
		if( stuRegNo!=null && !stuRegNo.equals("")){
			updateFlg=TaskUtil.updateTaskForSpecificStudentByRegNo(stuRegNo, taskId);
		}
		if(updateFlg)
			return SUCCESS;
		else
			return ERROR;
	}
}
