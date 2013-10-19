package org.gapito.jdwex.model;

public class QueryData {
	private TableInfo tableInfo;
	private String query;
	private boolean editable = false;

	public QueryData(TableInfo ti) {
		this.tableInfo = ti;
	}

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
