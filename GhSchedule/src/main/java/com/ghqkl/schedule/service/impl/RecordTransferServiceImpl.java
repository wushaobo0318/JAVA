package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.RecordTransferDao;
import com.ghqkl.schedule.model.RecordTransferBean;
import com.ghqkl.schedule.service.RecordTransferService;
import com.github.pagehelper.PageInfo;

@Service
public class RecordTransferServiceImpl implements RecordTransferService{
	
	@Autowired
	private RecordTransferDao dao;

	@Override
	public int insert(RecordTransferBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(RecordTransferBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(RecordTransferBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public RecordTransferBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<RecordTransferBean> queryList(RecordTransferBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(RecordTransferBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<RecordTransferBean> pageList(RecordTransferBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
