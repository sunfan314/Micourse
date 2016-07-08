package net.micourse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.micourse.model.CourseInfo;
import net.micourse.model.Department;
import net.micourse.model.Department_student;
import net.micourse.service.DepartmentService;
import net.micourse.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DepartmentController {
	
	@Resource
	private DepartmentService departmentService;
	
	@RequestMapping("/getDepartments")
	public @ResponseBody List<Department_student> getDepartments(){
		return departmentService.getDepartmentList();
	}

}
