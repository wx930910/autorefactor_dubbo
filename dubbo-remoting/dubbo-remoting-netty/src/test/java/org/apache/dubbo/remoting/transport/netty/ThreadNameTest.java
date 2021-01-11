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
package org.apache.dubbo.remoting.transport.netty;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.ChannelHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ThreadNameTest {

	private void output(String method, boolean[] client) {
		System.out.println(
				Thread.currentThread().getName() + " " + (client[0] ? "client " + method : "server " + method));
	}

	private void checkThreadName(String[] message, boolean success) {
		if (!success) {
			success = Thread.currentThread().getName().matches(message[0]);
		}
	}

	String[] clientHandlerMessage = new String[1];

	boolean clientHandlerSuccess = false;

	boolean[] clientHandlerClient = new boolean[1];

	String[] serverHandlerMessage = new String[1];

	boolean serverHandlerSuccess = false;

	boolean[] serverHandlerClient = new boolean[1];

	private NettyServer server;
	private NettyClient client;

	private URL serverURL;
	private URL clientURL;

	private ChannelHandler serverHandler;
	private ChannelHandler clientHandler;

	private static String serverRegex = "DubboServerHandler\\-localhost:(\\d+)\\-thread\\-(\\d+)";
	private static String clientRegex = "DubboClientHandler\\-localhost:(\\d+)\\-thread\\-(\\d+)";

	@BeforeEach
	public void before() throws Exception {
		int port = NetUtils.getAvailablePort();
		serverURL = URL.valueOf("telnet://localhost?side=provider").setPort(port);
		clientURL = URL.valueOf("telnet://localhost?side=consumer").setPort(port);

		serverHandler = Mockito.spy(ChannelHandler.class);
		serverHandlerMessage[0] = serverRegex;
		serverHandlerClient[0] = false;
		try {
			Mockito.doAnswer((stubInvo) -> {
				output("connected", serverHandlerClient);
				checkThreadName(serverHandlerMessage, serverHandlerSuccess);
				return null;
			}).when(serverHandler).connected(Mockito.any(Channel.class));
			Mockito.doAnswer((stubInvo) -> {
				output("caught", serverHandlerClient);
				checkThreadName(serverHandlerMessage, serverHandlerSuccess);
				return null;
			}).when(serverHandler).caught(Mockito.any(Channel.class), Mockito.any(Throwable.class));
			Mockito.doAnswer((stubInvo) -> {
				output("sent", serverHandlerClient);
				checkThreadName(serverHandlerMessage, serverHandlerSuccess);
				return null;
			}).when(serverHandler).sent(Mockito.any(Channel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				output("disconnected", serverHandlerClient);
				checkThreadName(serverHandlerMessage, serverHandlerSuccess);
				return null;
			}).when(serverHandler).disconnected(Mockito.any(Channel.class));
			Mockito.doAnswer((stubInvo) -> {
				output("received", serverHandlerClient);
				checkThreadName(serverHandlerMessage, serverHandlerSuccess);
				return null;
			}).when(serverHandler).received(Mockito.any(Channel.class), Mockito.any(Object.class));
		} catch (Exception exception) {
		}
		clientHandler = Mockito.spy(ChannelHandler.class);
		clientHandlerMessage[0] = clientRegex;
		clientHandlerClient[0] = true;
		try {
			Mockito.doAnswer((stubInvo) -> {
				output("connected", clientHandlerClient);
				checkThreadName(clientHandlerMessage, clientHandlerSuccess);
				return null;
			}).when(clientHandler).connected(Mockito.any(Channel.class));
			Mockito.doAnswer((stubInvo) -> {
				output("caught", clientHandlerClient);
				checkThreadName(clientHandlerMessage, clientHandlerSuccess);
				return null;
			}).when(clientHandler).caught(Mockito.any(Channel.class), Mockito.any(Throwable.class));
			Mockito.doAnswer((stubInvo) -> {
				output("sent", clientHandlerClient);
				checkThreadName(clientHandlerMessage, clientHandlerSuccess);
				return null;
			}).when(clientHandler).sent(Mockito.any(Channel.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				output("disconnected", clientHandlerClient);
				checkThreadName(clientHandlerMessage, clientHandlerSuccess);
				return null;
			}).when(clientHandler).disconnected(Mockito.any(Channel.class));
			Mockito.doAnswer((stubInvo) -> {
				output("received", clientHandlerClient);
				checkThreadName(clientHandlerMessage, clientHandlerSuccess);
				return null;
			}).when(clientHandler).received(Mockito.any(Channel.class), Mockito.any(Object.class));
		} catch (Exception exception) {
		}

		server = new NettyServer(serverURL, serverHandler);
		client = new NettyClient(clientURL, clientHandler);
	}

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
	}

	@Test
	public void testThreadName() throws Exception {
		client.send("hello");
		Thread.sleep(1000L * 5L);
		if (!serverHandlerSuccess || !clientHandlerSuccess) {
			Assertions.fail();
		}
	}

}
