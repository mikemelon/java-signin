package cn.lynu.lyq.signin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lynu.lyq.signin.model.Student;

@Service
public interface StudentService {
	public Student validateStudent(String regNo, String name);
	public List<Student> findByOnline(Boolean online, String className);
	public Student getIPForCurDate(String ip);
	public Student getStudentForCurrentClassAndIP(String className, String ip);
	public List<String> findDistinctClassName();
	public boolean updateStudent(Student stuToUpdate, String ipAddress, int rowIndex, int columnIndex);
	public  boolean updateStudentOnline(String className, boolean online);
	public  List<Student> getAllStudent(String className);
	public  List<Object[]> getStatsList(String currentClassName);
	public  boolean getStatsListByRegNoAndRegDate(String currentClassName, String regNo, String dateStr);
}