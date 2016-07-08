package net.micourse.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.criteria.From;

import org.springframework.stereotype.Service;

import net.micourse.dao.BaseDao;
import net.micourse.model.Department;
import net.micourse.model.Department_student;
import net.micourse.model.Host_Review;
import net.micourse.model.Reply_review;
import net.micourse.model.User;
import net.micourse.service.UserService;
import net.micourse.util.EmailUtil;
import net.micourse.util.SortByDateUtil;
@Service("userService")

public class UserServiceImpl implements UserService {
	
	@Resource
	private BaseDao<User> baseDao;
	
	@Resource
	private BaseDao<Host_Review> hostReviewDao;
	
	@Resource
	private BaseDao<Reply_review> replyReviewDao;
	
	@Resource
	private BaseDao<Department_student> department_baseDao;
	
	@Override
	public boolean emailExists(String email){//判断邮箱是否已经存在
		User user=baseDao.get("from User where email=?",new Object[] { email });
		if(user!=null){//邮箱已存在
			return true;
		}
		return false;
	}
	
	@Override
	public boolean userVerification(String email, String password) {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		User user=baseDao.get("from User where email=?",new Object[] { email });
		if(user!=null){
			if(user.getPassword().equals(password)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	@Override
	public Map<String, String> userLogin(String email,String password){//用户登录
		Map<String, String> map=new HashMap<String, String>();
		User user=baseDao.get("from User where email=?",new Object[] { email });
		if(user!=null){
			if(user.getPassword().equals(password)){//用户成功登录则上传用户名和头像的url
				map.put("success", "true");
				map.put("username",user.getUsername());
				map.put("headphoto_url", user.getHeadphoto_url());
				if(user.getDepartment()==null){
					map.put("department", "");
				}else{
					map.put("department",user.getDepartment().getDepartment_name());
				}
				String sex="";
				if(user.getSex()==1){
					sex="男";
				}else if(user.getSex()==2){
					sex="女";
				}
				map.put("sex", sex);
				map.put("self_description",user.getSelf_description() );
				
			}
			else{
				map.put("error", "true");
				map.put("errMsg", "密码错误");
			}
		}
		else{
			map.put("error", "true");
			map.put("errMsg", "用户不存在");
		}
		return map;
	}
	
	@Override
	public Map<String,String> userRegister(String email,String password,String ip){//用户注册
		Map<String, String> map=new HashMap<String, String>();
		if(!EmailUtil.emailFormat(email)){//验证邮箱格式
			map.put("error", "true");
			map.put("errMsg","邮箱格式不正确");
			return map;
		}
		
		if(emailExists(email)){//如果邮箱已存在
			map.put("error","true");
			map.put("errMsg","该邮箱已被注册");
			return map;
		}
		
		List paramList=new ArrayList<Object>();
		paramList.add(ip);
		List<User> list=baseDao.find("from User where register_ip=?",paramList);
		if(list.size()>0){//注册ip已存在
			Date date=new Date();
			long t1 =date .getTime();
			long t2 = this.getLatestRegisterTime(list);
			long millis = t1 - t2;
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
			if(seconds<1800){//同一ip注册时间小于30分钟
				map.put("error", "true");
				map.put("errMsg","请勿频繁注册");
				return map;
			}
		}
		
		User newUser=new User(EmailUtil.transferEmailToUsername(email),password,email,ip,new Date());
		baseDao.save(newUser);
		map.put("success", "true");
		map.put("username", EmailUtil.transferEmailToUsername(email));
		return map;
	}
	
	public long getLatestRegisterTime(List<User> userList){//获取重复ip账号中最新注册的账号
		long tmp=userList.get(0).getRegister_time().getTime();
		for(int i=0;i<userList.size();i++){
			if(tmp<=userList.get(i).getRegister_time().getTime()){
				tmp=userList.get(i).getRegister_time().getTime();
			}
		}
		return tmp;
	}

	@Override
	public void updateHeadPhoto(String email, String headPhoto_url) {//更新用户头像
		// TODO Auto-generated method stub
		
		User user=baseDao.get("from User where email=?",new Object[] { email });
		user.setHeadphoto_url(headPhoto_url);
		baseDao.saveOrUpdate(user);
		
	}

	@Override
	public void updateDepartment(String email,int department_id) {
		// TODO Auto-generated method stub
		User user=baseDao.get("from User where email=?",new Object[] { email });
		Department_student department=department_baseDao.get("from Department_student where department_id=?",new Object[]{ department_id } );
		user.setDepartment(department);
		baseDao.saveOrUpdate(user);
	
	}

	@Override
	public void updateUsername(String email,String username) {
		// TODO Auto-generated method stub
		User user=baseDao.get("from User where email=?",new Object[] { email });
		user.setUsername(username);
		baseDao.saveOrUpdate(user);
	}

	@Override
	public void updateSex(String email,String sex) {
		// TODO Auto-generated method stub
		User user=baseDao.get("from User where email=?",new Object[] { email });
		if(sex.equals("男"))
			user.setSex(1);
		if(sex.equals("女"))
			user.setSex(2);
		baseDao.saveOrUpdate(user);
		
	}

	@Override
	public void updateSelfDescription(String email,String self_description) {
		// TODO Auto-generated method stub
		User user=baseDao.get("from User where email=?",new Object[] { email });
		user.setSelf_description(self_description);
		baseDao.saveOrUpdate(user);
	}

	@Override
	public void changePassword(String email, String newPassword) {
		// TODO Auto-generated method stub
		User user=baseDao.get("from User where email=?",new Object[] { email });
		user.setPassword(newPassword);
		baseDao.saveOrUpdate(user);
	}

	@Override
	public Map<String, Object> getReplyInfos(String email) throws ParseException {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		User user=baseDao.get("from User where email=?",new Object[]{email});
		List<Host_Review> userHost_Reviews=hostReviewDao.find("from Host_Review where user=?", new Object[]{user});
		List<Reply_review> userReply_reviews=replyReviewDao.find("from Reply_review where user=?",new Object[]{user});
		List<Reply_review> replyList=new ArrayList<Reply_review>();
		for (Host_Review h : userHost_Reviews) {
			List<Reply_review> list=replyReviewDao.find("from Reply_review where host_review=? and reply_to=null", new Object[]{h});
			replyList.addAll(list);
		}
		for (Reply_review rr : userReply_reviews) {
			List<Reply_review> list=replyReviewDao.find("from Reply_review where reply_to=?", new Object[]{rr.getId()});
			replyList.addAll(list);
		}
		List<HashMap<String,Object>> replyListInfo=new ArrayList<HashMap<String,Object>>();
		for (Reply_review temp : replyList) {
			HashMap<String,Object> map2=new HashMap<String, Object>();
			map2.put("review_id",temp.getId());
			map2.put("host_review_id",temp.getHost_review().getId());
			map2.put("user_id",temp.getUser().getUserId());
			map2.put("user_name",temp.getUser().getUsername());
			map2.put("head_photo",temp.getUser().getHeadphoto_url());
			map2.put("date",temp.getDate().toString());
			map2.put("content",temp.getContent());
			map2.put("courseId",temp.getHost_review().getCourseInfo().getCourse_id());
			map2.put("courseName",temp.getHost_review().getCourseInfo().getCourse_name());
			replyListInfo.add(map2);
		}
		map.put("success", "true");
		replyListInfo=this.removeRepeatItem(replyListInfo);
		map.put("replyInfos", SortByDateUtil.sortByDate(replyListInfo));
		return map;
	}
	
	public List<HashMap<String, Object>> removeRepeatItem(List<HashMap<String, Object>> list){
		HashSet<HashMap<String, Object>> set=new HashSet<HashMap<String,Object>>();
		for (HashMap<String, Object> map : list) {
			set.add(map);
		}
		List<HashMap<String, Object>> result=new ArrayList<HashMap<String,Object>>();
		for (HashMap<String, Object> hashMap : set) {
			result.add(hashMap);
		}
		return result;
	}

	@Override
	public Map<String, Object> getUserInfo(int user_id) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String, Object>();
		User user=baseDao.get("from User where userId=?",new Object[]{user_id});
		if(user!=null){
			Map<String,Object> infoMap=new HashMap<String, Object>();
			map.put("success","true" );
			infoMap.put("user_id", user.getUserId());
			infoMap.put("username",user.getUsername());
			if(user.getDepartment()!=null){
				infoMap.put("department_name",user.getDepartment().getDepartment_name());
			}else{
				infoMap.put("department_name","");
			}
			switch (user.getSex()) {
			case 1:
				infoMap.put("sex","男");
				break;
			case 2:
				infoMap.put("sex","女");
				break;
			default:
				infoMap.put("sex","");
				break;
			}
			infoMap.put("self_description",user.getSelf_description());
			infoMap.put("head_photo_url",user.getHeadphoto_url());
			map.put("user_info", infoMap);
			return map;
		}
		else{
			map.put("error","true");
			map.put("errMsg","用户不存在");
			return map;
		}
		
	}
	

}
