package net.micourse.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reply_review")
public class Reply_review {
	@Id
	@Column(name = "reply_review_id")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "host_review_id", unique = true)
	private Host_Review host_review;

	@Column(name = "reply_to")
	private Integer reply_to;

	@Column(name = "content")
	private String content;

	@Column(name = "date")
	private Date date;

	public Reply_review() {
		super();
	}

	public Reply_review(User user, Host_Review host_review, Integer reply_to,
			String content, Date date) {
		super();
		this.user = user;
		this.host_review = host_review;
		this.reply_to = reply_to;
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

	public Host_Review getHost_review() {
		return host_review;
	}

	public void setHost_review(Host_Review host_review) {
		this.host_review = host_review;
	}

	public Integer getReply_to() {
		return reply_to;
	}

	public void setReply_to(Integer reply_to) {
		this.reply_to = reply_to;
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

}
