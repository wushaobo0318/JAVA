package com.ghqkl.schedule.service;


import java.util.List;

import com.ghqkl.schedule.model.PushLogBean;

public interface PushLogService extends BaseService<PushLogBean> {
	List<PushLogBean> queryNeedPushList(PushLogBean pushLog);
}
