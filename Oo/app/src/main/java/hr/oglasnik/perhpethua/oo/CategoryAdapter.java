package hr.oglasnik.perhpethua.oo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolina on 14.10.2016..
 */

public class CategoryAdapter extends ArrayAdapter {
	List list = new ArrayList();
	public CategoryAdapter(Context context, int resource) {
		super(context, resource);
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
			row.setTag(categoryHolder);

		}else {
			categoryHolder = (CategoryHolder) row.getTag();
		}
		//set resources for Views
		Categories categories = (Categories) this.getItem(position);
		//set resource for category holder
//ONO ŠTO PIŠE U REDU ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		categoryHolder.tx_name.setText(categories.getName());
		//categoryHolder.tx_name.setText(categories.getUrlFromJson());
		return row;
	}

	static class CategoryHolder{
		TextView tx_name;
	}
}
