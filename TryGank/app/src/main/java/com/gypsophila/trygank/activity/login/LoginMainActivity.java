package com.gypsophila.trygank.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.activity.gank.TestGankApi;
import com.gypsophila.trygank.activity.news.NewsActivity;
import com.gypsophila.trygank.base.AppBaseActivity;
import com.gypsophila.trygank.engine.AppConstants;
import com.gypsophila.trygank.engine.User;


public class LoginMainActivity extends AppBaseActivity {

	private static final int LOGIN_REDIRECT_OUTSIDE = 3000;	//登录后跳转到其它页面
	private static final int LOGIN_REDIRECT_INSIDE = 3001;	//登录后仍然在本页面

	@Override
	protected void initVariables() {

	}	

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login_main);
		
		Button btnLogin1 = (Button)findViewById(R.id.btnLogin1);
		btnLogin1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginMainActivity.this, 
						LoginActivity.class);
				startActivity(intent);
			}		
		});

		Button btnLogin2 = (Button)findViewById(R.id.btnLogin2);
		btnLogin2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(User.getInstance().isLogin()) {
					gotoNewsActivity();
				} else {
					Intent intent = new Intent(LoginMainActivity.this, 
							LoginActivity.class);
					intent.putExtra(AppConstants.NeedCallback, true);
					startActivityForResult(intent, LOGIN_REDIRECT_OUTSIDE);
				}
			}		
		});

		Button btnLogin3 = (Button)findViewById(R.id.btnLogin3);
		btnLogin3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(User.getInstance().isLogin()) {
					changeText();
				} else {
					Intent intent = new Intent(LoginMainActivity.this, 
							LoginActivity.class);
					intent.putExtra(AppConstants.NeedCallback, true);
					startActivityForResult(intent, LOGIN_REDIRECT_INSIDE);
				}
			}		
		});
		
		Button btnLogout = (Button)findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				User.getInstance().reset();
			}		
		});
//		startActivity(new Intent(this, TestGankApi.class));
	}

	@Override
	protected void loadData() {

	}
	
	@Override
	protected void onActivityResult(int requestCode, 
			int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		
		switch (requestCode) {
		case LOGIN_REDIRECT_OUTSIDE:
			gotoNewsActivity();
			break;
		case LOGIN_REDIRECT_INSIDE:
			changeText();
			break;
		default:
			break;
		}
	} 
		
	private void gotoNewsActivity() {
		Intent intent = new Intent(LoginMainActivity.this, 
				NewsActivity.class);
		startActivity(intent);
	}
	
	private void changeText() {
		TextView textView1 = (TextView)findViewById(R.id.textView1);
		textView1.setText("1");
	}
}