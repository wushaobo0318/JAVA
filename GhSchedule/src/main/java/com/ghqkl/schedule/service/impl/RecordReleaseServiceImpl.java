package com.ghqkl.schedule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.RecordReleaseDao;
import com.ghqkl.schedule.model.RecordReleaseBean;
import com.ghqkl.schedule.service.RecordReleaseService;
import com.github.pagehelper.PageInfo;
@Service
public class RecordReleaseServiceImpl implements RecordReleaseService {

	@Resource
	private RecordReleaseDao recordReleaseDao;
	
	@Override
	public int insert(RecordReleaseBean t) {
		// TODO Auto-generated method stub
		return recordReleaseDao.insert(t);
	}

	@Override
	public int delete(RecordReleaseBean t) {
		// TODO Auto-generated method stub
		return recordReleaseDao.delete(t);
	}

	@Override
	public int update(RecordReleaseBean t) {
		// TODO Auto-generated method stub
		return recordReleaseDao.update(t);
	}

	@Override
	public int count(RecordReleaseBean t) {
		// TODO Auto-generated method stub
		return recordReleaseDao.count(t);
	}

	@Override
	public RecordReleaseBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return recordReleaseDao.getById(Id);
	}

	@Override
	public List<RecordReleaseBean> queryList(RecordReleaseBean t) {
		// TODO Auto-generated method stub
		return recordReleaseDao.queryList(t);
	}

	@Override
	public PageInfo<RecordReleaseBean> pageList(RecordReleaseBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
