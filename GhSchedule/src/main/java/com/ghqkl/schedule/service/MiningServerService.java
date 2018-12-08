package com.ghqkl.schedule.service;

import com.ghqkl.schedule.model.MiningServerBean;

public interface MiningServerService extends BaseService<MiningServerBean>{
	int queryMiningUserStatus(Integer userId);
	
	int resetMiningUserStatus();
}
