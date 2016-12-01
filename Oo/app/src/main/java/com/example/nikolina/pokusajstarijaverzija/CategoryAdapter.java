package com.example.nikolina.pokusajstarijaverzija;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolina on 14.10.2016..
 */

public class CategoryAdapter extends ArrayAdapter {
	List list = new ArrayList();
	Context context;

	public CategoryAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
	}

	public void add(Categories object) {
		super.add(object);
		list.add(object);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Nullable
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		row = convertView;
		CategoryHolder categoryHolder;
		if (row == null){
			LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = layoutInflater.inflate(R.layout.row_layout, parent, false);
			categoryHolder = new CategoryHolder();
			categoryHolder.tx_name = (TextView) row.findViewById(R.id.tx_name);
			categoryHolder.iv_jsonicon = (ImageView) row.findViewById(R.id.id_icon);

			row.setTag(categoryHolder);

		}else {
			categoryHolder = (CategoryHolder) row.getTag();
		}
		//set resources for Views
		Categories categories = (Categories) this.getItem(position);
		//set resource for category holder
//--------------------------------------------------------------------------------------------------
//-----------------------------------ONO ŠTO PIŠE U REDU -------------------------------------------
//--------------------------------------------------------------------------------------------------
		categoryHolder.tx_name.setText(categories.getName());
		String iconUrlPrefix = "http://slaviceva40.zapto.org/assets/img/categories/";
		String iconUrl = iconUrlPrefix + categories.getJsonicon();
//--------------------------------------------------------------------------------------------------
//------------------- prikazuje glavnu ikonu odabrane kategorije ukoliko nema zadane ikone ---------
//--------------------------------------------------------------------------------------------------
		String key = "png";
		if (categories.getJsonicon().equals("null") || !categories.getJsonicon().contains(key)){ // nemaju svi paramteri null --> fancy icon
			String iconUrlSufix = DisplayListView.jsonicon;
			String iconUrlOld = iconUrlPrefix + iconUrlSufix;
			Picasso.with(context).load(iconUrlOld) //"http://image.flaticon.com/teams/new/1-freepik.jpg"
					.placeholder(R.mipmap.logo) //optional
					.error(R.mipmap.logo) //ic_launcher
					.into(categoryHolder.iv_jsonicon, new com.squareup.picasso.Callback() {

						@Override
						public void onSuccess() { /*Toast.makeText(context,"Success icon", Toast.LENGTH_SHORT).show();*/ }
						@Override
						public void onError() {	Toast.makeText(context, "Error icon", Toast.LENGTH_SHORT).show(); }
					});
		}else {
			Picasso.with(context).load(iconUrl) //"http://image.flaticon.com/teams/new/1-freepik.jpg"
					.placeholder(R.mipmap.logo) //optional
					.error(R.mipmap.logo) //ic_launcher
					.into(categoryHolder.iv_jsonicon, new com.squareup.picasso.Callback() {

						@Override
						public void onSuccess() { /*Toast.makeText(context,"Success icon", Toast.LENGTH_SHORT).show();*/ }
						@Override
						public void onError() {	Toast.makeText(context, "Error icon", Toast.LENGTH_SHORT).show(); }
					});
			//categoryHolder.tx_name.setText(categories.getUrlFromJson());
		}
		return row;
	}

	static class CategoryHolder{
		TextView tx_name;
		ImageView iv_jsonicon;
	}
}
