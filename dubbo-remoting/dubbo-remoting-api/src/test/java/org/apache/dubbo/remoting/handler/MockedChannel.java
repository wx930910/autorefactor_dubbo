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
package org.apache.dubbo.remoting.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.mockito.Mockito;

public class MockedChannel {
	static public Channel mockChannel1() {
		ChannelHandler mockFieldVariableHandler = null;
		URL mockFieldVariableUrl = null;
		Map<String, Object> mockFieldVariableMap = new HashMap<String, Object>();
		boolean[] mockFieldVariableIsClosed = new boolean[1];
		Channel mockInstance = Mockito.mock(Channel.class,
				Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));
		try {
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getRemoteAddress();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				mockFieldVariableMap.remove(key);
				return null;
			}).when(mockInstance).removeAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return mockFieldVariableMap.get(key);
			}).when(mockInstance).getAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isConnected();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getLocalAddress();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl;
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).startClose();
			Mockito.doAnswer((stubInvo) -> {
				Object message = stubInvo.getArgument(0);
				mockInstance.send(message);
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class), Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				mockInstance.close();
				return null;
			}).when(mockInstance).close(Mockito.anyInt());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableIsClosed[0];
			}).when(mockInstance).isClosed();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				Object value = stubInvo.getArgument(1);
				mockFieldVariableMap.put(key, value);
				return null;
			}).when(mockInstance).setAttribute(Mockito.any(String.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return mockFieldVariableMap.containsKey(key);
			}).when(mockInstance).hasAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				mockFieldVariableIsClosed[0] = true;
				return null;
			}).when(mockInstance).close();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableHandler;
			}).when(mockInstance).getChannelHandler();
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
