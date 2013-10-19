package org.gapito.jdwex.vo;

import java.util.List;

public class QueryVO {

	private String text;
	
	private List<QueryResultVO> results;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<QueryResultVO> getResults() {
		return results;
	}

	public void setResults(List<QueryResultVO> results) {
		this.results = results;
	}
}
