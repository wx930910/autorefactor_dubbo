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

package org.apache.dubbo.configcenter.support.etcd;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.dubbo.remoting.etcd.Constants.SESSION_TIMEOUT_KEY;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.ConfigChangedEvent;
import org.apache.dubbo.common.config.configcenter.ConfigurationListener;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.launcher.EtcdCluster;
import io.etcd.jetcd.launcher.EtcdClusterFactory;

/**
 * Unit test for etcd config center support Integrate with
 * https://github.com/etcd-io/jetcd#launcher
 */
public class EtcdDynamicConfigurationTest {

	private static EtcdDynamicConfiguration config;

	public EtcdCluster etcdCluster = EtcdClusterFactory.buildCluster(getClass().getSimpleName(), 3, false);

	private static Client client;

	@Test
	public void testGetConfig() {

		put("/dubbo/config/org.apache.dubbo.etcd.testService/configurators", "hello");
		put("/dubbo/config/test/dubbo.properties", "aaa=bbb");
		Assert.assertEquals("hello", config.getConfig("org.apache.dubbo.etcd.testService.configurators",
				DynamicConfiguration.DEFAULT_GROUP));
		Assert.assertEquals("aaa=bbb", config.getConfig("dubbo.properties", "test"));
	}

	@Test
	public void testAddListener() throws Exception {
		CountDownLatch latch = new CountDownLatch(4);
		ConfigurationListener listener1 = Mockito.spy(ConfigurationListener.class);
		String[] listener1Value = new String[1];
		Map<String, Integer> listener1CountMap = new HashMap<>();
		CountDownLatch[] listener1Latch = new CountDownLatch[1];
		listener1Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				Integer count = listener1CountMap.computeIfAbsent(event.getKey(), k -> 0);
				listener1CountMap.put(event.getKey(), ++count);
				listener1Value[0] = event.getContent();
				listener1Latch[0].countDown();
				return null;
			}).when(listener1).process(Mockito.any(ConfigChangedEvent.class));
		} catch (Exception exception) {
		}
		ConfigurationListener listener2 = Mockito.spy(ConfigurationListener.class);
		String[] listener2Value = new String[1];
		Map<String, Integer> listener2CountMap = new HashMap<>();
		CountDownLatch[] listener2Latch = new CountDownLatch[1];
		listener2Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				Integer count = listener2CountMap.computeIfAbsent(event.getKey(), k -> 0);
				listener2CountMap.put(event.getKey(), ++count);
				listener2Value[0] = event.getContent();
				listener2Latch[0].countDown();
				return null;
			}).when(listener2).process(Mockito.any(ConfigChangedEvent.class));
		} catch (Exception exception) {
		}
		ConfigurationListener listener3 = Mockito.spy(ConfigurationListener.class);
		String[] listener3Value = new String[1];
		Map<String, Integer> listener3CountMap = new HashMap<>();
		CountDownLatch[] listener3Latch = new CountDownLatch[1];
		listener3Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				Integer count = listener3CountMap.computeIfAbsent(event.getKey(), k -> 0);
				listener3CountMap.put(event.getKey(), ++count);
				listener3Value[0] = event.getContent();
				listener3Latch[0].countDown();
				return null;
			}).when(listener3).process(Mockito.any(ConfigChangedEvent.class));
		} catch (Exception exception) {
		}
		ConfigurationListener listener4 = Mockito.spy(ConfigurationListener.class);
		String[] listener4Value = new String[1];
		Map<String, Integer> listener4CountMap = new HashMap<>();
		CountDownLatch[] listener4Latch = new CountDownLatch[1];
		listener4Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				Integer count = listener4CountMap.computeIfAbsent(event.getKey(), k -> 0);
				listener4CountMap.put(event.getKey(), ++count);
				listener4Value[0] = event.getContent();
				listener4Latch[0].countDown();
				return null;
			}).when(listener4).process(Mockito.any(ConfigChangedEvent.class));
		} catch (Exception exception) {
		}
		config.addListener("AService.configurators", listener1);
		config.addListener("AService.configurators", listener2);
		config.addListener("testapp.tagrouters", listener3);
		config.addListener("testapp.tagrouters", listener4);

		put("/dubbo/config/AService/configurators", "new value1");
		Thread.sleep(200);
		put("/dubbo/config/testapp/tagrouters", "new value2");
		Thread.sleep(200);
		put("/dubbo/config/testapp", "new value3");

		Thread.sleep(1000);

		Assert.assertTrue(latch.await(5, TimeUnit.SECONDS));
		Assert.assertEquals(1, (int) listener1CountMap.get("/dubbo/config/AService/configurators"));
		Assert.assertEquals(1, (int) listener2CountMap.get("/dubbo/config/AService/configurators"));
		Assert.assertEquals(1, (int) listener3CountMap.get("/dubbo/config/testapp/tagrouters"));
		Assert.assertEquals(1, (int) listener4CountMap.get("/dubbo/config/testapp/tagrouters"));

		Assert.assertEquals("new value1", listener1Value[0]);
		Assert.assertEquals("new value1", listener2Value[0]);
		Assert.assertEquals("new value2", listener3Value[0]);
		Assert.assertEquals("new value2", listener4Value[0]);
	}

	private void put(String key, String value) {
		try {
			client.getKVClient().put(ByteSequence.from(key, UTF_8), ByteSequence.from(value, UTF_8)).get();
		} catch (Exception e) {
			System.out.println("Error put value to etcd.");
		}
	}

	@Before
	public void setUp() {

		etcdCluster.start();

		client = Client.builder().endpoints(etcdCluster.getClientEndpoints()).build();

		List<URI> clientEndPoints = etcdCluster.getClientEndpoints();

		String ipAddress = clientEndPoints.get(0).getHost() + ":" + clientEndPoints.get(0).getPort();
		String urlForDubbo = "etcd3://" + ipAddress + "/org.apache.dubbo.etcd.testService";

		// timeout in 15 seconds.
		URL url = URL.valueOf(urlForDubbo).addParameter(SESSION_TIMEOUT_KEY, 15000);
		config = new EtcdDynamicConfiguration(url);
	}

	@After
	public void tearDown() {
		etcdCluster.close();
	}

}
