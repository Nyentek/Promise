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

import com.nyentek.promise.Promise;

public class DemoManager {

	public static void demoPromiseRequest(final Promise<String> promise) {
		int failSucceeedRNG = (int) Math.ceil(Math.random() * 4);
		if (failSucceeedRNG == 4) {
			promise.setStatus(Promise.Status.FAILURE);
			promise.setException(new Exception("There has been a generated Error"));
			promise.notifyUpdate();
		} else {
			promise.setStatus(Promise.Status.SUCCESS);
			promise.notifyUpdate();
		}
	}
}
