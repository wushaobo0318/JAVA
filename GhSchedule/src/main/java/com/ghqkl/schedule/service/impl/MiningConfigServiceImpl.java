package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.MiningConfigDao;
import com.ghqkl.schedule.model.MiningConfigBean;
import com.ghqkl.schedule.service.MiningConfigService;
import com.github.pagehelper.PageInfo;

@Service
public class MiningConfigServiceImpl implements MiningConfigService{
	
	@Autowired
	private MiningConfigDao dao;

	@Override
	public int insert(MiningConfigBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(MiningConfigBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(MiningConfigBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public MiningConfigBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<MiningConfigBean> queryList(MiningConfigBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(MiningConfigBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<MiningConfigBean> pageList(MiningConfigBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
