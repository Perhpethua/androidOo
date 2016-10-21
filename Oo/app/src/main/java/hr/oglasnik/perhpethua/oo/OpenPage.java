package hr.oglasnik.perhpethua.oo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OpenPage extends AppCompatActivity {

	String jelikraj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_page);

		jelikraj = getIntent().getExtras().getString("kraj");

		TextView prikazi = (TextView) findViewById(R.id.id_prikazi);
		prikazi.setText(jelikraj);
	}
}
