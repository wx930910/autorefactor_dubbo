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
package org.apache.dubbo.rpc.cluster.router;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.mockito.Mockito;

public class MockInvoker {
	static public Invoker<String> mockInvoker2() {
		boolean mockFieldVariableAvailable = false;
		URL mockFieldVariableUrl = null;
		Invoker<String> mockInstance = Mockito.spy(Invoker.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAvailable;
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInterface();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).invoke(Mockito.any(Invocation.class));
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl;
			}).when(mockInstance).getUrl();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	static public Invoker<String> mockInvoker1(URL url) {
		boolean mockFieldVariableAvailable = false;
		URL[] mockFieldVariableUrl = new URL[1];
		Invoker<String> mockInstance = Mockito.mock(Invoker.class,
				Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));
		mockFieldVariableUrl[0] = url;
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAvailable;
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInterface();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).invoke(Mockito.any(Invocation.class));
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl[0];
			}).when(mockInstance).getUrl();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	static public Invoker<String> mockInvoker3(boolean available) {
		boolean[] mockFieldVariableAvailable = new boolean[] { false };
		URL mockFieldVariableUrl = null;
		Invoker<String> mockInstance = Mockito.spy(Invoker.class);
		mockFieldVariableAvailable[0] = available;
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAvailable[0];
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInterface();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).invoke(Mockito.any(Invocation.class));
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl;
			}).when(mockInstance).getUrl();
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
