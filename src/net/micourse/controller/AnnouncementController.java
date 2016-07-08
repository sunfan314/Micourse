package net.micourse.controller;

import java.util.List;

import javax.annotation.Resource;

import net.micourse.model.Announcement;
import net.micourse.model.CourseInfo;
import net.micourse.service.AnnouncementService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnnouncementController {
	@Resource
	private AnnouncementService annService;
	
	@RequestMapping("/getAnnouncements")
	public @ResponseBody List<Announcement> getAnnouncements(){
		for (Announcement a : annService.getAnnouncements()) {
			System.out.println(a.getCreate_time());
		}
		return annService.getAnnouncements();
	}

}
