package com.finley.module.dictionary.dao;

import com.finley.module.dictionary.entity.Dictionary;
import com.finley.module.dictionary.entity.DictionaryType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 郑远锋 on 2017/3/1.
 */
public interface DictionaryDao {

    public int addDictionaryType(DictionaryType dictionaryType);

    public int addDictionary(Dictionary dictionary);

    public int updateDictionaryType(DictionaryType dictionaryType);

    public int updateDictionary(Dictionary dictionary);

    public int countDictionaryType(DictionaryType dictionaryType);

    public int countDictionary(Dictionary dictionary);

    public List<DictionaryType> getDictionaryTypes(DictionaryType dictionaryType);

    public List<Dictionary> getDictionaries(Dictionary dictionary);

    public List<Dictionary> getDictionariesByType(@Param("typeCode")String typeCode);

    public List<DictionaryType> getDictionaryMap();


}
