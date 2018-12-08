package com.ghqkl.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.MiningServerBean;

@Mapper
public interface MiningServerDao extends BaseDao<MiningServerBean>{
	int queryMiningUserStatus(Integer userId);
	int resetMiningUserStatus();
}
