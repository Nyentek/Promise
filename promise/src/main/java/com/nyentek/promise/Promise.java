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
package com.nyentek.promise;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Promise<T> {

	public interface PromiseListener<T> {

		void onPromiseUpdate(Promise<T> promise);
	}

	private static Handler Handler = new Handler(Looper.getMainLooper());

	public enum Status {
		PENDING,
		LOADING,
		SUCCESS,
		FAILURE
	}

	private T model;
	private Status status;
	private Exception exception;

	public Promise() {
		status = Status.PENDING;
		listeners = new HashSet<>();
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Exception getException() {
		return exception;
	}

	public String getErrorMessage() {
		return exception != null ? exception.getMessage() != null ? exception.getMessage() : "" : "";
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	// Listeners
	private final Set<PromiseListener<T>> listeners;

	public Promise<T> addListener(PromiseListener<T> listener) {
		listeners.add(listener);
		return this;
	}

	public Promise<T> removeListener(PromiseListener<T> listener) {
		listeners.remove(listener);
		return this;
	}

	public void notifyUpdate() {
		Handler.post(new Runnable() {
			@Override
			public void run() {
				for (PromiseListener<T> listener : new ArrayList<>(listeners)) {
					listener.onPromiseUpdate(Promise.this);
				}
			}
		});
	}

	public void _notifyUpdate() {
		for (PromiseListener<T> listener : new ArrayList<>(listeners)) {
			listener.onPromiseUpdate(Promise.this);
		}
	}
}
