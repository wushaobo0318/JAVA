package com.ghqkl.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.ConfigBean;

@Mapper
public interface ConfigDao extends BaseDao<ConfigBean>{
	 /**
	  * 查询配置当日的释放比例
	  * @return
	  */
	public ConfigBean getConfigByKey(String key);
}
