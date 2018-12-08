package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.CoinPriceDao;
import com.ghqkl.schedule.model.CoinPriceBean;
import com.ghqkl.schedule.service.CoinPriceService;
import com.github.pagehelper.PageInfo;
@Service
public class CoinPriceServiceImpl implements CoinPriceService {

	@Autowired
	private CoinPriceDao dao;

	@Override
	public int insert(CoinPriceBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(CoinPriceBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(CoinPriceBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public CoinPriceBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<CoinPriceBean> queryList(CoinPriceBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public List<CoinPriceBean> queryListByCoinName(CoinPriceBean t) {
		// TODO Auto-generated method stub
		return dao.queryListByCoinName(t);
	}

	@Override
	public int count(CoinPriceBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<CoinPriceBean> pageList(CoinPriceBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
