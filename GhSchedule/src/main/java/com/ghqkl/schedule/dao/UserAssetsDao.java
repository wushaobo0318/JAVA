package com.ghqkl.schedule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.UserAssetsBean;

@Mapper
public interface UserAssetsDao extends BaseDao<UserAssetsBean>{
	int release(UserAssetsBean user);
	List<UserAssetsBean> queryNeedReleaseList1(UserAssetsBean user);
	List<UserAssetsBean> queryNeedReleaseList2(UserAssetsBean user);
	int updateUserAsse(UserAssetsBean user);
	List<UserAssetsBean> queryUsersList(UserAssetsBean user);
	UserAssetsBean selectByUserAssetsId(int userAssetsId);
}
