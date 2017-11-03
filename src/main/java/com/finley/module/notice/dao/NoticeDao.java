package com.finley.module.notice.dao;

import com.finley.module.notice.entity.Notice;


import java.util.List;

/**
 * Created by 郑远锋 on 2017/3/1.
 */
public interface NoticeDao {

    public int addNotice(Notice notice);

    public int updateNotice(Notice notice);

    public int countNotices(Notice notice);

    public List<Notice> findNotices(Notice notice);

    public Notice findNotice(Integer noticeId);
}
