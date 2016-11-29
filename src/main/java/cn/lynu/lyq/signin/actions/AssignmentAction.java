package cn.lynu.lyq.signin.actions;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.lynu.lyq.signin.model.Assignment;
import cn.lynu.lyq.signin.service.AssignmentService;
import cn.lynu.lyq.signin.util.Settings;
@Controller
@Scope("prototype")
public class AssignmentAction extends ActionSupport{
	private static final long serialVersionUID = 5583655833051548583L;
	private static Logger logger = LoggerFactory.getLogger(AssignmentAction.class);
	
	private String comments;
	private File attachFile;
	private String attachFileFileName;
	@Resource private AssignmentService assignmentService;
	
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
				
				/*boolean submitOK=*/assignmentService.saveAssignmentWithCurDate(regNo,
						new File(file,attachFileFileName).getAbsolutePath(), comments);
				logger.info("作业已保存:"+attachFileFileName);
				List<Assignment> list=assignmentService.getAssignmentList();
				ctx.put("ASSIGNMENT_LIST", list);
				return "list";
			}
			return "upload";
		}else{
			List<Assignment> list=assignmentService.getAssignmentList();
			ctx.put("ASSIGNMENT_LIST", list);			
			return "list";
		}
	}
}
