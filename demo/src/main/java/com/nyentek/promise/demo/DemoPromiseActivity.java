/*
 * Copyright (c) 2017, Nyentek LLC & Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nyentek.promise.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nyentek.promise.Promise;

public class DemoPromiseActivity extends AppCompatActivity implements View.OnClickListener, Promise.PromiseListener<String> {

	private Promise<String> demoPromise = new Promise<>();

	protected ProgressDialog progressDialog;
	protected AlertDialog.Builder dialogBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		View sendPromiseRequestButton = findViewById(R.id.send_promise_request_button);
		sendPromiseRequestButton.setOnClickListener(this);

		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setNeutralButton(R.string.dialog_okay, null);
		dialogBuilder.setCancelable(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
		demoPromise.addListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		demoPromise.removeListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.send_promise_request_button:
				DemoManager.demoPromiseRequest(demoPromise);
				break;
		}
	}

	@Override
	public void onPromiseUpdate(Promise<String> promise) {
		switch (promise.getStatus()) {
			case LOADING:
				progressDialog = ProgressDialog.show(this, getString(R.string.dialog_title_loading), "", true, false);
				break;
			case SUCCESS:
				if (progressDialog != null) {
					progressDialog.dismiss();
				}

				dialogBuilder.setTitle(getString(R.string.dialog_title_success));
				dialogBuilder.setMessage(getString(R.string.promise_signal_received));
				dialogBuilder.show();
				break;
			case FAILURE:
				if (progressDialog != null) {
					progressDialog.dismiss();
				}

				dialogBuilder.setTitle(getString(R.string.dialog_title_error));
				dialogBuilder.setMessage(promise.getErrorMessage());
				dialogBuilder.show();
				break;
		}
	}
}
