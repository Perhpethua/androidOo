package com.example.nikolina.pokusajstarijaverzija;

public class lista_kategorija{

	private String categoryName;

	public lista_kategorija(String categoryName){
		this.setCategoryName(categoryName);
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
