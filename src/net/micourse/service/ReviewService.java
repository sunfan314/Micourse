package net.micourse.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.micourse.model.Host_Review;

public interface ReviewService {

	public Map<String,String> addReview(int course_id, int star, String content,String email);
	
	public Map<String, String> addReplyReview(int host_review_id, Integer reply_to,String content, String email);

	public List<HashMap<String, Object>> getCourseReviews(int course_id) throws ParseException;

	public List<HashMap<String, Object>> getCourseReviews(int course_id,String email) throws ParseException;

	public Map<String, String> evaluteWithReview(int host_review_id,
			String email, int i);

	
}
