package com.ghqkl.schedule.service;


import com.ghqkl.schedule.model.UserAssetsBean;

public interface UserAssetsService extends BaseService<UserAssetsBean> {
	boolean sycnBSTAssets(String txd );
	
	boolean sycnSPGAssets(String txd );
	
	boolean sycnBTSystemAssets(String txd,String coin );
	
	boolean sycnETHAssets(UserAssetsBean userAssetsBean);
	
	void release();
	void releaseBST();
}
