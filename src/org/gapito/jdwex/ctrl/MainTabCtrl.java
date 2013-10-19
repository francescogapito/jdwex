package org.gapito.jdwex.ctrl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gapito.jdwex.model.RowResult;
import org.gapito.jdwex.service.IDatabaseInfoService;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainTabCtrl extends SelectorComposer<Window> {

	private static final long serialVersionUID = 2469542298117527850L;

	@Wire("#mainTabbox")
	private Tabbox mainTabbox;

	@Wire("#tbQuery")
	private Textbox tbQuery;

	@WireVariable(value = "databaseInfoService")
	private IDatabaseInfoService srv;

	private List<String> columns;
	private List<RowResult> results;

	@Listen("onExecute = #queryPanel")
	public void onExecute(ForwardEvent event) {
		String query = tbQuery.getValue();
		srv.executeStatement(query, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet arg0) throws SQLException {
				System.out.println("ciao");

			}
		});
		System.out.println("ciao");
	}

	private void addChildrenTab(String title, String table) {
		Tabpanel tabpanel = new Tabpanel();
		Map<String, String> map = new HashMap<String, String>();
		map.put("query", String.format("SELECT * FROM %s", table));
		Executions.createComponents("mainTabpanel.zul", tabpanel, map);
		Tab tab = new Tab(title);
		tab.setClosable(true);
		if (mainTabbox.getTabs() == null) {
			mainTabbox.appendChild(new Tabs());
			mainTabbox.appendChild(new Tabpanels());
		}

		mainTabbox.getTabs().appendChild(tab);
		mainTabbox.getTabpanels().appendChild(tabpanel);
	}

	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<RowResult> getResults() {
		return results;
	}

	public void setResults(List<RowResult> results) {
		this.results = results;
	}
}
