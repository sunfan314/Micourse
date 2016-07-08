package net.micourse.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "user_id")
	private Integer user_id;

	@Column(name = "content")
	private String content;

	@Column(name = "date")
	private Date date;

	public Feedback() {
		super();
	}

	public Feedback(String content, Date date) {
		super();
		this.content = content;
		this.date = date;
	}

	public Feedback(Integer user_id, String content, Date date) {
		super();
		this.user_id = user_id;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
