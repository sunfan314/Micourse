package net.micourse.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "host_review")
public class Host_Review {
	@Id
	@Column(name = "host_review_id")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name = "course_id", unique = true)
	private CourseInfo courseInfo;

	@Column(name = "useful")
	private int useful;

	@Column(name = "useless")
	private int useless;

	@Column(name = "content")
	private String content;

	@Column(name = "date")
	private Date date;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "host_review", cascade = CascadeType.ALL)
	private List<Reply_review> replyList=new ArrayList<Reply_review>();

	public Host_Review() {
		super();
	}

	public Host_Review(User user, CourseInfo courseInfo, int useful,
			int useless, String content, Date date) {
		super();
		this.user = user;
		this.courseInfo = courseInfo;
		this.useful = useful;
		this.useless = useless;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public int getUseful() {
		return useful;
	}

	public void setUseful(int useful) {
		this.useful = useful;
	}

	public int getUseless() {
		return useless;
	}

	public void setUseless(int useless) {
		this.useless = useless;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Reply_review> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply_review> replyList) {
		this.replyList = replyList;
	}

}
