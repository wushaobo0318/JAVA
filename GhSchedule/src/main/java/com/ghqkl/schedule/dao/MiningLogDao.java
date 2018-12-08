package com.ghqkl.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.MiningLogBean;

@Mapper
public interface MiningLogDao extends BaseDao<MiningLogBean>{
	Double queryTodayMiningSumByUser(MiningLogBean miniLog);
	int clearLogNotSaveWallet(String time);
}
