package org.gapito.jdwex.ctrl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.gapito.jdwex.model.ServerInfo;
import org.gapito.jdwex.service.IDatabaseInfoService;
import org.gapito.jdwex.ui.ServerTreeModel;
import org.gapito.jdwex.ui.ServerTreeNode;
import org.gapito.jdwex.ui.ServerTreeNodeType;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ServerController extends SelectorComposer<Window> {

	private static final long serialVersionUID = 8874572295471279565L;

	@Wire("#serverTree")
	private Tree serverTree;

	@Wire("#menuPopupTreeItem")
	private Menupopup menuPopupTreeItem;

	@WireVariable(value="databaseInfoService")
	private IDatabaseInfoService srv;

	@AfterCompose(superclass = true)
	public void init() {
	}

	public TreeModel<ServerTreeNode> getTreeModel() throws SQLException {
		ServerInfo si = srv.loadServerInfo();
		ServerTreeNode root = new ServerTreeNode(si.getName(), ServerTreeNodeType.SERVER, si.getUrl(), null);
		return new ServerTreeModel(root, srv);
	}

	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return super.doCatch(ex);
	}

	@Override
	public void doFinally() throws Exception {
		super.doFinally();
	}

	@Listen("onClick = menuitem[value='PROPS']")
	public void onClickProps(Event ce) {
		Menuitem mi = (Menuitem) ce.getTarget();
		Treeitem ti = serverTree.getSelectedItem();
		ServerTreeNode node = (ServerTreeNode) ti.getValue();
		alert(mi.getValue() + " on " + node.getId());
	}

	@Listen("onClick = menuitem[value='REFRESH']")
	public void onClickRefresh(Event ce) {
		Menuitem mi = (Menuitem) ce.getTarget();
		Treeitem ti = serverTree.getSelectedItem();
		ServerTreeNode node = (ServerTreeNode) ti.getValue();
		alert(mi.getValue() + " on " + node.getId());
	}

	@Listen("onClick = menuitem[value='SELECTTOP']")
	public void onClickSelectTop(Event ce) {
		Menuitem mi = (Menuitem) ce.getTarget();
		Treeitem ti = serverTree.getSelectedItem();
		ServerTreeNode node = (ServerTreeNode) ti.getValue();
		alert(mi.getValue() + " on " + node.getId());
	}

	@Listen("onClick = menuitem[value='SELECTALL']")
	public void onClickSelectAll(Event ce) {
		Menuitem mi = (Menuitem) ce.getTarget();
		Treeitem ti = serverTree.getSelectedItem();
		if (ti == null) {
			return;
		}
		ServerTreeNode node = (ServerTreeNode) ti.getValue();
		String command = mi.getValue() + " on " + node.getId();
		//addChildrenTab(command, node.getId());
	}
}
