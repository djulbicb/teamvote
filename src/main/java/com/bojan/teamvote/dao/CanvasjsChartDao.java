
package com.bojan.teamvote.dao;

import java.util.*;

import com.bojan.teamvote.model.Question;

public interface CanvasjsChartDao {
	List<List<Map<Object, Object>>> getCanvasjsChartData();

	List<List<Map<Object, Object>>> getCanvasjsChartData(Question question);
}
