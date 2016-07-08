package net.micourse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.micourse.dao.BaseDao;
import net.micourse.model.Announcement;
import net.micourse.service.AnnouncementService;

@Service("announcementService")
public class AnnouncementServiceImpl implements AnnouncementService {
	
	@Resource
	private BaseDao<Announcement> baseDao;

	@Override
	public List<Announcement> getAnnouncements() {
		// TODO Auto-generated method stub
		return baseDao.find("from Announcement");
	}

}
