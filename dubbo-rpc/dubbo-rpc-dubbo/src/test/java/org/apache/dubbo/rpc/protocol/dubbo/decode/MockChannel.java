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
package org.apache.dubbo.rpc.protocol.dubbo.decode;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.remoting.Channel;
import org.mockito.Mockito;

public class MockChannel {
	static public Channel mockChannel1(Consumer consumer) {
		Consumer[] mockFieldVariableConsumer = new Consumer[1];
		Channel mockInstance = Mockito.spy(Channel.class);
		mockFieldVariableConsumer[0] = consumer;
		try {
			Mockito.doAnswer((stubInvo) -> {
				return new InetSocketAddress(20883);
			}).when(mockInstance).getLocalAddress();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isConnected();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getChannelHandler();
			Mockito.doAnswer((stubInvo) -> {
				Object message = stubInvo.getArgument(0);
				if (mockFieldVariableConsumer[0] != null) {
					mockFieldVariableConsumer[0].accept(message);
				}
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return new InetSocketAddress(NetUtils.getAvailablePort());
			}).when(mockInstance).getRemoteAddress();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isClosed();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).hasAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return new URL("dubbo", "localhost", 20880);
			}).when(mockInstance).getUrl();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	static public Channel mockChannel2() {
		Consumer mockFieldVariableConsumer = null;
		Channel mockInstance = Mockito.spy(Channel.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				return new InetSocketAddress(20883);
			}).when(mockInstance).getLocalAddress();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isConnected();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getChannelHandler();
			Mockito.doAnswer((stubInvo) -> {
				Object message = stubInvo.getArgument(0);
				if (mockFieldVariableConsumer != null) {
					mockFieldVariableConsumer.accept(message);
				}
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return new InetSocketAddress(NetUtils.getAvailablePort());
			}).when(mockInstance).getRemoteAddress();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isClosed();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).hasAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttribute(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return new URL("dubbo", "localhost", 20880);
			}).when(mockInstance).getUrl();
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
