package net.micourse.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "course_info")
public class CourseInfo {
	@Id
	private int course_id;

	private int course_num;

	private String course_name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "department_id", unique = true)
	private Department department;

	private Integer credit;// 课程学分

	private Integer type;// type课程类型 规定如下：0、未知。1、通时课2、研讨课
							// 3、优秀文化素质课4、文化素质课5、就业创业课

	private String teachers;

	@Column(name = "1_star")
	private int one_star;

	@Column(name = "2_star")
	private int two_star;

	@Column(name = "3_star")
	private int three_star;

	@Column(name = "4_star")
	private int four_star;

	@Column(name = "5_star")
	private int five_star;

	private String avg;// 课程评分

	@Column(name = "summary")
	private String summary;
	
	@Column(name="detailed_info")
	private String detailed_info;
	
	/*
	@OneToMany(mappedBy="courseInfo")
	@Fetch(value=FetchMode.JOIN)
	private List<Host_Review> host_review_list;
	*/

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public int getCourse_num() {
		return course_num;
	}

	public void setCourse_num(int course_num) {
		this.course_num = course_num;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTeachers() {
		return teachers;
	}

	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	public int getOne_star() {
		return one_star;
	}

	public void setOne_star(int one_star) {
		this.one_star = one_star;
	}

	public int getTwo_star() {
		return two_star;
	}

	public void setTwo_star(int two_star) {
		this.two_star = two_star;
	}

	public int getThree_star() {
		return three_star;
	}

	public void setThree_star(int three_star) {
		this.three_star = three_star;
	}

	public int getFour_star() {
		return four_star;
	}

	public void setFour_star(int four_star) {
		this.four_star = four_star;
	}

	public int getFive_star() {
		return five_star;
	}

	public void setFive_star(int five_star) {
		this.five_star = five_star;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getDetailed_info() {
		return detailed_info;
	}

	public void setDetailed_info(String detailed_info) {
		this.detailed_info = detailed_info;
	}

	/*
	public List<Host_Review> getHost_review_list() {
		return host_review_list;
	}

	public void setHost_review_list(List<Host_Review> host_review_list) {
		this.host_review_list = host_review_list;
	}
	*/
	
	
	

}
