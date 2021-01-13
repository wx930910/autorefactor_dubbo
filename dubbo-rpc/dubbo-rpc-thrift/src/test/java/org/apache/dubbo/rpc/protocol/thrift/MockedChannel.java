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
package org.apache.dubbo.rpc.protocol.thrift;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.mockito.Mockito;

public class MockedChannel {

	static public Channel mockChannel1(URL url) {
		URL[] mockFieldVariableUrl = new URL[1];
		Channel mockInstance = Mockito.spy(Channel.class);
		mockFieldVariableUrl[0] = url;
		try {
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isConnected();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl[0];
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isClosed();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).hasAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getRemoteAddress();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getChannelHandler();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getLocalAddress();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

}
