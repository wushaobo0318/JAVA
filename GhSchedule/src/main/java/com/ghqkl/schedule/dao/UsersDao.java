package com.ghqkl.schedule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ghqkl.schedule.model.UsersBean;

@Mapper
public interface UsersDao extends BaseDao<UsersBean>{
	int updateByPhone(UsersBean user);
	
	List<UsersBean> queryMiningList(@Param("spoceNum")Integer spoceNum,@Param("lastNum")Integer lastNum);
}
