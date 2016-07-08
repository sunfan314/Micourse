package net.micourse.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.From;
import javax.persistence.criteria.CriteriaBuilder.Case;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Service;

import net.micourse.dao.BaseDao;
import net.micourse.model.CourseInfo;
import net.micourse.model.Host_Review;
import net.micourse.model.Reply_review;
import net.micourse.model.User;
import net.micourse.model.User_Review;
import net.micourse.service.ReviewService;
import net.micourse.util.SortByDateUtil;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
	@Resource
	private BaseDao<User_Review> userReviewDao;
	
	@Resource
	private BaseDao<User> userDao;

	@Resource
	private BaseDao<CourseInfo> courseDao;

	@Resource
	private BaseDao<Host_Review> hostReviewDao;

	@Resource
	private BaseDao<Reply_review> replyReviewDao;

	@Override
	public Map<String, String> addReview(int course_id, int star,
			String content, String email) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		CourseInfo courseInfo = courseDao
				.get("from CourseInfo where course_id=?",
						new Object[] { course_id });
		if (!(courseInfo == null)) {// 课程不存在
			if (star != 0) {// 添加学生对课程的评分
				switch (star) {
				case 1:
					int temp = courseInfo.getOne_star() + 1;
					courseInfo.setOne_star(temp);
					break;
				case 2:
					temp = courseInfo.getTwo_star() + 1;
					courseInfo.setTwo_star(temp);
					break;
				case 3:
					temp = courseInfo.getThree_star() + 1;
					courseInfo.setThree_star(temp);
					break;
				case 4:
					temp = courseInfo.getFour_star() + 1;
					courseInfo.setFour_star(temp);
					break;
				case 5:
					temp = courseInfo.getFive_star() + 1;
					courseInfo.setFive_star(temp);
					break;
				default:
					break;
				}
				courseDao.saveOrUpdate(courseInfo);

			}
		} else {
			map.put("error", "true");
			map.put("errMsg", "课程不存在");
			return map;
		}
		User user = userDao.get("from User where email=?",
				new Object[] { email });
		Host_Review hr = new Host_Review(user, courseInfo, 0, 0, content,
				new Date());
		hostReviewDao.save(hr);
		map.put("success", "true");
		return map;
	}

	@Override
	public Map<String, String> addReplyReview(int host_review_id,
			Integer reply_to, String content, String email) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		User user = userDao.get("from User where email=?",
				new Object[] { email });
		Host_Review host_review = hostReviewDao.get(
				"from Host_Review where id=?", new Object[] { host_review_id });
		if (host_review != null) {
			Reply_review rr = new Reply_review(user, host_review, reply_to,
					content, new Date());
			replyReviewDao.save(rr);
			map.put("success", "true");
		} else {
			map.put("error", "true");
			map.put("errMsg", "主楼层不存在");
		}
		return map;
	}

	@Override
	public List<HashMap<String, Object>> getCourseReviews(int course_id)
			throws ParseException {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		List paramList = new ArrayList<Object>();
		paramList.add(course_id);
		List<Host_Review> list = hostReviewDao.find(
				"from Host_Review where course_id=?", paramList);
		for (Host_Review h : list) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("host_review_id", h.getId());
			map.put("user_id", h.getUser().getUserId());
			map.put("username", h.getUser().getUsername());
			map.put("head_photo_url", h.getUser().getHeadphoto_url());
			map.put("date", h.getDate().toString());
			map.put("useful", h.getUseful());
			map.put("useless", h.getUseless());
			map.put("content", h.getContent());

			/*
			 * 添加子楼层信息
			 */
			List<Reply_review> reply_reviews = h.getReplyList();
			List<HashMap<String, Object>> reply_list = new ArrayList<HashMap<String, Object>>();
			for (Reply_review r : reply_reviews) {
				HashMap<String, Object> replyMap = new HashMap<String, Object>();
				replyMap.put("reply_review_id", r.getId());
				replyMap.put("user_id", r.getUser().getUserId());
				replyMap.put("user_name", r.getUser().getUsername());
				replyMap.put("head_photo_url", r.getUser().getHeadphoto_url());
				replyMap.put("date", r.getDate().toString());
				replyMap.put("content", r.getContent());
				reply_list.add(replyMap);
			}
			map.put("host_reply", SortByDateUtil.sortByDate2(reply_list));

			result.add(map);
		}
		return SortByDateUtil.sortByDate(result);
	}

	@Override
	public List<HashMap<String, Object>> getCourseReviews(int course_id,
			String email) throws ParseException {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		List paramList = new ArrayList<Object>();
		paramList.add(course_id);
		List<Host_Review> list = hostReviewDao.find(
				"from Host_Review where course_id=?", paramList);
		for (Host_Review h : list) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("host_review_id", h.getId());
			map.put("user_id", h.getUser().getUserId());
			map.put("username", h.getUser().getUsername());
			map.put("head_photo_url", h.getUser().getHeadphoto_url());
			map.put("date", h.getDate().toString());
			map.put("useful", h.getUseful());
			map.put("useless", h.getUseless());
			map.put("content", h.getContent());
			map.put("state", this.getReviewState(h.getId(), email));// 某条回复状态（0：未赞同未反对，1：已赞同，2：已反对）

			/*
			 * 添加子楼层信息
			 */
			List<Reply_review> reply_reviews = h.getReplyList();
			List<HashMap<String, Object>> reply_list = new ArrayList<HashMap<String, Object>>();
			for (Reply_review r : reply_reviews) {
				HashMap<String, Object> replyMap = new HashMap<String, Object>();
				replyMap.put("reply_review_id", r.getId());
				replyMap.put("user_id", r.getUser().getUserId());
				replyMap.put("user_name", r.getUser().getUsername());
				replyMap.put("head_photo_url", r.getUser().getHeadphoto_url());
				replyMap.put("date", r.getDate().toString());
				replyMap.put("content", r.getContent());
				reply_list.add(replyMap);
			}
			map.put("host_reply", SortByDateUtil.sortByDate2(reply_list));

			result.add(map);
		}
		return SortByDateUtil.sortByDate(result);

	}

	@Override
	public Map<String, String> evaluteWithReview(int host_review_id,
			String email, int i) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		User user = userDao.get("from User where email=?",
				new Object[] { email });
		User_Review user_Review = userReviewDao.get(
				"from User_Review where user_id=? and host_review_id=?",
				new Object[] { user.getUserId(), host_review_id });
		if (user_Review != null) {// 已对主楼层赞过或踩过
			map.put("error", "true");
			switch (user_Review.getState()) {
			case 1:
				map.put("errMsg", "您已赞过");
				break;
			case 2:
				map.put("errMsg", "您已踩过");
				break;
			default:
				break;
			}
			return map;
		}
		Host_Review hr = hostReviewDao.get("from Host_Review where id=?",
				new Object[] { host_review_id });
		if (hr != null) {
			User_Review ur = new User_Review(user.getUserId(), host_review_id,
					i);
			userReviewDao.save(ur);
			if (i == 1) {
				int useful=hr.getUseful()+1;
				hr.setUseful(useful);
				hostReviewDao.saveOrUpdate(hr);
			} else if (i == 2) {
				int useless=hr.getUseless()+1;
				hr.setUseless(useless);
				hostReviewDao.saveOrUpdate(hr);
			}
			
			map.put("success", "true");
		} else {
			map.put("error", "true");
			map.put("errMsg", "主楼层不存在");
		}
		return map;
	}

	private int getReviewState(int host_review_id, String email) {// 查询某条回复状态（0：未赞同未反对，1：已赞同，2：已反对）
		// TODO Auto-generated method stub
		User user = userDao.get("from User where email=?",
				new Object[] { email });
		int user_id = user.getUserId();
		User_Review ur = userReviewDao.get(
				"from User_Review where user_id=? and host_review_id=?",
				new Object[] { user_id, host_review_id });
		if (ur != null) {
			return ur.getState();
		} else {
			return 0;
		}

	}

}
