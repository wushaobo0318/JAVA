package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.CoinPriceLogDao;
import com.ghqkl.schedule.model.CoinPriceLogBean;
import com.ghqkl.schedule.service.CoinPriceLogService;
import com.github.pagehelper.PageInfo;
@Service
public class CoinPriceLogServiceImpl implements CoinPriceLogService {

	@Autowired
	private CoinPriceLogDao dao;

	@Override
	public int insert(CoinPriceLogBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(CoinPriceLogBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(CoinPriceLogBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public CoinPriceLogBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<CoinPriceLogBean> queryList(CoinPriceLogBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(CoinPriceLogBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<CoinPriceLogBean> pageList(CoinPriceLogBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
