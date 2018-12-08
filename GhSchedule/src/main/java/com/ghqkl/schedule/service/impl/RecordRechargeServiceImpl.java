package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.RecordRechargeDao;
import com.ghqkl.schedule.model.RecordRechargeBean;
import com.ghqkl.schedule.service.RecordRechargeService;
import com.github.pagehelper.PageInfo;

@Service
public class RecordRechargeServiceImpl implements RecordRechargeService{
	
	@Autowired
	private RecordRechargeDao dao;

	@Override
	public int insert(RecordRechargeBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(RecordRechargeBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(RecordRechargeBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public RecordRechargeBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<RecordRechargeBean> queryList(RecordRechargeBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(RecordRechargeBean t) {
		// TODO Auto-generated method stub
		return dao.count(t);
	}

	@Override
	public PageInfo<RecordRechargeBean> pageList(RecordRechargeBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
