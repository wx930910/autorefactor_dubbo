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
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.ExecutionException;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.Request;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.remoting.transport.dispatcher.connection.ConnectionOrderedChannelHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConnectChannelHandlerTest extends WrappedChannelHandlerTest {

	public Channel mockChannel1(AtomicInteger count) {
		ChannelHandler mockFieldVariableHandler = null;
		boolean[] mockFieldVariableClosing = new boolean[] { false };
		URL mockFieldVariableUrl = null;
		Map<String, Object> mockFieldVariableMap = new HashMap<String, Object>();
		boolean[] mockFieldVariableIsClosed = new boolean[1];
		Channel mockInstance = Mockito.spy(Channel.class);
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
				mockFieldVariableClosing[0] = true;
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
				Object message = stubInvo.getArgument(0);
				Assertions.assertTrue(((Response) message).isHeartbeat(), "response.heartbeat");
				count.incrementAndGet();
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableHandler;
			}).when(mockInstance).getChannelHandler();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	@BeforeEach
	public void setUp() throws Exception {
		handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(true), url);
	}

	@Test
	public void test_Connect_Blocked() throws RemotingException {
		handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(false), url);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) getField(handler, "connectionExecutor", 1);
		Assertions.assertEquals(1, executor.getMaximumPoolSize());

		int runs = 20;
		int taskCount = runs * 2;
		for (int i = 0; i < runs; i++) {
			handler.connected(MockedChannel.mockChannel1());
			handler.disconnected(MockedChannel.mockChannel1());
			Assertions.assertTrue(executor.getActiveCount() <= 1, executor.getActiveCount() + " must <=1");
		}
		// queue.size
		Assertions.assertEquals(taskCount - 1, executor.getQueue().size());

		for (int i = 0; i < taskCount; i++) {
			if (executor.getCompletedTaskCount() < taskCount) {
				sleep(100);
			}
		}
		Assertions.assertEquals(taskCount, executor.getCompletedTaskCount());
	}

	@Test // biz error should not throw and affect biz thread.
	public void test_Connect_Biz_Error() throws RemotingException {
		handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(true), url);
		handler.connected(MockedChannel.mockChannel1());
	}

	@Test // biz error should not throw and affect biz thread.
	public void test_Disconnect_Biz_Error() throws RemotingException {
		handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(true), url);
		handler.disconnected(MockedChannel.mockChannel1());
	}

	@Test
	public void test_Connect_Execute_Error() throws RemotingException {
		Assertions.assertThrows(ExecutionException.class, () -> {
			handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(false), url);
			ThreadPoolExecutor executor = (ThreadPoolExecutor) getField(handler, "connectionExecutor", 1);
			executor.shutdown();
			handler.connected(MockedChannel.mockChannel1());
		});
	}

	@Test
	public void test_Disconnect_Execute_Error() throws RemotingException {
		Assertions.assertThrows(ExecutionException.class, () -> {
			handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(false), url);
			ThreadPoolExecutor executor = (ThreadPoolExecutor) getField(handler, "connectionExecutor", 1);
			executor.shutdown();
			handler.disconnected(MockedChannel.mockChannel1());
		});
	}

	// throw ChannelEventRunnable.runtimeExeception(int logger) not in execute
	// exception
	@Test // (expected = RemotingException.class)
	public void test_MessageReceived_Biz_Error() throws RemotingException {
		handler.received(MockedChannel.mockChannel1(), "");
	}

	// throw ChannelEventRunnable.runtimeExeception(int logger) not in execute
	// exception
	@Test
	public void test_Caught_Biz_Error() throws RemotingException {
		handler.caught(MockedChannel.mockChannel1(), new BizException());
	}

	@Test
	@Disabled("FIXME")
	public void test_Received_InvokeInExecuter() throws RemotingException {
		Assertions.assertThrows(ExecutionException.class, () -> {
			handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(false), url);
			ThreadPoolExecutor executor = (ThreadPoolExecutor) getField(handler, "SHARED_EXECUTOR", 1);
			executor.shutdown();
			executor = (ThreadPoolExecutor) getField(handler, "executor", 1);
			executor.shutdown();
			handler.received(MockedChannel.mockChannel1(), "");
		});
	}

	/**
	 * Events do not pass through the thread pool and execute directly on the IO
	 */
	@SuppressWarnings("deprecation")
	@Disabled("Heartbeat is processed in HeartbeatHandler not WrappedChannelHandler.")
	@Test
	public void test_Received_Event_invoke_direct() throws RemotingException {
		handler = new ConnectionOrderedChannelHandler(mockChannelHandler1(false), url);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) getField(handler, "SHARED_EXECUTOR", 1);
		executor.shutdown();
		executor = (ThreadPoolExecutor) getField(handler, "executor", 1);
		executor.shutdown();
		Request req = new Request();
		req.setHeartbeat(true);
		final AtomicInteger count = new AtomicInteger(0);
		handler.received(mockChannel1(count), req);
		Assertions.assertEquals(1, count.get(), "channel.send must be invoke");
	}
}
