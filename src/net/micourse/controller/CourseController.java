package net.micourse.controller;

import javax.annotation.Resource;

import net.micourse.model.CourseInfo;
import net.micourse.service.CourseService;
import net.micourse.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
	@Resource
	private UserService userService;
	
	@Resource
	private CourseService courseService;
	
	@RequestMapping("/getJsonData")
	public @ResponseBody HashMap<String, String> getJsonData(){
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("data1", "value1");
		map.put("data2","value2");
		return map;
	}
	
	@RequestMapping("/getCourseInfos")
	public @ResponseBody List<HashMap<String, Object>> getCourseInfos(){
		return courseService.getCourseInfos();
	}
	
	@RequestMapping("/getCourseInfo")
	public @ResponseBody HashMap<String, Object> getCourseInfo(int course_id){
		return courseService.getCourseInfo(course_id);
	}
	
	@RequestMapping("/getCourseDescription")
	public @ResponseBody HashMap<String, String> getCourseDescription(int course_id){
		return courseService.getCourseDescription(course_id);
	}
	
	@RequestMapping("/addCollectedCourse")
	public @ResponseBody Map<String, String> addCollectedCourse(int course_id,String email,String password){
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return courseService.addCollectedCourse(course_id,email);
		
	}
	
	@RequestMapping("/deleteCollectedCourse")
	public @ResponseBody Map<String, String> deleteCollectedCourse(int course_id,String email,String password){
		Map<String, String> result = new HashMap<String, String>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return courseService.deleteCollectedCourse(course_id,email);
	}
	
	@RequestMapping("/queryCourseState")
	public @ResponseBody Map<String,Object> queryCourseState(int course_id,String email,String password){//查询课程收藏情况
		Map<String, Object> result = new HashMap<String, Object>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return courseService.queryCourseState(course_id,email);
		
	}
	
	@RequestMapping("/getCollectedCourses")
	public @ResponseBody Map<String,Object> getCollectedCourses(String email,String password){
		Map<String, Object> result = new HashMap<String, Object>();
		if(!userService.userVerification(email, password)){//用户没有权限
			result.put("error","true");
			result.put("errMsg", "没有操作权限");
			return result;
		}
		return courseService.getCollectedCourses(email);
	}
	
	@RequestMapping("/vagueSearchCourse")
	public @ResponseBody List<Map<String, Object>> vagueSearchCourse(String coursename){//模糊查找课程
		return courseService.vagueSearchCourse(coursename);
	}
	
	
}
