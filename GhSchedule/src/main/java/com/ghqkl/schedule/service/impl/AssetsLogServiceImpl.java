package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.AssetsLogDao;
import com.ghqkl.schedule.model.AssetsLogBean;
import com.ghqkl.schedule.service.AssetsLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AssetsLogServiceImpl implements AssetsLogService{
	
	@Autowired
	private AssetsLogDao dao;

	@Override
	public int insert(AssetsLogBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(AssetsLogBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(AssetsLogBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public AssetsLogBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<AssetsLogBean> queryList(AssetsLogBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(AssetsLogBean t) {
		// TODO Auto-generated method stub
		return dao.count(t);
	}

	@Override
	public PageInfo<AssetsLogBean> pageList(AssetsLogBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<AssetsLogBean> list = dao.queryList(t);
		PageInfo<AssetsLogBean> pageInfo= new PageInfo<AssetsLogBean>(list);
		return pageInfo;
	}

}
