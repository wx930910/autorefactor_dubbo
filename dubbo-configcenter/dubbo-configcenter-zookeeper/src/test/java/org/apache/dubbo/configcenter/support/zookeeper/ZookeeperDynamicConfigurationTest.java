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
package org.apache.dubbo.configcenter.support.zookeeper;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.ConfigChangedEvent;
import org.apache.dubbo.common.config.configcenter.ConfigurationListener;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.common.config.configcenter.DynamicConfigurationFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.NetUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * TODO refactor using mockito
 */
public class ZookeeperDynamicConfigurationTest {
	private static CuratorFramework client;

	private static URL configUrl;
	private static int zkServerPort = NetUtils.getAvailablePort();
	private static TestingServer zkServer;
	private static DynamicConfiguration configuration;

	@BeforeAll
	public static void setUp() throws Exception {
		zkServer = new TestingServer(zkServerPort, true);

		client = CuratorFrameworkFactory.newClient("127.0.0.1:" + zkServerPort, 60 * 1000, 60 * 1000,
				new ExponentialBackoffRetry(1000, 3));
		client.start();

		try {
			setData("/dubbo/config/dubbo/dubbo.properties", "The content from dubbo.properties");
			setData("/dubbo/config/dubbo/service:version:group.configurators", "The content from configurators");
			setData("/dubbo/config/appname", "The content from higer level node");
			setData("/dubbo/config/dubbo/appname.tag-router", "The content from appname tagrouters");
			setData("/dubbo/config/dubbo/never.change.DemoService.configurators",
					"Never change value from configurators");
		} catch (Exception e) {
			e.printStackTrace();
		}

		configUrl = URL.valueOf("zookeeper://127.0.0.1:" + zkServerPort);

		configuration = ExtensionLoader.getExtensionLoader(DynamicConfigurationFactory.class)
				.getExtension(configUrl.getProtocol()).getDynamicConfiguration(configUrl);
	}

	@AfterAll
	public static void tearDown() throws Exception {
		zkServer.stop();
	}

	private static void setData(String path, String data) throws Exception {
		if (client.checkExists().forPath(path) == null) {
			client.create().creatingParentsIfNeeded().forPath(path);
		}
		client.setData().forPath(path, data.getBytes());
	}

	@Test
	public void testGetConfig() throws Exception {
		Assertions.assertEquals("The content from dubbo.properties",
				configuration.getConfig("dubbo.properties", "dubbo"));
	}

	@Test
	public void testAddListener() throws Exception {
		CountDownLatch latch = new CountDownLatch(4);
		ConfigurationListener listener1 = Mockito.spy(ConfigurationListener.class);
		CountDownLatch[] listener1Latch = new CountDownLatch[1];
		String[] listener1Value = new String[1];
		Map<String, Integer> listener1CountMap = new HashMap<>();
		listener1Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				System.out.println(this + ": " + event);
				Integer count = listener1CountMap.computeIfAbsent(event.getKey(), k -> new Integer(0));
				listener1CountMap.put(event.getKey(), ++count);
				listener1Value[0] = event.getContent();
				listener1Latch[0].countDown();
				return null;
			}).when(listener1).process(Mockito.any());
		} catch (Exception exception) {
		}
		ConfigurationListener listener2 = Mockito.spy(ConfigurationListener.class);
		CountDownLatch[] listener2Latch = new CountDownLatch[1];
		String[] listener2Value = new String[1];
		Map<String, Integer> listener2CountMap = new HashMap<>();
		listener2Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				System.out.println(this + ": " + event);
				Integer count = listener2CountMap.computeIfAbsent(event.getKey(), k -> new Integer(0));
				listener2CountMap.put(event.getKey(), ++count);
				listener2Value[0] = event.getContent();
				listener2Latch[0].countDown();
				return null;
			}).when(listener2).process(Mockito.any());
		} catch (Exception exception) {
		}
		ConfigurationListener listener3 = Mockito.spy(ConfigurationListener.class);
		CountDownLatch[] listener3Latch = new CountDownLatch[1];
		String[] listener3Value = new String[1];
		Map<String, Integer> listener3CountMap = new HashMap<>();
		listener3Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				System.out.println(this + ": " + event);
				Integer count = listener3CountMap.computeIfAbsent(event.getKey(), k -> new Integer(0));
				listener3CountMap.put(event.getKey(), ++count);
				listener3Value[0] = event.getContent();
				listener3Latch[0].countDown();
				return null;
			}).when(listener3).process(Mockito.any());
		} catch (Exception exception) {
		}
		ConfigurationListener listener4 = Mockito.spy(ConfigurationListener.class);
		CountDownLatch[] listener4Latch = new CountDownLatch[1];
		String[] listener4Value = new String[1];
		Map<String, Integer> listener4CountMap = new HashMap<>();
		listener4Latch[0] = latch;
		try {
			Mockito.doAnswer((stubInvo) -> {
				ConfigChangedEvent event = stubInvo.getArgument(0);
				System.out.println(this + ": " + event);
				Integer count = listener4CountMap.computeIfAbsent(event.getKey(), k -> new Integer(0));
				listener4CountMap.put(event.getKey(), ++count);
				listener4Value[0] = event.getContent();
				listener4Latch[0].countDown();
				return null;
			}).when(listener4).process(Mockito.any());
		} catch (Exception exception) {
		}
		configuration.addListener("service:version:group.configurators", listener1);
		configuration.addListener("service:version:group.configurators", listener2);
		configuration.addListener("appname.tag-router", listener3);
		configuration.addListener("appname.tag-router", listener4);

		setData("/dubbo/config/dubbo/service:version:group.configurators", "new value1");
		Thread.sleep(100);
		setData("/dubbo/config/dubbo/appname.tag-router", "new value2");
		Thread.sleep(100);
		setData("/dubbo/config/appname", "new value3");

		Thread.sleep(5000);

		latch.await();
		Assertions.assertEquals(1, (int) listener1CountMap.get("service:version:group.configurators"));
		Assertions.assertEquals(1, (int) listener2CountMap.get("service:version:group.configurators"));
		Assertions.assertEquals(1, (int) listener3CountMap.get("appname.tag-router"));
		Assertions.assertEquals(1, (int) listener4CountMap.get("appname.tag-router"));

		Assertions.assertEquals("new value1", listener1Value[0]);
		Assertions.assertEquals("new value1", listener2Value[0]);
		Assertions.assertEquals("new value2", listener3Value[0]);
		Assertions.assertEquals("new value2", listener4Value[0]);
	}

	@Test
	public void testPublishConfig() {
		String key = "user-service";
		String group = "org.apache.dubbo.service.UserService";
		String content = "test";

		assertTrue(configuration.publishConfig(key, group, content));
		assertEquals("test", configuration.getProperties(key, group));
	}

	@Test
	public void testGetConfigKeysAndContents() {

		String group = "mapping";
		String key = "org.apache.dubbo.service.UserService";
		String content = "app1";

		String key2 = "org.apache.dubbo.service.UserService2";

		assertTrue(configuration.publishConfig(key, group, content));
		assertTrue(configuration.publishConfig(key2, group, content));

		Set<String> configKeys = configuration.getConfigKeys(group);

		assertEquals(new TreeSet(asList(key, key2)), configKeys);
	}

}
