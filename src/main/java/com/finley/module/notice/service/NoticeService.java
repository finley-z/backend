package com.finley.module.notice.service;


import com.finley.module.notice.dao.NoticeDao;
import com.finley.module.notice.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class NoticeService {
	@Autowired
	public NoticeDao noticeDao;

	public boolean addNotice(Notice notice){
		return noticeDao.addNotice(notice)>0?true:false;
	}

	public boolean updateNotice(Notice notice){
		return noticeDao.updateNotice(notice)>0?true:false;
	}
	public int countNotices(Notice notice){
		return noticeDao.countNotices(notice);
	}

	public List<Notice> findNotices(Notice notice){
		return noticeDao.findNotices(notice);
	}
	public Notice findNotice(Integer noticeId){
		return noticeDao.findNotice(noticeId);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteNotice(Integer[] ids){
		Notice notice=new Notice();
		for(Integer noticeId:ids){
			notice.setNoticeId(noticeId);
			notice.setStatus(2);
			noticeDao.updateNotice(notice);
		}
	}


}
