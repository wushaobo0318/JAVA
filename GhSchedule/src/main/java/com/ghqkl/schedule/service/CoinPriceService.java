package com.ghqkl.schedule.service;


import java.util.List;

import com.ghqkl.schedule.model.CoinPriceBean;

public interface CoinPriceService extends BaseService<CoinPriceBean> {
	List<CoinPriceBean> queryListByCoinName(CoinPriceBean t);
	
}
