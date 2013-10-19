package org.gapito.jdwex.model;

public class ServerInfo {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return url == null ? "ND" : url.substring(0, 30);
	}

}
