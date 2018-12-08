package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.PushLogDao;
import com.ghqkl.schedule.model.PushLogBean;
import com.ghqkl.schedule.service.PushLogService;
import com.github.pagehelper.PageInfo;

@Service
public class PushLogImpl implements PushLogService{
	
	@Autowired
	private PushLogDao dao;

	@Override
	public int insert(PushLogBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(PushLogBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PushLogBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PushLogBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<PushLogBean> queryList(PushLogBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public List<PushLogBean> queryNeedPushList(PushLogBean pushLog) {
		// TODO Auto-generated method stub
		return dao.queryNeedPushList(pushLog);
	}

	@Override
	public int count(PushLogBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<PushLogBean> pageList(PushLogBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
