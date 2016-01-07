package com.example.thirdapp.http;

import com.example.thirdapp.http.AsyncHttpClient.HttpClientThread;

public interface OnCompleteListener {
	public void onComplete(HttpClientThread clientThread);
}
