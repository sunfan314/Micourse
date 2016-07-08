package net.micourse.service;

import net.micourse.model.CourseInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface CourseService {
	public List<HashMap<String, Object>> getCourseInfos();

	public HashMap<String, Object> getCourseInfo(int course_id);

	public HashMap<String, String> getCourseDescription(int course_id);

	public Map<String, String> addCollectedCourse(int course_id, String email);

	public Map<String, String> deleteCollectedCourse(int course_id, String email);

	public Map<String, Object> queryCourseState(int course_id, String email);

	public Map<String, Object> getCollectedCourses(String email);

	public List<Map<String, Object>> vagueSearchCourse(String coursename);
	
}
