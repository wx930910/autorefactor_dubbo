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

import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.RemotingException;
import org.mockito.Mockito;

public class MockChannelHandler {
	static public ChannelHandler mockChannelHandler1() {
		ConcurrentHashSet<Channel> mockFieldVariableChannels = new ConcurrentHashSet<Channel>();
		ChannelHandler mockInstance = Mockito.spy(ChannelHandler.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				Object message = stubInvo.getArgument(1);
				channel.send(message);
				return null;
			}).when(mockInstance).received(Mockito.any(Channel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				Throwable exception = stubInvo.getArgument(1);
				throw new RemotingException(channel, exception);
			}).when(mockInstance).caught(Mockito.any(Channel.class), Mockito.any(Throwable.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				mockFieldVariableChannels.add(channel);
				return null;
			}).when(mockInstance).connected(Mockito.any(Channel.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				Object message = stubInvo.getArgument(1);
				channel.send(message);
				return null;
			}).when(mockInstance).sent(Mockito.any(Channel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				mockFieldVariableChannels.remove(channel);
				return null;
			}).when(mockInstance).disconnected(Mockito.any(Channel.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
