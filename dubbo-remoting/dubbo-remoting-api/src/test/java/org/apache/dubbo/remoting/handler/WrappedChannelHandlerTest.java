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

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.transport.dispatcher.WrappedChannelHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WrappedChannelHandlerTest {
	public ChannelHandler mockChannelHandler1(boolean invokeWithBizError) {
		ConcurrentHashSet<Channel> mockFieldVariableChannels = new ConcurrentHashSet<Channel>();
		boolean[] mockFieldVariableInvokeWithBizError = new boolean[1];
		ChannelHandler mockInstance = Mockito.spy(ChannelHandler.class);
		mockFieldVariableInvokeWithBizError[0] = invokeWithBizError;
		try {
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				Throwable exception = stubInvo.getArgument(1);
				throw new RemotingException(channel, exception);
			}).when(mockInstance).caught(Mockito.any(Channel.class), Mockito.any(Throwable.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				if (mockFieldVariableInvokeWithBizError[0]) {
					throw new RemotingException(channel, "test received biz error");
				}
				sleep(20);
				return null;
			}).when(mockInstance).received(Mockito.any(Channel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				Object message = stubInvo.getArgument(1);
				channel.send(message);
				return null;
			}).when(mockInstance).sent(Mockito.any(Channel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				if (mockFieldVariableInvokeWithBizError[0]) {
					throw new RemotingException(channel, "test disconnect biz error");
				}
				sleep(20);
				return null;
			}).when(mockInstance).disconnected(Mockito.any(Channel.class));
			Mockito.doAnswer((stubInvo) -> {
				Channel channel = stubInvo.getArgument(0);
				if (mockFieldVariableInvokeWithBizError[0]) {
					throw new RemotingException(channel, "test connect biz error");
				}
				sleep(20);
				return null;
			}).when(mockInstance).connected(Mockito.any(Channel.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	WrappedChannelHandler handler;
	URL url = URL.valueOf("test://10.20.30.40:1234");

	@BeforeEach
	public void setUp() throws Exception {
		handler = new WrappedChannelHandler(mockChannelHandler1(true), url);
	}

	@Test
	public void test_Execute_Error() throws RemotingException {

	}

	protected Object getField(Object obj, String fieldName, int parentdepth) {
		try {
			Class<?> clazz = obj.getClass();
			Field field = null;
			for (int i = 0; i <= parentdepth && field == null; i++) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field f : fields) {
					if (f.getName().equals(fieldName)) {
						field = f;
						break;
					}
				}
				clazz = clazz.getSuperclass();
			}
			if (field != null) {
				field.setAccessible(true);
				return field.get(obj);
			} else {
				throw new NoSuchFieldException();
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Test
	public void test_Connect_Biz_Error() throws RemotingException {
		Assertions.assertThrows(RemotingException.class, () -> handler.connected(MockedChannel.mockChannel1()));
	}

	@Test
	public void test_Disconnect_Biz_Error() throws RemotingException {
		Assertions.assertThrows(RemotingException.class, () -> handler.disconnected(MockedChannel.mockChannel1()));
	}

	@Test
	public void test_MessageReceived_Biz_Error() throws RemotingException {
		Assertions.assertThrows(RemotingException.class, () -> handler.received(MockedChannel.mockChannel1(), ""));
	}

	@Test
	public void test_Caught_Biz_Error() throws RemotingException {
		try {
			handler.caught(MockedChannel.mockChannel1(), new BizException());
			fail();
		} catch (Exception e) {
			Assertions.assertEquals(BizException.class, e.getCause().getClass());
		}
	}

	class BizException extends RuntimeException {
		private static final long serialVersionUID = -7541893754900723624L;
	}
}