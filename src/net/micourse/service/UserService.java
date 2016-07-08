package net.micourse.service;

import java.text.ParseException;
import java.util.Map;

import net.micourse.model.User;

public interface UserService {
	public boolean emailExists(String email); //判断邮箱是否存在
	
	public boolean userVerification(String email,String password);//用户身份验证
	
	public Map<String, String> userLogin(String email,String password);//用户登录	
	
	public Map<String,String> userRegister(String email,String password,String ip);//用户注册	
	
	public void updateHeadPhoto(String email,String headPhoto_url);//更新用户头像

	public void updateDepartment(String email,int department_id);//更改用户院系
	
	public void updateUsername(String email,String username);//更改用户名
	
	public void updateSex(String email,String sex);//更改性别
	
	public void updateSelfDescription(String email,String self_description);//更改个人描述

	public void changePassword(String email, String newPassword);//用户更改个人密码

	public Map<String, Object> getReplyInfos(String email)throws ParseException;//获取他人的回复信息

	public Map<String, Object> getUserInfo(int user_id);//获取用户信息
	
	
}
