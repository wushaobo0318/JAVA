package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.MiningLogDao;
import com.ghqkl.schedule.model.MiningLogBean;
import com.ghqkl.schedule.service.MiningLogService;
import com.github.pagehelper.PageInfo;

@Service
public class MiningLogServiceImpl implements MiningLogService{
	
	@Autowired
	private MiningLogDao dao;

	@Override
	public int insert(MiningLogBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(MiningLogBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(MiningLogBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public MiningLogBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<MiningLogBean> queryList(MiningLogBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public Double queryTodayMiningSumByUser(MiningLogBean miniLog) {
		// TODO Auto-generated method stub
		return dao.queryTodayMiningSumByUser(miniLog);
	}

	@Override
	public int count(MiningLogBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clearLogNotSaveWallet(String time) {
		// TODO Auto-generated method stub
		return dao.clearLogNotSaveWallet(time);
	}

	@Override
	public PageInfo<MiningLogBean> pageList(MiningLogBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
