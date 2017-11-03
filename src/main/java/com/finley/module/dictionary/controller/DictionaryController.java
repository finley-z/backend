package com.finley.module.dictionary.controller;

import com.finley.core.pagination.PageVo;
import com.finley.core.respone.ResultVo;
import com.finley.module.dictionary.entity.Dictionary;
import com.finley.module.dictionary.entity.DictionaryType;
import com.finley.module.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 郑远锋 on 2017/3/5.
 */

@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController {
    @Autowired
    DictionaryService dictionaryService;

    @RequestMapping(value = "dictionary")
    public String dictionary() {
        return "dictionary/dictionary";
    }

    @ResponseBody
    @RequestMapping(value = "saveDictionaryType")
    public ResultVo saveDictionaryType(DictionaryType dictionaryType) {
        ResultVo resultVo = new ResultVo(true);
        try {
            if (dictionaryType.getTypeId() == null) {
                dictionaryService.addDictionaryType(dictionaryType);
            } else {
                dictionaryService.updateDictionaryType(dictionaryType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setStatus(false);
        }
        return resultVo;
    }

    @ResponseBody
    @RequestMapping(value = "saveDictionary")
    public ResultVo saveDictionary(Dictionary dictionary) {
        ResultVo resultVo = new ResultVo(true);
        try {
            if (dictionary.getDictionaryId() == null) {
                dictionaryService.addDictionary(dictionary);
            } else {
                dictionaryService.updateAuthority(dictionary);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setStatus(false);
        }
        return resultVo;
    }


    /**
     * 字典类型列表
     *
     * @param dictionaryType
     */
    @RequestMapping("/dictionaryTypeList")
    @ResponseBody
    public PageVo<DictionaryType> dictionaryTypeList(DictionaryType dictionaryType) {
        PageVo<DictionaryType> pageVo = new PageVo<DictionaryType>();
        pageVo.setData(dictionaryService.getDictionaryTypes(dictionaryType));
        int count = dictionaryService.countDictionaryType(dictionaryType);
        pageVo.setRecordsTotal(count);
        pageVo.setRecordsFiltered(count);
        return pageVo;
    }


    /**
     * 字典类型列表
     *
     * @param dictionaryType
     */
    @RequestMapping("/getTypeList")
    @ResponseBody
    public List<DictionaryType> getTypeList(DictionaryType dictionaryType) {
      return dictionaryService.getDictionaryTypes(dictionaryType);
    }


    /**
     * 字典列表
     *
     * @param dictionary
     */
    @RequestMapping("/dictionaryList")
    @ResponseBody
    public PageVo<Dictionary> dictionaryList(Dictionary dictionary) {
        PageVo<Dictionary> pageVo = new PageVo<Dictionary>();
        pageVo.setData(dictionaryService.getDictionaries(dictionary));
        int count = dictionaryService.countDictionary(dictionary);
        pageVo.setRecordsTotal(count);
        pageVo.setRecordsFiltered(count);
        return pageVo;
    }


    /**
     * 获取字典类型
     *
     * @param dictionaryType
     */
    @RequestMapping("/getDictionaryType")
    @ResponseBody
    public DictionaryType getDictionaryType(DictionaryType dictionaryType) {
        List<DictionaryType> result = dictionaryService.getDictionaryTypes(dictionaryType);
        if (result != null) {
            return result.get(0);
        }
        return null;
    }

    /**
     * 获取字典
     *
     * @param dictionary
     */
    @RequestMapping("/getDictionary")
    @ResponseBody
    public Dictionary getDictionary(Dictionary dictionary) {
        List<Dictionary> result = dictionaryService.getDictionaries(dictionary);
        if (result != null) {
            return result.get(0);
        }
        return null;
    }

    /**
     * 获取字典数据，类型与字典值集合键值对
     *
     * @return
     */
    @RequestMapping("/getDictionaryMap")
    @ResponseBody
    public Map<String,List<Dictionary>> getDictionaryMap() {
        Map<String,List<Dictionary>> dictionaryMap=new HashMap<String, List<Dictionary>>();

        List<DictionaryType> dictionaryTypeList=dictionaryService.getDictionaryMap();
        Iterator<DictionaryType> it=dictionaryTypeList.iterator();
        while(it.hasNext()){
            DictionaryType dictionaryType=it.next();
            dictionaryMap.put(dictionaryType.getTypeCode(),dictionaryType.getDictionaryList());
        }
        return dictionaryMap;
    }

    /**
     * 字典列表
     *
     * @param typeCode
     */
    @RequestMapping("/getDictionaries/{typeCode}")
    @ResponseBody
    public List<Dictionary> getDictionaries(@PathVariable("typeCode")String typeCode) {
        return dictionaryService.getDictionariesByType(typeCode);
    }

}
