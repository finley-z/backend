package com.finley.module.notice.entity;

import com.finley.enums.NoticeTypeEnum;
import com.finley.module.common.entity.BaseEntity;

import java.util.Date;

/**
 * @author zyf
 * @date 2017/5/23
 */
public class Notice extends BaseEntity {
    private Integer noticeId;
    private String noticeTitle;
    private String noticeContent;
    private Date createTime;
    private Integer optUserId;
    private String optUserName;
    private Integer noticeType;
    private String noticeTypeName;
    private Integer status;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Integer optUserId) {
        this.optUserId = optUserId;
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTypeName() {
       if(this.noticeType!=null){
           return NoticeTypeEnum.getDesc(noticeType);
       }
        return "";
    }

    public void setNoticeTypeName(String noticeTypeName) {
        this.noticeTypeName = noticeTypeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
