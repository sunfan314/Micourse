package net.micourse.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.micourse.dao.BaseDao;
import net.micourse.model.Feedback;
import net.micourse.model.User;
import net.micourse.service.FeedbackService;
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
	@Resource
	private BaseDao<User> userDao;
	
	@Resource
	private BaseDao<Feedback> feedbackDao;

	@Override
	public Map<String, String> addFeedback(String email, String content) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String, String>();
		map.put("success","true");
		if(email!=null){
			User user=userDao.get("from User where email=?",new Object[]{email});
			if(user!=null){
				Feedback fb=new Feedback(user.getUserId(), content, new Date());
				feedbackDao.save(fb);
				return map;
			}
			
		}
		Feedback fb=new Feedback(content, new Date());
		feedbackDao.save(fb);
		return map;
	}

}
