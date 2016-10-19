package hr.oglasnik.perhpethua.oo;

/**
 * Created by Nikolina on 14.10.2016..
 */

public class Categories {
	private String name;
	private String urlFromJson;
	private String parentId;
	private String id;

//class constructor ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public Categories(String id, String parentId, String name, String urlFromJson) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.urlFromJson = urlFromJson;
	}
//getters and setters ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlFromJson() {
		return urlFromJson;
	}

	public void setUrlFromJson(String urlFromJson) {
		this.urlFromJson = urlFromJson;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
