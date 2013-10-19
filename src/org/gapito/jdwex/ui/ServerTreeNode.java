package org.gapito.jdwex.ui;

import java.util.LinkedList;
import java.util.List;

public class ServerTreeNode {

	private String id;
	private String description;
	private List<ServerTreeNode> children;
	private ServerTreeNodeType type;
	private ServerTreeNode parent;

	public ServerTreeNode(String id, ServerTreeNodeType type, String description, ServerTreeNode parent) {
		super();
		this.id = id;
		this.description = description;
		this.type = type;
		this.parent = parent;
	}

	public ServerTreeNodeType getType() {
		return type;
	}

	public void setType(ServerTreeNodeType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ServerTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ServerTreeNode> children) {
		this.children = children;
	}

	public void add(ServerTreeNode serverTreeNode) {
		if (this.children == null) {
			this.children = new LinkedList<ServerTreeNode>();
		}
		this.children.add(serverTreeNode);
	}

	public ServerTreeNode getParent() {
		return parent;
	}

	public void setParent(ServerTreeNode parent) {
		this.parent = parent;
	}

	public String getContextMenu() {
		return (this.type == ServerTreeNodeType.TABLE || this.type == ServerTreeNodeType.VIEW) ? "ctxMenuFull" : "ctxMenu";
	}

}
