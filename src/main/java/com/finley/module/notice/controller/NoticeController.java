package com.finley.module.notice.controller;

import com.finley.common.usersession.UserSession;
import com.finley.core.pagination.PageVo;
import com.finley.core.respone.ResultVo;
import com.finley.module.notice.entity.Notice;
import com.finley.module.notice.service.NoticeService;
import com.finley.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zyf
 * @date 2017/5/23
 */

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "toNotice")
    public String categoryManage() {
        return "notice/notice";
    }


    @RequestMapping(value = "addNotice")
    public String addNotice() {
        return "notice/addNotice";
    }


    /**
     * 前端用户全部公告列表页面
     *
     * @param
     */
    @RequestMapping("/frontedList")
    public String frontedList() {
        return "notice/frontedList";
    }

    /**
     * 后台管理员用户全部公告列表页面
     *
     * @param
     */
    @RequestMapping("/backList")
    public String backList() {
        return "notice/backList";
    }


    /**
     * 前端公告详情页面
     *
     * @param noticeId
     */
    @RequestMapping("/frontedDetail")
    public String frontedDetail(Integer noticeId, Model model) {
        model.addAttribute("noticeId", noticeId);
        return "notice/frontedDetail";
    }

    /**
     * 后台公告详情页面
     *
     * @param noticeId
     */
    @RequestMapping("/backDetail")
    public String backDetail(Integer noticeId, String goback, Model model) {
        model.addAttribute("noticeId", noticeId);
        model.addAttribute("goback", goback);
        return "notice/backDetail";
    }


    @ResponseBody
    @RequestMapping(value = "saveNotice")
    public ResultVo saveNotice(Notice notice) {
        ResultVo resultVo = new ResultVo(true);
        notice.setOptUserId(UserSession.getUserId());
        try {
            if (notice.getNoticeId() == null) {
                noticeService.addNotice(notice);
            } else {
                noticeService.updateNotice(notice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setStatus(false);
        }
        return resultVo;
    }

    /**
     * 获取公告列表信息
     *
     * @param notice
     */
    @RequestMapping("/noticeList")
    @ResponseBody
    public PageVo<Notice> noticeList(Notice notice) {
        PageVo<Notice> pageVo = new PageVo<Notice>();
        pageVo.setData(noticeService.findNotices(notice));
        int count = noticeService.countNotices(notice);
        pageVo.setRecordsTotal(count);
        pageVo.setRecordsFiltered(count);
        return pageVo;
    }


    @RequestMapping("/noticeRead")
    @ResponseBody
    public Map<String, Object> noticeRead(Notice notice) {
        Map<String, Object> res = new HashMap<String, Object>();
        User user = UserSession.getUser();
        notice.setStartRow(0);
        notice.setRows(10);
        notice.setNoticeType(2);
        res.put("notices", noticeService.findNotices(notice));
        res.put("total", noticeService.countNotices(notice));
        return res;
    }


    @ResponseBody
    @RequestMapping(value = "deleteNotice")
    public ResultVo deleteNotice(Integer[] ids) {
        ResultVo resultVo = new ResultVo(true);
        try {
            noticeService.deleteNotice(ids);
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setStatus(false);
        }
        return resultVo;
    }


    @ResponseBody
    @RequestMapping(value = "findNotice")
    public Notice findNotice(Integer noticeId) {
        return noticeService.findNotice(noticeId);
    }

}


