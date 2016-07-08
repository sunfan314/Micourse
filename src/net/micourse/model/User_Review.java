package net.micourse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_review")
public class User_Review {// 存储用户对主楼层的赞同反对情况
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "user_id")
	private int user_id;

	@Column(name = "host_review_id")
	private int host_review_id;

	@Column(name = "state")
	private int state;// 1表示赞同，2表示反对
	

	public User_Review() {
		super();
	}

	public User_Review(int user_id, int host_review_id, int state) {
		super();
		this.user_id = user_id;
		this.host_review_id = host_review_id;
		this.state = state;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getHost_review_id() {
		return host_review_id;
	}

	public void setHost_review_id(int host_review_id) {
		this.host_review_id = host_review_id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
