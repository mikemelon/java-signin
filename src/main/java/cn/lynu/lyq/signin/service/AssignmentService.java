package cn.lynu.lyq.signin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lynu.lyq.signin.model.Assignment;

@Service
public interface AssignmentService {
	public boolean saveAssignmentWithCurDate(String regNo, String filePath, String comments);
	public List<Assignment> getAssignmentList();
}