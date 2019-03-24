package com.sydml.framework.ioc.bean;

import com.sydml.common.utils.CastUtil;
import com.sydml.common.utils.CollectionUtil;

import java.util.Map;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 22:14
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public boolean isEmpty(){
        return CollectionUtil.isEmpty(paramMap);
    }
}
