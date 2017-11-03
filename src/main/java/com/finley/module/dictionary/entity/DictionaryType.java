package com.finley.module.dictionary.entity;

import com.finley.module.common.entity.BaseEntity;

import java.util.List;

/**
 * 字典类型
 * @author 郑远锋
 *
 */
public class DictionaryType extends BaseEntity{

	private Integer typeId;
	private String  typeCode;
	private String  typeName;
	private String  typeDesc;
	private Integer status;

	//字典列表
	private List<Dictionary> dictionaryList;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Dictionary> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<Dictionary> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}
}