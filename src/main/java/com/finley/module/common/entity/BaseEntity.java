package com.finley.module.common.entity;

import java.io.Serializable;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public class BaseEntity implements Serializable{

    protected  Integer id;
    //起始行
    protected transient Integer startRow=0;
    //行数
    protected transient Integer rows;
    //页码
    protected transient Integer page = 1;

    protected transient String startDate;

    protected transient String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartRow() {
        //默认起始行
        if(page!=null&&rows!=null){
            return startRow == 0 ? ((page - 1) < 0 ? 0 : (page - 1)) * rows : startRow;
        }
        return startRow;
        //return startRow;
    }
    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }
    public Integer getRows() {
        return rows;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    public int getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
