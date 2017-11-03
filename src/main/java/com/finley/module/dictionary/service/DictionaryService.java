package com.finley.module.dictionary.service;


import com.finley.module.dictionary.dao.DictionaryDao;
import com.finley.module.dictionary.entity.Dictionary;
import com.finley.module.dictionary.entity.DictionaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DictionaryService {
	@Autowired
	public DictionaryDao dictionaryDao;

	/**
	 * 添加字典类型
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addDictionaryType(DictionaryType entity){
		return dictionaryDao.addDictionaryType(entity)>0?true:false;
	}

	/**
	 * 修改字典类型
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateDictionaryType(DictionaryType entity){
		return dictionaryDao.updateDictionaryType(entity)>0?true:false;
	}

	/**
	 * 添加字典
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean addDictionary(Dictionary entity){
		return dictionaryDao.addDictionary(entity)>0?true:false;
	}

	/**
	 * 修改字典
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateAuthority(Dictionary entity) throws Exception {
		dictionaryDao.updateDictionary(entity);
		throw new Exception("test");
	}


	/***
	 *条件查询字典类型
	 * @param entity
	 * @return
	 */
	public List<DictionaryType> getDictionaryTypes(DictionaryType entity){
		return dictionaryDao.getDictionaryTypes(entity);
	}

	/***
	 *条件查询字典
	 * @param entity
	 * @return
	 */
	public List<Dictionary> getDictionaries(Dictionary entity){
		return dictionaryDao.getDictionaries(entity);
	}

	/***
	 *根据TypeCode获取字典
	 * @param typeCode
	 * @return
	 */
	public List<Dictionary> getDictionariesByType(String typeCode){
		return dictionaryDao.getDictionariesByType(typeCode);
	}

	/***
	 *条件统计字典类型
	 * @param entity
	 * @return
	 */
	public int countDictionaryType(DictionaryType entity){
		return dictionaryDao.countDictionaryType(entity);
	}

	/***
	 *条件统计字典
	 * @param entity
	 * @return
	 */
	public int countDictionary(Dictionary entity){
		return dictionaryDao.countDictionary(entity);
	}


	/**
	 * 获取字典数据，类型与字典值集合键值对
	 * @return
	 */
	public List<DictionaryType> getDictionaryMap(){
		return dictionaryDao.getDictionaryMap();
	}
}
