package org.gapito.jdwex.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.gapito.jdwex.model.ColumnInfo;
import org.gapito.jdwex.model.DatabaseInfo;
import org.gapito.jdwex.model.ServerInfo;
import org.gapito.jdwex.model.TableInfo;
import org.gapito.jdwex.model.ViewInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

@Component("databaseInfoService")
public class DatabaseInfoService implements IDatabaseInfoService {

	private JdbcTemplate jdbcTemplate;

	public DatabaseInfoService() {
		super();
	}

	@Resource(name = "dataSource")
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<DatabaseInfo> loadDatabaseInfos(String id) throws SQLException {
		Connection conn = null;

		ResultSet rs = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			rs = conn.getMetaData().getCatalogs();
			List<DatabaseInfo> catalogs = new LinkedList<DatabaseInfo>();
			while (rs.next()) {
				String catalog = rs.getString("TABLE_CAT");
				DatabaseInfo di = new DatabaseInfo();
				di.setId(catalog);
				di.setName(catalog);
				catalogs.add(di);
			}
			return catalogs;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}

		}

	}

	@Override
	public List<TableInfo> loadTableInfos(String id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<TableInfo> tables = new ArrayList<TableInfo>();
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			rs = conn.getMetaData().getTables(id, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				TableInfo ti = new TableInfo();
				ti.setId(rs.getString("TABLE_NAME"));
				ti.setTableCat(rs.getString("TABLE_CAT"));
				ti.setTableSchem(rs.getString("TABLE_SCHEM"));
				ti.setTableName(rs.getString("TABLE_NAME"));
				ti.setTableType(rs.getString("TABLE_TYPE"));
				// ti.setTypeCat(rsTables.getString("TYPE_CAT"));
				// ti.setTypeSchem(rsTables.getString("TYPE_SCHEM"));
				// ti.setTypeName(rsTables.getString("TYPE_NAME"));
				// ti.setSelfRefColName(rsTables.getString("SELF_REFERENCING_COL_NAME"));
				// ti.setRefGeneration(rsTables.getString("REF_GENERATION"));
				tables.add(ti);
			}
			Collections.sort(tables, new Comparator<TableInfo>() {
				@Override
				public int compare(TableInfo o1, TableInfo o2) {
					return o1.getTableName().compareTo(o2.getTableName());
				}
			});
			return tables;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public ServerInfo loadServerInfo() throws SQLException {
		Connection conn = null;
		try {
			ServerInfo si = new ServerInfo();
			conn = jdbcTemplate.getDataSource().getConnection();
			String url = conn.getMetaData().getURL();
			si.setUrl(url);
			return si;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

	}

	@Override
	public List<ViewInfo> loadViewInfos(String id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<ViewInfo> views = new ArrayList<ViewInfo>();
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			rs = conn.getMetaData().getTables(id, "dbo", null, new String[] { "VIEW" });
			while (rs.next()) {
				ViewInfo vi = new ViewInfo();
				vi.setId(rs.getString("TABLE_NAME"));
				vi.setTableCat(rs.getString("TABLE_CAT"));
				vi.setTableSchem(rs.getString("TABLE_SCHEM"));
				vi.setTableName(rs.getString("TABLE_NAME"));
				vi.setTableType(rs.getString("TABLE_TYPE"));
				// ti.setTypeCat(rsTables.getString("TYPE_CAT"));
				// ti.setTypeSchem(rsTables.getString("TYPE_SCHEM"));
				// ti.setTypeName(rsTables.getString("TYPE_NAME"));
				// ti.setSelfRefColName(rsTables.getString("SELF_REFERENCING_COL_NAME"));
				// ti.setRefGeneration(rsTables.getString("REF_GENERATION"));
				views.add(vi);
			}
			Collections.sort(views, new Comparator<ViewInfo>() {
				@Override
				public int compare(ViewInfo o1, ViewInfo o2) {
					return o1.getTableName().compareTo(o2.getTableName());
				}
			});
			return views;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<ViewInfo> loadSystemViewInfos(String id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<ViewInfo> views = new ArrayList<ViewInfo>();
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			String[] schemas = { "sys", "INFORMATION_SCHEMA" };
			for (int i = 0; i < schemas.length; i++) {
				rs = conn.getMetaData().getTables(id, schemas[i], null, new String[] { "VIEW" });
				while (rs.next()) {
					ViewInfo vi = new ViewInfo();
					vi.setTableCat(rs.getString("TABLE_CAT"));
					vi.setTableSchem(rs.getString("TABLE_SCHEM"));
					vi.setTableName(rs.getString("TABLE_NAME"));
					vi.setTableType(rs.getString("TABLE_TYPE"));
					// ti.setTypeCat(rsTables.getString("TYPE_CAT"));
					// ti.setTypeSchem(rsTables.getString("TYPE_SCHEM"));
					// ti.setTypeName(rsTables.getString("TYPE_NAME"));
					// ti.setSelfRefColName(rsTables.getString("SELF_REFERENCING_COL_NAME"));
					// ti.setRefGeneration(rsTables.getString("REF_GENERATION"));
					views.add(vi);
				}
				rs.close();
			}
			Collections.sort(views, new Comparator<ViewInfo>() {
				@Override
				public int compare(ViewInfo o1, ViewInfo o2) {
					return o1.getTableName().compareTo(o2.getTableName());
				}
			});
			return views;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<TableInfo> loadSystemTableInfos(String id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<TableInfo> tables = new ArrayList<TableInfo>();
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			rs = conn.getMetaData().getTables(id, null, null, new String[] { "SYSTEM TABLE" });
			while (rs.next()) {
				TableInfo ti = new TableInfo();
				ti.setTableCat(rs.getString("TABLE_CAT"));
				ti.setTableSchem(rs.getString("TABLE_SCHEM"));
				ti.setTableName(rs.getString("TABLE_NAME"));
				ti.setTableType(rs.getString("TABLE_TYPE"));
				// ti.setTypeCat(rsTables.getString("TYPE_CAT"));
				// ti.setTypeSchem(rsTables.getString("TYPE_SCHEM"));
				// ti.setTypeName(rsTables.getString("TYPE_NAME"));
				// ti.setSelfRefColName(rsTables.getString("SELF_REFERENCING_COL_NAME"));
				// ti.setRefGeneration(rsTables.getString("REF_GENERATION"));
				tables.add(ti);
			}
			Collections.sort(tables, new Comparator<TableInfo>() {
				@Override
				public int compare(TableInfo o1, TableInfo o2) {
					return o1.getTableName().compareTo(o2.getTableName());
				}
			});
			return tables;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<ColumnInfo> loadColumnInfos(String catalog, String table) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			rs = conn.getMetaData().getColumns(catalog, null, table, null);
			while (rs.next()) {
				ColumnInfo ci = new ColumnInfo();
				ci.setTableCat(rs.getString("TABLE_CAT"));
				ci.setTableSchem(rs.getString("TABLE_SCHEM"));
				ci.setTableName(rs.getString("TABLE_NAME"));
				ci.setColumnName(rs.getString("COLUMN_NAME"));
				ci.setDataType(rs.getInt("DATA_TYPE"));
				ci.setTypeName(rs.getString("TYPE_NAME"));
				ci.setColumnSize(rs.getInt("COLUMN_SIZE"));
				ci.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
				ci.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
				ci.setNullable(rs.getInt("NULLABLE"));
				ci.setRemarks(rs.getString("REMARKS"));
				ci.setColumnDef(rs.getString("COLUMN_DEF"));
				ci.setCharOctetLength(rs.getInt("CHAR_OCTET_LENGTH"));
				ci.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
				ci.setIsNullable(rs.getString("IS_NULLABLE"));
				// ci.setScopeCatalog(rs.getString("SCOPE_CATALOG"));
				// ci.setScopeSchema(rs.getString("SCOPE_SCHEMA"));
				// ci.setScopeTable(rs.getString("SCOPE_TABLE"));
				// ci.setSourceDataType(rs.getShort("SOURCE_DATA_TYPE"));
				ci.setIsAutoincrement(rs.getString("IS_AUTOINCREMENT"));
				// ci.setIsGeneratedColumn(rs.getString("IS_GENERATEDCOLUMN"));

				columns.add(ci);
			}
			Collections.sort(columns, new Comparator<ColumnInfo>() {
				@Override
				public int compare(ColumnInfo o1, ColumnInfo o2) {
					return o1.getColumnName().compareTo(o2.getColumnName());
				}
			});
			return columns;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public void executeStatement(String sql, RowCallbackHandler rowCallbackHandler) {
		jdbcTemplate.query(sql, rowCallbackHandler);
		
	}
}
