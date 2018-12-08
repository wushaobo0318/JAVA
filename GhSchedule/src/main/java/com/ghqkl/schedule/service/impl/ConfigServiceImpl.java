package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.ConfigDao;
import com.ghqkl.schedule.model.ConfigBean;
import com.ghqkl.schedule.service.ConfigService;
import com.github.pagehelper.PageInfo;

@Service
public class ConfigServiceImpl implements ConfigService{
	
	@Autowired
	private ConfigDao dao;

	@Override
	public int insert(ConfigBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(ConfigBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public List<ConfigBean> queryList(ConfigBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int update(ConfigBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public ConfigBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public int count(ConfigBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<ConfigBean> pageList(ConfigBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigBean getConfigByKey(String key) {
		// TODO Auto-generated method stub
		return dao.getConfigByKey(key);
	}
	

}
