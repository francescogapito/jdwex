package org.gapito.jdwex.ui;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.gapito.jdwex.model.ColumnInfo;
import org.gapito.jdwex.model.DatabaseInfo;
import org.gapito.jdwex.model.TableInfo;
import org.gapito.jdwex.model.ViewInfo;
import org.gapito.jdwex.service.IDatabaseInfoService;
import org.zkoss.lang.SystemException;
import org.zkoss.zul.AbstractTreeModel;

public class ServerTreeModel extends AbstractTreeModel<ServerTreeNode> {

	private static final long serialVersionUID = -4701796073780156790L;

	private IDatabaseInfoService service;

	public ServerTreeModel(ServerTreeNode root, IDatabaseInfoService databaseInfoService) {
		super(root);
		this.service = databaseInfoService;
	}

	@Override
	public boolean isLeaf(ServerTreeNode node) {
		return node.getChildren() != null && getChildCount(node) == 0;
	}

	@Override
	public ServerTreeNode getChild(ServerTreeNode parent, int index) {
		try {
			if (parent.getChildren() == null) {
				loadChildren(parent);
			}
			return parent.getChildren().get(index);
		} catch (SQLException sqle) {
			throw new SystemException(sqle);
		}
	}

	private void loadChildren(ServerTreeNode node) throws SQLException {
		switch (node.getType()) {
		case SERVER:
			List<DatabaseInfo> dbinfos = service.loadDatabaseInfos(node.getId());
			for (DatabaseInfo databaseInfo : dbinfos) {
				node.add(new ServerTreeNode(databaseInfo.getId(), ServerTreeNodeType.DATABASE, databaseInfo.getName(), node));
			}
			break;
		case DATABASE:
			node.add(new ServerTreeNode(ServerTreeNodeType.TABLES.name(), ServerTreeNodeType.TABLES, "Tables", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.VIEW.name(), ServerTreeNodeType.VIEWS, "Views", node));
			List<TableInfo> tables = service.loadTableInfos(node.getId());
			node.getChildren().get(0).add(new ServerTreeNode(ServerTreeNodeType.SYSTABLES.name(), ServerTreeNodeType.SYSTABLES, "System Tables", node.getChildren().get(0)));
			node.getChildren().get(1).add(new ServerTreeNode(ServerTreeNodeType.SYSVIEWS.name(), ServerTreeNodeType.SYSVIEWS, "System Views", node.getChildren().get(1)));
			for (TableInfo tableInfo : tables) {
				node.getChildren().get(0).add(new ServerTreeNode(tableInfo.getId(), ServerTreeNodeType.TABLE, tableInfo.getTableSchem() + "." + tableInfo.getTableName(), node.getChildren().get(0)));
			}
			List<ViewInfo> views = service.loadViewInfos(node.getId());
			for (ViewInfo viewInfo : views) {
				node.getChildren().get(1).add(new ServerTreeNode(viewInfo.getId(), ServerTreeNodeType.VIEW, viewInfo.getTableSchem() + "." + viewInfo.getTableName(), node.getChildren().get(1)));
			}
			break;
		case SYSTABLES:
			List<TableInfo> systables = service.loadSystemTableInfos(node.getParent().getParent().getId());
			for (TableInfo sysTableInfo : systables) {
				node.add(new ServerTreeNode(sysTableInfo.getId(), ServerTreeNodeType.SYSTABLE, sysTableInfo.getTableSchem() + "." + sysTableInfo.getTableName(), node));
			}
			break;
		case SYSVIEWS:
			List<ViewInfo> sysviews = service.loadSystemViewInfos(node.getParent().getParent().getId());
			for (ViewInfo sysViewInfo : sysviews) {
				node.add(new ServerTreeNode(sysViewInfo.getId(), ServerTreeNodeType.SYSVIEW, sysViewInfo.getTableSchem() + "." + sysViewInfo.getTableName(), node));
			}
			break;
		case TABLE:
			node.add(new ServerTreeNode(ServerTreeNodeType.COLUMNS.name(), ServerTreeNodeType.COLUMNS, "Columns", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.KEYS.name(), ServerTreeNodeType.KEYS, "Keys", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.CONSTRAINTS.name(), ServerTreeNodeType.CONSTRAINTS, "Constraints", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.TRIGGERS.name(), ServerTreeNodeType.TRIGGERS, "Triggers", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.INDEXES.name(), ServerTreeNodeType.INDEXES, "Indexes", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.STATISTICS.name(), ServerTreeNodeType.STATISTICS, "Statistics", node));
			break;
		case VIEW:
			node.add(new ServerTreeNode(ServerTreeNodeType.COLUMNS.name(), ServerTreeNodeType.COLUMNS, "Columns", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.TRIGGERS.name(), ServerTreeNodeType.TRIGGERS, "Triggers", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.INDEXES.name(), ServerTreeNodeType.INDEXES, "Indexes", node));
			node.add(new ServerTreeNode(ServerTreeNodeType.STATISTICS.name(), ServerTreeNodeType.STATISTICS, "Statistics", node));
			break;
		case COLUMNS:
			if (node.getParent().getType() == ServerTreeNodeType.TABLE) {
				String catalog = node.getParent().getParent().getParent().getId();
				String table = node.getParent().getId();
				List<ColumnInfo> cols = service.loadColumnInfos(catalog, table);
				for (ColumnInfo colInfo : cols) {
					ServerTreeNode colNode = new ServerTreeNode(colInfo.getColumnName(), ServerTreeNodeType.COLUMN, colInfo.getDescription(), node);
					colNode.setChildren(new LinkedList<ServerTreeNode>());
 					node.add(colNode);
				}
			}
			break;
		default:
			break;
		}

		if (node.getChildren() == null) {
			node.setChildren(new LinkedList<ServerTreeNode>());
		}
	}

	@Override
	public int getChildCount(ServerTreeNode parent) {
		if (parent.getChildren() == null) {
			try {
				loadChildren(parent);
			} catch (SQLException e) {
				throw new SystemException(e);
			}
		}
		return parent.getChildren().size();
	}
}
