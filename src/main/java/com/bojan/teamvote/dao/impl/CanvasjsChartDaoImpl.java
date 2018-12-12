package com.bojan.teamvote.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bojan.teamvote.dao.CanvasjsChartDao;
import com.bojan.teamvote.dao.QuestionDao;
import com.bojan.teamvote.model.CanvasjsChartData;
import com.bojan.teamvote.model.Opinion;
import com.bojan.teamvote.model.Question;

@Component
public class CanvasjsChartDaoImpl implements CanvasjsChartDao {

	@Autowired
	QuestionDao questionDao;
	
	public List<List<Map<Object, Object>>> getCanvasjsDataList() {
		 Map<Object,Object> map = null;
		 List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
		 List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();
		 
		 Question question = questionDao.findById(4).get();
		 
		 List<Opinion> opinions = question.getOpinions(); 
		 System.out.println(opinions);
		 int count = 0;
		 for (Opinion opinion : opinions) {
			 System.out.println(opinion.getText());
			 System.out.println(opinion.getVotes().size());
			 map = new HashMap<Object,Object>(); 
			 map.put("x", count++); 
			 map.put("y", opinion.getVotes().size());
			 map.put("label", opinion.getText());
			 dataPoints1.add(map);
		}
	 
			list.add(dataPoints1);
		 
		return list;
	}
	
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {

		return getCanvasjsDataList();
	}

	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData(Question question) {
		 Map<Object,Object> map = null;
		 List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
		 List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();
 
		 List<Opinion> opinions = question.getOpinions(); 
		 System.out.println(opinions);
		 for (Opinion opinion : opinions) {
			System.out.println(opinion.getVotes());
			System.out.println(opinion.getVotes().size());
		}
		 int count = 0;
		 for (Opinion opinion : opinions) {
			 System.out.println(opinion.getText());
			 System.out.println(opinion.getVotes().size());
			 map = new HashMap<Object,Object>(); 
			 map.put("x", count++); 
			 map.put("y", opinion.getVotes().size()/2);
			 map.put("label", opinion.getText());
			 dataPoints1.add(map);
		}
		 
		/*
			map = new HashMap<Object,Object>(); map.put("x", 1167589800000L); map.put("y", 188);dataPoints1.add(map);
			map = new HashMap<Object,Object>(); map.put("x", 1199125800000L); map.put("y", 213);dataPoints1.add(map);
			map = new HashMap<Object,Object>(); map.put("x", 1230748200000L); map.put("y", 213);dataPoints1.add(map);
			map = new HashMap<Object,Object>(); map.put("x", 1262284200000L); map.put("y", 219);dataPoints1.add(map);
			map = new HashMap<Object,Object>(); map.put("x", 1293820200000L); map.put("y", 207);dataPoints1.add(map);
			map = new HashMap<Object,Object>(); map.put("x", 1325356200000L); map.put("y", 167);dataPoints1.add(map);
			map = new HashMap<Object,Object>(); map.put("x", 1356978600000L); map.put("y", 136);dataPoints1.add(map);
		 */
	 
			list.add(dataPoints1);
		 
		return list;
	}

}
