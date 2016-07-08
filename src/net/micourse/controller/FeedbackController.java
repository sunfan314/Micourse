package net.micourse.controller;

import java.util.Map;

import javax.annotation.Resource;

import net.micourse.service.FeedbackService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedbackController {
	@Resource
	private FeedbackService feedbackService;
	
	@RequestMapping("/addFeedback")
	public @ResponseBody Map<String, String> addFeedback(@RequestParam(required=false)String email,String content){
		return feedbackService.addFeedback(email,content);
	}

}
