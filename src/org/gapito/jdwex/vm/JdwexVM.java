package org.gapito.jdwex.vm;

import java.sql.SQLException;
import java.util.List;

import org.gapito.jdwex.model.ServerInfo;
import org.gapito.jdwex.service.IDatabaseInfoService;
import org.gapito.jdwex.ui.ServerTreeModel;
import org.gapito.jdwex.ui.ServerTreeNode;
import org.gapito.jdwex.ui.ServerTreeNodeType;
import org.gapito.jdwex.vo.QueryVO;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class JdwexVM {

	@WireVariable(value = "databaseInfoService")
	private IDatabaseInfoService srv;

	private ServerTreeNode selectedTreeNode;

	private ServerTreeModel serverTreeModel;

	private QueryVO activeQuery;
	
	private List<QueryVO> queries;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) throws SQLException {
		fetchData();
	}

	private void fetchData() throws SQLException {
		ServerInfo si = srv.loadServerInfo();
		ServerTreeNode root = new ServerTreeNode(si.getName(), ServerTreeNodeType.SERVER, si.getUrl(), null);
		this.serverTreeModel = new ServerTreeModel(root, srv);
	}

	@Command
	public void query() {
		System.out.println("ciao");
		QueryVO q = new QueryVO();
		String table = selectedTreeNode.getId();
		String db = selectedTreeNode.getParent().getParent().getId();
		if (selectedTreeNode.getType() == ServerTreeNodeType.TABLE){
			
		}
		q.setText(String.format("SELECT * FROM %s.%s.%s", db, "dbo", table));
	}

	public ServerTreeNode getSelectedTreeNode() {
		return selectedTreeNode;
	}

	public void setSelectedTreeNode(ServerTreeNode selectedTreeNode) {
		this.selectedTreeNode = selectedTreeNode;
	}

	public ServerTreeModel getServerTreeModel() {
		return serverTreeModel;
	}

	public void setServerTreeModel(ServerTreeModel serverTreeModel) {
		this.serverTreeModel = serverTreeModel;
	}

	public QueryVO getActiveQuery() {
		return activeQuery;
	}

	public void setActiveQuery(QueryVO activeQuery) {
		this.activeQuery = activeQuery;
	}

	public List<QueryVO> getQueries() {
		return queries;
	}

	public void setQueries(List<QueryVO> queries) {
		this.queries = queries;
	}
}
