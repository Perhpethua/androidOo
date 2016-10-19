package hr.oglasnik.perhpethua.oo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
