package net.micourse.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.corba.se.spi.oa.OADefault;

import net.micourse.dao.BaseDao;
import net.micourse.model.CourseInfo;
import net.micourse.model.Host_Review;
import net.micourse.model.User;
import net.micourse.service.CourseService;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
	@Resource
	private BaseDao<User> userDao;
	
	@Resource
	private BaseDao<CourseInfo> baseDao;
	
	@Resource
	private BaseDao<Host_Review> reviewDao; 

	@Override
	public List<HashMap<String, Object>> getCourseInfos() {
		// TODO Auto-generated method stub
		//一个hashmap中包含的信息有：course_id course_num course_name credit teachers avg summary sum
		List<HashMap<String, Object>> course_infos=new ArrayList<HashMap<String,Object>>();
		List<CourseInfo> courses=baseDao.find("from CourseInfo");
		for (CourseInfo c : courses) {//设置课程评价分
			int n=c.getOne_star()+c.getTwo_star()+c.getThree_star()+c.getFour_star()+c.getFive_star();
			if(n==0){
				//尚未有人评分
				c.setAvg("0");
				continue;
			}
			double total=c.getOne_star()+2*c.getTwo_star()+3*c.getThree_star()+4*c.getFour_star()+5*c.getFive_star();
			double num=n;
			double result=total/num;
			DecimalFormat df = new DecimalFormat("0.0");
			c.setAvg(df.format(result));
		}
		for (CourseInfo c : courses) {
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("course_id", c.getCourse_id());
			map.put("course_num",c.getCourse_num());
			map.put("course_name",c.getCourse_name());
			map.put("credit",c.getCredit());
			map.put("teachers",c.getTeachers());
			map.put("avg",c.getAvg());
			map.put("sum",c.getOne_star()+c.getTwo_star()+c.getThree_star()+c.getFour_star()+c.getFive_star());
			//map.put("sum_review",this.getNumOfHost_reviews(c.getCourse_id()));
			//map.put("sum_review",c.getHost_review_list().size());
			map.put("summary",c.getSummary());
			course_infos.add(map);
		}
	
		return course_infos;
	}
	

	@Override
	public HashMap<String, Object> getCourseInfo(int course_id) {
		// TODO Auto-generated method stub
		//返回信息：课程id,课程号  课程名 类型 学分 主讲教师 开课院系 1星，2星。。。人数
		HashMap<String, Object> map=new HashMap<String, Object>();
		CourseInfo course=baseDao.get("from CourseInfo where course_id=?",new Object[]{course_id});
		if(!(course==null)){
			map.put("course_id", course.getCourse_id());
			map.put("course_num",course.getCourse_num());
			map.put("course_name", course.getCourse_name());
			
			switch (course.getType()) {//'type课程类型 规定如下：0、未知。1、通时课2、研讨课 3、优秀文化素质课4、文化素质课5、就业创业课'
			
			case 1:
				map.put("type", "通识课");
				break;
				
			case 2:
				map.put("type", "研讨课");
				break;
				
			case 3:
				map.put("type", "优秀文化素质课");
				break;
			
			case 4:
				map.put("type", "文化素质课");
				break;
			
			case 5:
				map.put("type", "就业创业课");
				break;
				
			default://未知类型
				map.put("type", "");
				break;
			}
			
			map.put("credit", course.getCredit());
			map.put("main_teacher",this.getMainTeacher(course.getDetailed_info()));
			if(course.getDepartment()==null){
				map.put("department_name", "");
			}
			else{
				map.put("department_name",course.getDepartment().getDepartment_name());
			}
			map.put("one_star", course.getOne_star());
			map.put("two_star", course.getTwo_star());
			map.put("three_star", course.getThree_star());
			map.put("four_star", course.getFour_star());
			map.put("five_star", course.getFive_star());
			
		}
		
		return map;
	}

	@Override
	public HashMap<String, String> getCourseDescription(int course_id) {
		// TODO Auto-generated method stub
		HashMap<String, String> map=new HashMap<String, String>();
		CourseInfo course=baseDao.get("from CourseInfo where course_id=?",new Object[]{course_id});
		if(!(course==null)){
			map.put("description", course.getDetailed_info());
		}
		return map;
	}
	
	private String getMainTeacher(String description){//从课程详细信息中获取课程的主教室信息
		if(!(description==null)){
			description=description.replace(" ", "");//去除空格
			int index=description.indexOf("主讲教师");
			if(index<0){
				return "";
			}
			description=description.substring(index);
			index=description.indexOf("<");
			description=description.substring(0, index);
			index=description.indexOf("：");
			description=description.substring(index+1);
			return description;
		}else{
			return "";
		}
		
	}
	
	/*
	private int getNumOfHost_reviews(int course_id) {
		// TODO Auto-generated method stub
		List paramList=new ArrayList<Object>();
		paramList.add(course_id);
		List<Host_Review> list=reviewDao.find("from Host_Review where course_id=?",paramList);
		return list.size();
	}
	*/
	

	@Override
	public Map<String, String> addCollectedCourse(int course_id, String email) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String, String>();
		User user=userDao.get("from User where email=?",new Object[]{email});
		CourseInfo courseInfo=baseDao.get("from CourseInfo where course_id=?",new Object[]{course_id});
		if(courseInfo!=null){
			user.getCollections().add(courseInfo);
			userDao.saveOrUpdate(user);
			map.put("success","true");
		}else{
			map.put("error","true");
			map.put("errMsg","课程不存在" );
		}
		return map;
	}

	@Override
	public Map<String, String> deleteCollectedCourse(int course_id, String email) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String, String>();
		User user=userDao.get("from User where email=?",new Object[]{email});
		List<CourseInfo> courseList=user.getCollections();
		for (CourseInfo c : courseList) {
			if(c.getCourse_id()==course_id){
				courseList.remove(c);
				userDao.saveOrUpdate(user);
				map.put("success", "true");
				return map;
			}
		}
		map.put("error", "true");
		map.put("errMsg", "尚未收藏该课程");
		return map;
	}

	@Override
	public Map<String, Object> queryCourseState(int course_id, String email) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String, Object>();
		User user=userDao.get("from User where email=?",new Object[]{email});
		List<CourseInfo> courseList=user.getCollections();
		int state=0;//未收藏
		for (CourseInfo c : courseList) {
			if(c.getCourse_id()==course_id){
				state=1;
				break;
			}	
		}
		map.put("success", "true");
		map.put("state", state);
		return map;
	}

	@Override
	public Map<String, Object> getCollectedCourses(String email) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String, Object>();
		User user=userDao.get("from User where email=?",new Object[]{email});
		List<CourseInfo> courseList=user.getCollections();
		List<Map<String,Object>> collectedCourses=new ArrayList<Map<String,Object>>();
		for (CourseInfo c : courseList) {
			Map<String,Object> tmpMap=new HashMap<String, Object>();
			tmpMap.put("course_id", c.getCourse_id());
			tmpMap.put("course_name", c.getCourse_name());
			tmpMap.put("teachers",c.getTeachers() );
			collectedCourses.add(tmpMap);
		}
		map.put("success","true");
		map.put("courses",collectedCourses);
		return map;
	}

	@Override
	public List<Map<String, Object>> vagueSearchCourse(String coursename) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String param="%"+coursename+"%";
		List<CourseInfo> courseList=baseDao.find("from CourseInfo where course_name like ?",new Object[]{param});
		for (CourseInfo c : courseList) {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("course_id",c.getCourse_id());
			map.put("course_name",c.getCourse_name());
			map.put("teachers",c.getTeachers() );
			list.add(map);
		}
		return list;
	}


}
