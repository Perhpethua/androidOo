package com.example.nikolina.pokusajstarijaverzija;

/**
 * Created by Nikolina on 14.10.2016..
 */

public class Categories {
	private String name;
	private String urlFromJson;
	private String parentId;
	private String id;
	private String catEnd;
	private String urlsufix;
	private String jsonicon;

//class constructor ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public Categories(String id, String parentId, String name, String urlFromJson, String catEnd, String urlsufix, String json) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.urlFromJson = urlFromJson;
		this.catEnd = catEnd;
		this.urlsufix = urlsufix;
		this.jsonicon = json;
	}
//getters and setters ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrlFromJson() {
		return urlFromJson;
	}

	public String getParentId() {
		return parentId;
	}

	public String getCatEnd() {
		return catEnd;
	}

	public String getUrlsufix() { return urlsufix; }

	public String getJsonicon() { return jsonicon; }
//---------------------------------------------------------------------------------------
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrlFromJson(String urlFromJson) {
		this.urlFromJson = urlFromJson;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setCatEnd(String catEnd) {
		this.catEnd = catEnd;
	}

	public void setUrlsufix(String urlsufix) {
		this.urlsufix = urlsufix;
	}

	public void setJsonicon(String jsonicon) { this.jsonicon = jsonicon; }
}
