package com.ghqkl.schedule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.PushLogBean;

@Mapper
public interface PushLogDao extends BaseDao<PushLogBean>{
	List<PushLogBean> queryNeedPushList(PushLogBean pushLog);
}