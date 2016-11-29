package cn.lynu.lyq.signin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lynu.lyq.signin.model.SignRecord;
import cn.lynu.lyq.signin.model.Student;

@Service
public interface SignRecordService {
	public SignRecord getSignRecordByStuId(int studentId);
	public List<SignRecord> getSignRecordByRegDate(String dateStr);
	public boolean updateStudentOnlineByRegDate(String dateStr, String className);
	public Student getStudentByRegDateAndIpAndClassName(String dateStr, String ip, String className);
}