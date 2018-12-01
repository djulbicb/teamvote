package com.bojan.teamvote.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.CanvasjsChartDao;
import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.service.CanvasjsChartService;
 
@Service
@Transactional
public class CanvasjsChartServiceImp implements CanvasjsChartService{
		
	@Autowired
	private CanvasjsChartDao canvasjsChartDao;
 
	public void setCanvasjsChartDao(CanvasjsChartDao canvasjsChartDao) {
		this.canvasjsChartDao = canvasjsChartDao;
	}
 
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		return canvasjsChartDao.getCanvasjsChartData();
	}

	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData(Question question) {
		return canvasjsChartDao.getCanvasjsChartData(question);
	}
}
