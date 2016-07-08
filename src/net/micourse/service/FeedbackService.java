package net.micourse.service;

import java.util.Map;

public interface FeedbackService {

	Map<String, String> addFeedback(String email, String content);

}
