package com.bojan.teamvote.service;
import java.util.*;

import com.bojan.teamvote.model.Question;

public interface CanvasjsChartService {
	List<List<Map<Object, Object>>> getCanvasjsChartData();

	List<List<Map<Object, Object>>> getCanvasjsChartData(Question question);
}
