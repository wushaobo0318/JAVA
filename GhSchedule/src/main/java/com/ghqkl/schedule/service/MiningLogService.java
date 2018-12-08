package com.ghqkl.schedule.service;

import com.ghqkl.schedule.model.MiningLogBean;

public interface MiningLogService extends BaseService<MiningLogBean>{
	Double queryTodayMiningSumByUser(MiningLogBean miniLog);
	int clearLogNotSaveWallet(String time);
}
