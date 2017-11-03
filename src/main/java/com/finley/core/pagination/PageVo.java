package com.finley.core.pagination;

import java.util.List;

/***
 *
 * 分页Vo
 * @author 郑远锋
 * @param <T>
 */

public class PageVo<T>{

	private int  start;		    	//数据总条数
	private int  end;					//数据总条数
	private int recordsTotal;
	private int recordsFiltered;
	private List<T>	 data;				//分页对象

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
}
