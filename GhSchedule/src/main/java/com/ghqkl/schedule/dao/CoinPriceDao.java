package com.ghqkl.schedule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.CoinPriceBean;

@Mapper
public interface CoinPriceDao extends BaseDao<CoinPriceBean>{
	List<CoinPriceBean> queryListByCoinName(CoinPriceBean t);
	
}