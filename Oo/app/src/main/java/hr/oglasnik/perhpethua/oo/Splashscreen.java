package hr.oglasnik.perhpethua.oo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Nikolina on 27.10.2016..
 */

public class Splashscreen extends Activity {
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
	//called when activity is first created
	Thread splashTread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		StartAnimations();
	}

	private void StartAnimations() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		ImageView iv = (ImageView) findViewById(R.id.logo);
		iv.clearAnimation();
		iv.startAnimation(anim);

		splashTread = new Thread(){
			@Override
			public void run() {
				try{
					int waited = 0;
					//splash screen pause time
					while (waited < 3500){
						sleep(100);
						waited = 100;
					}
					Intent intent = new Intent(Splashscreen.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
					Splashscreen.this.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					Splashscreen.this.finish();
				}
			}
		};
		splashTread.start();
	}
}
