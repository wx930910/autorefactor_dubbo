/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.registry.dubbo;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.remoting.exchange.support.Replier;
import org.mockito.Mockito;

/**
 * MockedClient
 *
 */
public class MockedClient {

	// private String host;

	// private int port;

	static public ExchangeClient mockExchangeClient1(String host, int port, boolean connected) {
		boolean[] mockFieldVariableConnected = new boolean[1];
		InetSocketAddress[] mockFieldVariableAddress = new InetSocketAddress[1];
		Object[] mockFieldVariableReceived = new Object[1];
		Object[] mockFieldVariableSent = new Object[1];
		Object[] mockFieldVariableInvoked = new Object[1];
		Replier<?>[] mockFieldVariableHandler = new Replier[1];
		boolean[] mockFieldVariableClosed = new boolean[] { false };
		ExchangeClient mockInstance = Mockito.spy(ExchangeClient.class);
		mockFieldVariableAddress[0] = new InetSocketAddress(host, port);
		mockFieldVariableConnected[0] = connected;
		mockFieldVariableReceived[0] = null;
		try {
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getLocalAddress();
			Mockito.doAnswer((stubInvo) -> {
				Object msg = stubInvo.getArgument(0);
				int timeout = stubInvo.getArgument(1);
				return mockInstance.request(msg, timeout, null);
			}).when(mockInstance).request(Mockito.any(Object.class), Mockito.anyInt());
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getExchangeHandler();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAddress[0];
			}).when(mockInstance).getRemoteAddress();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableClosed[0];
			}).when(mockInstance).isClosed();
			Mockito.doAnswer((stubInvo) -> {
				Object msg = stubInvo.getArgument(0);
				mockFieldVariableSent[0] = msg;
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableConnected[0];
			}).when(mockInstance).isConnected();
			Mockito.doAnswer((stubInvo) -> {
				Object msg = stubInvo.getArgument(0);
				ExecutorService executor = stubInvo.getArgument(1);
				return mockInstance.request(msg, 0, executor);
			}).when(mockInstance).request(Mockito.any(Object.class), Mockito.any(ExecutorService.class));
			Mockito.doAnswer((stubInvo) -> {
				mockInstance.close();
				return null;
			}).when(mockInstance).close(Mockito.anyInt());
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttribute(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).hasAttribute(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				Object msg = stubInvo.getArgument(0);
				mockFieldVariableInvoked[0] = msg;
				return (CompletableFuture<Object>) new CompletableFuture<Object>() {
					public Object get() throws InterruptedException, ExecutionException {
						return mockFieldVariableReceived[0];
					}

					public Object get(int timeoutInMillis)
							throws InterruptedException, ExecutionException, TimeoutException {
						return mockFieldVariableReceived[0];
					}

					public boolean isDone() {
						return true;
					}
				};
			}).when(mockInstance).request(Mockito.any(Object.class), Mockito.anyInt(),
					Mockito.any(ExecutorService.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getChannelHandler();
			Mockito.doAnswer((stubInvo) -> {
				Object msg = stubInvo.getArgument(0);
				return mockInstance.request(msg, null);
			}).when(mockInstance).request(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				mockFieldVariableClosed[0] = true;
				return null;
			}).when(mockInstance).close();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	// private ChannelListener listener;

}
