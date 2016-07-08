package net.micourse.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.micourse.model.Host_Review;
import net.micourse.service.ReviewService;
import net.micourse.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReviewController {
	@Resource
	private UserService userService;
	
	@Resource
	private ReviewService reviewService;
	
	@RequestMapping("/addReview")
	public @ResponseBody Map<String,String> addReview(int course_id,int star,String content,String email,String password){
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return reviewService.addReview(course_id, star, content, email);
		
	}
	
	@RequestMapping("/addReplyReview")
	public @ResponseBody Map<String,String> addReplyReview(int host_review_id,@RequestParam(required=false)Integer reply_to,String content,String email,String password){
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return reviewService.addReplyReview(host_review_id,reply_to,content,email);
	}
	
	@RequestMapping("/getCourseReviews")
	public @ResponseBody List<HashMap<String, Object>> getCourseReviews(int course_id,@RequestParam(required=false)String email,@RequestParam(required=false)String password) throws ParseException{
		if(email!=null&&password!=null&&userService.userVerification(email, password)){//用户名密码不为空且用户名密码匹配成功
			return reviewService.getCourseReviews(course_id,email);
		}
		else{
			return reviewService.getCourseReviews(course_id);
		}
			
	}
	
	@RequestMapping("/agreeWithReview")
	public @ResponseBody Map<String,String> agreeWithReview(int host_review_id,String email,String password){
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return reviewService.evaluteWithReview(host_review_id,email,1);
	}
	
	@RequestMapping("/disagreeWithReview")
	public @ResponseBody Map<String,String> disagreeWithReview(int host_review_id,String email,String password){
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return reviewService.evaluteWithReview(host_review_id,email,2);
	}

}
