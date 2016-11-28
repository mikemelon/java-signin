package cn.lynu.lyq.signin.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lynu.lyq.signin.model.Assignment;

@Transactional
@Service
public interface AssignmentService {
	public boolean saveAssignmentWithCurDate(String regNo, String filePath, String comments);
	public List<Assignment> getAssignmentList();
}