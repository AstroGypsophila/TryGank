package com.gypsophila.trygank.activity.personcenter;

import android.os.Bundle;
import android.widget.TextView;

import com.gypsophila.trygank.R;
import com.gypsophila.trygank.base.AppBaseActivity;
import com.gypsophila.trygank.engine.User;

public class PersonCenterActivity extends AppBaseActivity {
	TextView tvPersonCenter;
	
	@Override
	protected void initVariables() {

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_personcenter);
		
		tvPersonCenter = (TextView)findViewById(R.id.tvPersonCenter);
		tvPersonCenter.setText(
				User.getInstance().getUserName());
	}

	
	
	@Override
	protected void loadData() {

	}
}
