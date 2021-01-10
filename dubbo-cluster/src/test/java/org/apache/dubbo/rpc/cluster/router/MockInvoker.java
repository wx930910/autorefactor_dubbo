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
import org.apache.dubbo.rpc.Invoker;
import org.mockito.Mockito;

public class MockInvoker {
	static public Invoker<String> mockInvoker2() {
		URL mockFieldVariableUrl = null;
		boolean mockFieldVariableAvailable = false;
		Invoker<String> mockInstance = Mockito.spy(Invoker.class);
		try {
			Mockito.doNothing().when(mockInstance).destroy();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).invoke(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl;
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAvailable;
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInterface();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	static public Invoker<String> mockInvoker1(URL url) {
		URL[] mockFieldVariableUrl = new URL[1];
		boolean mockFieldVariableAvailable = false;
		Invoker<String> mockInstance = Mockito.mock(Invoker.class,
				Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));
		mockFieldVariableUrl[0] = url;
		try {
			Mockito.doNothing().when(mockInstance).destroy();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).invoke(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl[0];
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAvailable;
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInterface();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	static public Invoker<String> mockInvoker3(boolean available) {
		URL mockFieldVariableUrl = null;
		boolean[] mockFieldVariableAvailable = new boolean[] { false };
		Invoker<String> mockInstance = Mockito.spy(Invoker.class);
		mockFieldVariableAvailable[0] = available;
		try {
			Mockito.doNothing().when(mockInstance).destroy();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).invoke(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl;
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAvailable[0];
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInterface();
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
