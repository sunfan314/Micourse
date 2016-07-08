package net.micourse.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "user_id")
	private int userId;

	private String username;

	private String password;

	private String email;

	private String headphoto_url;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "department_id", unique = true)
	private Department_student department;

	private int sex;

	private String register_ip;// 注册ip

	private Date register_time;// 注册时间

	private String self_description;// 个人描述

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "user_collections", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id")

	)
	private List<CourseInfo> collections;// 个人收藏的课程

	public User() {
		super();
	}

	public User(String username, String password, String email,
			String register_ip, Date register_time) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.register_ip = register_ip;
		this.register_time = register_time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadphoto_url() {
		return headphoto_url;
	}

	public void setHeadphoto_url(String headphoto_url) {
		this.headphoto_url = headphoto_url;
	}

	public Department_student getDepartment() {
		return department;
	}

	public void setDepartment(Department_student department) {
		this.department = department;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getRegister_ip() {
		return register_ip;
	}

	public void setRegister_ip(String register_ip) {
		this.register_ip = register_ip;
	}

	public Date getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public String getSelf_description() {
		return self_description;
	}

	public void setSelf_description(String self_description) {
		this.self_description = self_description;
	}

	public List<CourseInfo> getCollections() {
		return collections;
	}

	public void setCollections(List<CourseInfo> collections) {
		this.collections = collections;
	}

}
