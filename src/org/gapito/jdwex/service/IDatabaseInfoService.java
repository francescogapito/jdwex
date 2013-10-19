package org.gapito.jdwex.service;

import java.sql.SQLException;
import java.util.List;

import org.gapito.jdwex.model.ColumnInfo;
import org.gapito.jdwex.model.DatabaseInfo;
import org.gapito.jdwex.model.ServerInfo;
import org.gapito.jdwex.model.TableInfo;
import org.gapito.jdwex.model.ViewInfo;
import org.springframework.jdbc.core.RowCallbackHandler;

public interface IDatabaseInfoService {

	List<DatabaseInfo> loadDatabaseInfos(String id) throws SQLException;

	List<TableInfo> loadTableInfos(String id) throws SQLException;

	ServerInfo loadServerInfo() throws SQLException;

	List<ViewInfo> loadViewInfos(String id) throws SQLException;

	List<TableInfo> loadSystemTableInfos(String id) throws SQLException;

	List<ViewInfo> loadSystemViewInfos(String id) throws SQLException;

	List<ColumnInfo> loadColumnInfos(String catalog, String table) throws SQLException;

	void executeStatement(String sql, RowCallbackHandler rowCallbackHandler);
}
