package cn.lynu.lyq.signin.actions;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.lynu.lyq.signin.dao.AssignmentUtil;
import cn.lynu.lyq.signin.model.Assignment;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class AssignmentAction {
	private String comments;
	private File attachFile;
	private String attachFileFileName;
	
	public File getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(File attachFile) {
		this.attachFile = attachFile;
	}
	public String getAttachFileFileName() {
		return attachFileFileName;
	}
	public void setAttachFileFileName(String attachFileFileName) {
		this.attachFileFileName = attachFileFileName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String execute() throws Exception{
		ActionContext ctx=ActionContext.getContext();
		String regNo=(String)ctx.getSession().get("CURRENT_USER_REGID");
		String stuName=(String)ctx.getSession().get("CURRENT_USER_NAME");
		
		if(regNo!=null && !regNo.equals("")){
			if(attachFile!=null){
				
				//实际保存路径，类似 "d:/uploads/2013-03-28/2010网络工程/张三20010201/"
				String uploadDir= Settings.load(Settings.UPLOAD_DIR_KEY)
							+ "/" + Settings.load(Settings.CURRENT_DATE_KEY)
							+ "/" + Settings.load(Settings.CURRENT_CLASS_KEY)
							+"/" + stuName + regNo + "/";
				
				File file=new File(uploadDir);
				if(!file.exists())  file.mkdirs();
				FileUtils.copyFile(attachFile,new File(file,attachFileFileName));
				
				/*boolean submitOK=*/AssignmentUtil.saveAssignmentWithCurDate(regNo,
						new File(file,attachFileFileName).getAbsolutePath(), comments);
				
				List<Assignment> list=AssignmentUtil.getAssignmentList();
				ctx.put("ASSIGNMENT_LIST", list);
				return "list";
			}
			return "upload";
		}else{
			List<Assignment> list=AssignmentUtil.getAssignmentList();
			ctx.put("ASSIGNMENT_LIST", list);			
			return "list";
		}
	}
}
