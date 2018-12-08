package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.MiningServerDao;
import com.ghqkl.schedule.model.MiningServerBean;
import com.ghqkl.schedule.service.MiningServerService;
import com.github.pagehelper.PageInfo;

@Service
public class MiningServerServiceImpl implements MiningServerService{
	
	@Autowired
	private MiningServerDao dao;

	@Override
	public int insert(MiningServerBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(MiningServerBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(MiningServerBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public MiningServerBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<MiningServerBean> queryList(MiningServerBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(MiningServerBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int queryMiningUserStatus(Integer userId) {
		// TODO Auto-generated method stub
		return dao.queryMiningUserStatus(userId);
	}

	@Override
	public int resetMiningUserStatus() {
		// TODO Auto-generated method stub
		return dao.resetMiningUserStatus();
	}

	@Override
	public PageInfo<MiningServerBean> pageList(MiningServerBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
