package net.micourse.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import junit.framework.Test;
import net.micourse.model.User;
import net.micourse.service.UserService;
import net.micourse.util.IpUtil;
import net.micourse.util.TransferUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/userLogin")
	public @ResponseBody Map<String, String> userLogin(String email,
			String password) {//用户登录	
		
		return userService.userLogin(email, password);
		
	}
	
	@RequestMapping("/getUserInfo")
	public @ResponseBody Map<String,Object> getUserInfo(int user_id){
		return userService.getUserInfo(user_id);
	}

	@RequestMapping("/userRegister")
	public @ResponseBody Map<String, String> userRegister(String email,
			String password, HttpServletRequest request) {//用户注册
		String ip = IpUtil.getIpAddr(request);
		return userService.userRegister(email, password, ip);
	}

	@RequestMapping("/uploadHeadPhoto")
	public @ResponseBody Map<String, String> uploadFile(
			@RequestParam("file") MultipartFile file, String email,
			String password,HttpServletRequest request) throws Exception {//用户上传头像
		
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		String fileName = this.createHeadPhotoName(email);
		//String filePath = request.getServletContext().getRealPath("/")+"resources/img/head_photo/" + fileName;
		String  filePath = "saestor://pic/head_photo/"+fileName;
		file.transferTo(new File(filePath));
		userService.updateHeadPhoto(email, fileName);//更新用户头像信息
		result.put("success", "true");
		return result;
		
	}
	
	@RequestMapping("/updateDepartment")
	public @ResponseBody Map<String, String> updateDepartment(int department_id,String email,String password){//更新院系
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		userService.updateDepartment(email, department_id);
		result.put("success","true");
		return result;
	}
	
	@RequestMapping("/updateUsername")
	public @ResponseBody Map<String, String> updateUsername(String username,String email,String password){//更新用户名
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		userService.updateUsername(email, username);
		result.put("success","true");
		return result;
	}
	
	@RequestMapping("/updateSex")
	public @ResponseBody Map<String, String> updateSex(String sex,String email,String password){//更新用户名
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		userService.updateSex(email, sex);
		result.put("success","true");
		return result;
	}
	
	@RequestMapping("/updateSelfDescription")
	public @ResponseBody Map<String, String> updateSelfDescription(String self_description,String email,String password){//更新用户名
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		userService.updateSelfDescription(email, self_description);
		result.put("success","true");
		return result;
	}
	
	@RequestMapping("/changePassword")
	public @ResponseBody Map<String,String> changePassword(String email,String password,String newPassword){//用户更改密码
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		userService.changePassword(email,newPassword);
		result.put("success","true");
		return result;
	}
	
	@RequestMapping("/getReplyInfos")
	public @ResponseBody Map<String, Object> getReplyInfos(String email,String password) throws ParseException{
		Map<String, Object> result = new HashMap<String, Object>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return userService.getReplyInfos(email);
	}
	
	private String createHeadPhotoName(String email) {//根据邮箱和当前时间生成用户头像名
		String result="";
		try {
			String email_md5=TransferUtil.md5Encode(email);
			Date date=new Date();
			long t=date.getTime();
			result = email_md5+"_"+t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
