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

package org.apache.dubbo.remoting.exchange.support.header;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.remoting.Constants;
import org.apache.dubbo.remoting.exchange.ExchangeChannel;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.remoting.exchange.ExchangeHandler;
import org.apache.dubbo.remoting.exchange.ExchangeServer;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.transport.dispatcher.FakeChannelHandlers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HeartbeatHandlerTest {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatHandlerTest.class);

	private ExchangeServer server;
	private ExchangeClient client;

	@AfterEach
	public void after() throws Exception {
		if (client != null) {
			client.close();
			client = null;
		}

		if (server != null) {
			server.close();
			server = null;
		}

		// wait for timer to finish
		Thread.sleep(2000);
	}

	@Test
	public void testServerHeartbeat() throws Exception {
		URL serverURL = URL.valueOf("telnet://localhost:" + NetUtils.getAvailablePort(56780))
				.addParameter(Constants.EXCHANGER_KEY, HeaderExchanger.NAME)
				.addParameter(Constants.TRANSPORTER_KEY, "netty3").addParameter(Constants.HEARTBEAT_KEY, 1000);
		CountDownLatch connect = new CountDownLatch(1);
		CountDownLatch disconnect = new CountDownLatch(1);
		ExchangeHandler handler = Mockito.spy(ExchangeHandler.class);
		int[] handlerDisconnectCount = new int[] { 0 };
		CountDownLatch[] handlerConnectCountDownLatch = new CountDownLatch[1];
		CountDownLatch[] handlerDisconnectCountDownLatch = new CountDownLatch[1];
		int[] handlerConnectCount = new int[] { 0 };
		handlerConnectCountDownLatch[0] = connect;
		handlerDisconnectCountDownLatch[0] = disconnect;
		try {
			Mockito.doAnswer((stubInvo) -> {
				++handlerDisconnectCount[0];
				handlerDisconnectCountDownLatch[0].countDown();
				return null;
			}).when(handler).disconnected(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object request = stubInvo.getArgument(1);
				return CompletableFuture.completedFuture(request);
			}).when(handler).reply(Mockito.any(ExchangeChannel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				++handlerConnectCount[0];
				handlerConnectCountDownLatch[0].countDown();
				return null;
			}).when(handler).connected(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				String message = stubInvo.getArgument(1);
				return message;
			}).when(handler).telnet(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Throwable exception = stubInvo.getArgument(1);
				exception.printStackTrace();
				return null;
			}).when(handler).caught(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object message = stubInvo.getArgument(1);
				logger.error(handler.getClass().getSimpleName() + message.toString());
				return null;
			}).when(handler).received(Mockito.any(), Mockito.any());
		} catch (Exception exception) {
		}
		server = Exchangers.bind(serverURL, handler);
		System.out.println("Server bind successfully");

		FakeChannelHandlers.setTestingChannelHandlers();
		serverURL = serverURL.removeParameter(Constants.HEARTBEAT_KEY);

		// Let the client not reply to the heartbeat, and turn off automatic reconnect
		// to simulate the client dropped.
		serverURL = serverURL.addParameter(Constants.HEARTBEAT_KEY, 600 * 1000);
		serverURL = serverURL.addParameter(Constants.RECONNECT_KEY, false);

		client = Exchangers.connect(serverURL);
		disconnect.await();
		Assertions.assertTrue(handlerDisconnectCount[0] > 0);
		System.out.println("disconnect count " + handlerDisconnectCount[0]);
	}

	@Test
	public void testHeartbeat() throws Exception {
		URL serverURL = URL.valueOf("telnet://localhost:" + NetUtils.getAvailablePort(56785))
				.addParameter(Constants.EXCHANGER_KEY, HeaderExchanger.NAME)
				.addParameter(Constants.TRANSPORTER_KEY, "netty3").addParameter(Constants.HEARTBEAT_KEY, 1000);
		CountDownLatch connect = new CountDownLatch(1);
		CountDownLatch disconnect = new CountDownLatch(1);
		ExchangeHandler handler = Mockito.spy(ExchangeHandler.class);
		int[] handlerDisconnectCount = new int[] { 0 };
		CountDownLatch[] handlerConnectCountDownLatch = new CountDownLatch[1];
		CountDownLatch[] handlerDisconnectCountDownLatch = new CountDownLatch[1];
		int[] handlerConnectCount = new int[] { 0 };
		handlerConnectCountDownLatch[0] = connect;
		handlerDisconnectCountDownLatch[0] = disconnect;
		try {
			Mockito.doAnswer((stubInvo) -> {
				++handlerDisconnectCount[0];
				handlerDisconnectCountDownLatch[0].countDown();
				return null;
			}).when(handler).disconnected(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object request = stubInvo.getArgument(1);
				return CompletableFuture.completedFuture(request);
			}).when(handler).reply(Mockito.any(ExchangeChannel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				++handlerConnectCount[0];
				handlerConnectCountDownLatch[0].countDown();
				return null;
			}).when(handler).connected(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				String message = stubInvo.getArgument(1);
				return message;
			}).when(handler).telnet(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Throwable exception = stubInvo.getArgument(1);
				exception.printStackTrace();
				return null;
			}).when(handler).caught(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object message = stubInvo.getArgument(1);
				logger.error(handler.getClass().getSimpleName() + message.toString());
				return null;
			}).when(handler).received(Mockito.any(), Mockito.any());
		} catch (Exception exception) {
		}
		server = Exchangers.bind(serverURL, handler);
		System.out.println("Server bind successfully");

		client = Exchangers.connect(serverURL);
		connect.await();
		System.err.println("++++++++++++++ disconnect count " + handlerDisconnectCount[0]);
		System.err.println("++++++++++++++ connect count " + handlerConnectCount[0]);
		Assertions.assertEquals(0, handlerDisconnectCount[0]);
		Assertions.assertEquals(1, handlerConnectCount[0]);
	}

	@Test
	public void testClientHeartbeat() throws Exception {
		FakeChannelHandlers.setTestingChannelHandlers();
		URL serverURL = URL.valueOf("telnet://localhost:" + NetUtils.getAvailablePort(56790))
				.addParameter(Constants.EXCHANGER_KEY, HeaderExchanger.NAME)
				.addParameter(Constants.TRANSPORTER_KEY, "netty3");
		CountDownLatch connect = new CountDownLatch(1);
		CountDownLatch disconnect = new CountDownLatch(1);
		ExchangeHandler handler = Mockito.spy(ExchangeHandler.class);
		int[] handlerDisconnectCount = new int[] { 0 };
		CountDownLatch[] handlerConnectCountDownLatch = new CountDownLatch[1];
		CountDownLatch[] handlerDisconnectCountDownLatch = new CountDownLatch[1];
		int[] handlerConnectCount = new int[] { 0 };
		handlerConnectCountDownLatch[0] = connect;
		handlerDisconnectCountDownLatch[0] = disconnect;
		try {
			Mockito.doAnswer((stubInvo) -> {
				++handlerDisconnectCount[0];
				handlerDisconnectCountDownLatch[0].countDown();
				return null;
			}).when(handler).disconnected(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object request = stubInvo.getArgument(1);
				return CompletableFuture.completedFuture(request);
			}).when(handler).reply(Mockito.any(ExchangeChannel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				++handlerConnectCount[0];
				handlerConnectCountDownLatch[0].countDown();
				return null;
			}).when(handler).connected(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				String message = stubInvo.getArgument(1);
				return message;
			}).when(handler).telnet(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Throwable exception = stubInvo.getArgument(1);
				exception.printStackTrace();
				return null;
			}).when(handler).caught(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object message = stubInvo.getArgument(1);
				logger.error(handler.getClass().getSimpleName() + message.toString());
				return null;
			}).when(handler).received(Mockito.any(), Mockito.any());
		} catch (Exception exception) {
		}
		server = Exchangers.bind(serverURL, handler);
		System.out.println("Server bind successfully");

		FakeChannelHandlers.resetChannelHandlers();
		serverURL = serverURL.addParameter(Constants.HEARTBEAT_KEY, 1000);
		client = Exchangers.connect(serverURL);
		connect.await();
		Assertions.assertTrue(handlerConnectCount[0] > 0);
		System.out.println("connect count " + handlerConnectCount[0]);
	}

}
