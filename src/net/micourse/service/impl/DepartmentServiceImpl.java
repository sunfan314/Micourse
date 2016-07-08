package net.micourse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.micourse.dao.BaseDao;
import net.micourse.model.Department;
import net.micourse.model.Department_student;
import net.micourse.service.DepartmentService;
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	@Resource
	private BaseDao<Department_student> baseDao;

	@Override
	public List<Department_student> getDepartmentList() {
		// TODO Auto-generated method stub
		return baseDao.find("from Department_student");
	}

}
