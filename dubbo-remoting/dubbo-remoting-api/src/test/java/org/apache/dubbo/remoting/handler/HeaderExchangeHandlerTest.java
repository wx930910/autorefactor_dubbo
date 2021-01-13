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

import static org.apache.dubbo.common.constants.CommonConstants.READONLY_EVENT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Constants;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.ExchangeHandler;
import org.apache.dubbo.remoting.exchange.Request;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

//TODO response test
public class HeaderExchangeHandlerTest {

	public Channel mockChannel4(Person requestdata, AtomicInteger count, Request request) {
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
				Object message = stubInvo.getArgument(0);
				Response res = (Response) message;
				Assertions.assertEquals(request.getId(), res.getId());
				Assertions.assertEquals(request.getVersion(), res.getVersion());
				Assertions.assertEquals(Response.OK, res.getStatus());
				Assertions.assertEquals(requestdata, res.getResult());
				Assertions.assertNull(res.getErrorMessage());
				count.incrementAndGet();
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
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

	public Channel mockChannel3(AtomicInteger count, Request request) {
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
				Object message = stubInvo.getArgument(0);
				Response res = (Response) message;
				Assertions.assertEquals(request.getId(), res.getId());
				Assertions.assertEquals(request.getVersion(), res.getVersion());
				Assertions.assertEquals(Response.SERVICE_ERROR, res.getStatus());
				Assertions.assertNull(res.getResult());
				Assertions.assertTrue(res.getErrorMessage().contains(BizException.class.getName()));
				count.incrementAndGet();
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
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

	public Channel mockChannel2(AtomicInteger count, Request request) {
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
				Object message = stubInvo.getArgument(0);
				Response res = (Response) message;
				Assertions.assertEquals(request.getId(), res.getId());
				Assertions.assertEquals(request.getVersion(), res.getVersion());
				Assertions.assertEquals(Response.BAD_REQUEST, res.getStatus());
				Assertions.assertNull(res.getResult());
				Assertions.assertTrue(res.getErrorMessage().contains(BizException.class.getName()));
				count.incrementAndGet();
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
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
				return mockFieldVariableHandler;
			}).when(mockInstance).getChannelHandler();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	public Channel mockChannel1() {
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
				return mockFieldVariableHandler;
			}).when(mockInstance).getChannelHandler();
			Mockito.doAnswer((stubInvo) -> {
				Assertions.fail();
				return null;
			}).when(mockInstance).send(Mockito.any(Object.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	@Test
	public void test_received_request_oneway() throws RemotingException {
		final Channel mchannel = MockedChannel.mockChannel1();

		final Person requestdata = new Person("charles");
		Request request = new Request();
		request.setTwoWay(false);
		request.setData(requestdata);

		ExchangeHandler exhandler = new MockedExchangeHandler() {
			@Override
			public void received(Channel channel, Object message) throws RemotingException {
				Assertions.assertEquals(requestdata, message);
			}
		};
		HeaderExchangeHandler hexhandler = new HeaderExchangeHandler(exhandler);
		hexhandler.received(mchannel, request);
	}

	@Test
	public void test_received_request_twoway() throws RemotingException {
		final Person requestdata = new Person("charles");
		final Request request = new Request();
		request.setTwoWay(true);
		request.setData(requestdata);

		final AtomicInteger count = new AtomicInteger(0);
		final Channel mchannel = mockChannel4(requestdata, count, request);
		ExchangeHandler exhandler = new MockedExchangeHandler() {
			@Override
			public CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException {
				return CompletableFuture.completedFuture(request);
			}

			@Override
			public void received(Channel channel, Object message) throws RemotingException {
				Assertions.fail();
			}
		};
		HeaderExchangeHandler hexhandler = new HeaderExchangeHandler(exhandler);
		hexhandler.received(mchannel, request);
		Assertions.assertEquals(1, count.get());
	}

	@Test
	public void test_received_request_twoway_error_nullhandler() throws RemotingException {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new HeaderExchangeHandler(null));
	}

	@Test
	public void test_received_request_twoway_error_reply() throws RemotingException {
		final Person requestdata = new Person("charles");
		final Request request = new Request();
		request.setTwoWay(true);
		request.setData(requestdata);

		final AtomicInteger count = new AtomicInteger(0);
		final Channel mchannel = mockChannel3(count, request);
		ExchangeHandler exhandler = new MockedExchangeHandler() {
			@Override
			public CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException {
				throw new BizException();
			}
		};
		HeaderExchangeHandler hexhandler = new HeaderExchangeHandler(exhandler);
		hexhandler.received(mchannel, request);
		Assertions.assertEquals(1, count.get());
	}

	@Test
	public void test_received_request_twoway_error_reqeustBroken() throws RemotingException {
		final Request request = new Request();
		request.setTwoWay(true);
		request.setData(new BizException());
		request.setBroken(true);

		final AtomicInteger count = new AtomicInteger(0);
		final Channel mchannel = mockChannel2(count, request);
		HeaderExchangeHandler hexhandler = new HeaderExchangeHandler(new MockedExchangeHandler());
		hexhandler.received(mchannel, request);
		Assertions.assertEquals(1, count.get());
	}

	@Test
	public void test_received_request_event_readonly() throws RemotingException {
		final Request request = new Request();
		request.setTwoWay(true);
		request.setEvent(READONLY_EVENT);

		final Channel mchannel = MockedChannel.mockChannel1();
		HeaderExchangeHandler hexhandler = new HeaderExchangeHandler(new MockedExchangeHandler());
		hexhandler.received(mchannel, request);
		Assertions.assertTrue(mchannel.hasAttribute(Constants.CHANNEL_ATTRIBUTE_READONLY_KEY));
	}

	@Test
	public void test_received_request_event_other_discard() throws RemotingException {
		final Request request = new Request();
		request.setTwoWay(true);
		request.setEvent("my event");

		final Channel mchannel = mockChannel1();
		HeaderExchangeHandler hexhandler = new HeaderExchangeHandler(new MockedExchangeHandler() {

			@Override
			public CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException {
				Assertions.fail();
				throw new RemotingException(channel, "");
			}

			@Override
			public void received(Channel channel, Object message) throws RemotingException {
				Assertions.fail();
				throw new RemotingException(channel, "");
			}
		});
		hexhandler.received(mchannel, request);
	}

	private class BizException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	private class MockedExchangeHandler extends MockedChannelHandler implements ExchangeHandler {

		public String telnet(Channel channel, String message) throws RemotingException {
			throw new UnsupportedOperationException();
		}

		public CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException {
			throw new UnsupportedOperationException();
		}
	}

	private class Person {
		private String name;

		public Person(String name) {
			super();
			this.name = name;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + "]";
		}
	}
}
