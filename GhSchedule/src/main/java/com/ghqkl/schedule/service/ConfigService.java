package com.ghqkl.schedule.service;

import com.ghqkl.schedule.model.ConfigBean;

public interface ConfigService extends BaseService<ConfigBean>{
	public ConfigBean getConfigByKey(String key);
}
