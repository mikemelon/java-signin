package cn.lynu.lyq.signin.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lynu.lyq.signin.model.Student;

@Transactional
@Service
public interface AbsentRequestService {
	public boolean getRequestForStudentAndDate(String regNo,String dateStr);
	public boolean addAbsentReqeust(String stuId, Date date);
	public List<Student> getAbsentRequestStudentsForClassAndDate(String className, String dateStr);
}