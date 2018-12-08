package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.UsersDao;
import com.ghqkl.schedule.model.UsersBean;
import com.ghqkl.schedule.service.UsersService;
import com.github.pagehelper.PageInfo;

@Service
public class UsersServiceImpl implements UsersService{
	
	@Autowired
	private UsersDao dao;

	@Override
	public int insert(UsersBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(UsersBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UsersBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UsersBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<UsersBean> queryList(UsersBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public List<UsersBean> queryMiningList(Integer spoceNum, Integer lastNum) {
		// TODO Auto-generated method stub
		return dao.queryMiningList(spoceNum, lastNum);
	}

	@Override
	public int count(UsersBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<UsersBean> pageList(UsersBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
