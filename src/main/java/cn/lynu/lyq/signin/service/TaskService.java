package cn.lynu.lyq.signin.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lynu.lyq.signin.model.Task;

@Transactional
@Service
public interface TaskService {
	public List<Task> findAllTaskForClassName(String className);
	public boolean updateTaskForSpecificStudentByRegNo(String regNo, int taskId);
	public boolean checkStudentAlreadyAllocateTask(String regNo);
	public boolean checkTaskNotAllocated(Integer taskId);
}