package com.ghqkl.schedule.service;

import java.util.List;

import com.ghqkl.schedule.model.UsersBean;

public interface UsersService extends BaseService<UsersBean>{
	
	List<UsersBean> queryMiningList(Integer spoceNum,Integer lastNum);
	
}
