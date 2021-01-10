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
package org.apache.dubbo.config;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.api.Greeting;
import org.apache.dubbo.config.mock.GreetingLocal1;
import org.apache.dubbo.config.mock.GreetingLocal2;
import org.apache.dubbo.config.mock.GreetingLocal3;
import org.apache.dubbo.config.mock.GreetingMock1;
import org.apache.dubbo.config.mock.GreetingMock2;
import org.apache.dubbo.config.utils.ConfigValidationUtils;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

public class AbstractInterfaceConfigTest {
	static public AbstractInterfaceConfig mockAbstractInterfaceConfig1() {
		AbstractInterfaceConfig mockInstance = Mockito.spy(AbstractInterfaceConfig.class);
		try {
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	private static File dubboProperties;

	@BeforeAll
	public static void setUp(@TempDir Path folder) {
		ApplicationModel.reset();
		dubboProperties = folder.resolve(CommonConstants.DUBBO_PROPERTIES_KEY).toFile();
		System.setProperty(CommonConstants.DUBBO_PROPERTIES_KEY, dubboProperties.getAbsolutePath());
	}

	@AfterAll
	public static void tearDown() {
		ApplicationModel.reset();
		System.clearProperty(CommonConstants.DUBBO_PROPERTIES_KEY);
	}

	@AfterEach
	public void tearMethodAfterEachUT() {
//        ApplicationModel.getConfigManager().clear();
	}

	@Test
	public void testCheckRegistry1() {
		System.setProperty("dubbo.registry.address", "addr1");
		try {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setApplication(new ApplicationConfig("testCheckRegistry1"));
			interfaceConfig.checkRegistry();
			Assertions.assertEquals(1, interfaceConfig.getRegistries().size());
			Assertions.assertEquals("addr1", interfaceConfig.getRegistries().get(0).getAddress());
		} finally {
			System.clearProperty("dubbo.registry.address");
		}
	}

	@Test
	public void testCheckRegistry2() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.checkRegistry();
		});
	}

	@Test
	public void checkInterfaceAndMethods1() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.checkInterfaceAndMethods(null, null);
		});
	}

	@Test
	public void checkInterfaceAndMethods2() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.checkInterfaceAndMethods(AbstractInterfaceConfigTest.class, null);
		});
	}

	@Test
	public void checkInterfaceAndMethod3() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			MethodConfig methodConfig = new MethodConfig();
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.checkInterfaceAndMethods(Greeting.class, Collections.singletonList(methodConfig));
		});
	}

	@Test
	public void checkInterfaceAndMethod4() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			MethodConfig methodConfig = new MethodConfig();
			methodConfig.setName("nihao");
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.checkInterfaceAndMethods(Greeting.class, Collections.singletonList(methodConfig));
		});
	}

	@Test
	public void checkInterfaceAndMethod5() {
		MethodConfig methodConfig = new MethodConfig();
		methodConfig.setName("hello");
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.checkInterfaceAndMethods(Greeting.class, Collections.singletonList(methodConfig));
	}

	@Test
	public void checkStubAndMock1() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setLocal(GreetingLocal1.class.getName());
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void checkStubAndMock2() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setLocal(GreetingLocal2.class.getName());
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void checkStubAndMock3() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setLocal(GreetingLocal3.class.getName());
		interfaceConfig.checkStubAndLocal(Greeting.class);
		ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
	}

	@Test
	public void checkStubAndMock4() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setStub(GreetingLocal1.class.getName());
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void checkStubAndMock5() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setStub(GreetingLocal2.class.getName());
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void checkStubAndMock6() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setStub(GreetingLocal3.class.getName());
		interfaceConfig.checkStubAndLocal(Greeting.class);
		ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
	}

	@Test
	public void checkStubAndMock7() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setMock("return {a, b}");
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void checkStubAndMock8() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setMock(GreetingMock1.class.getName());
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void checkStubAndMock9() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
			interfaceConfig.setMock(GreetingMock2.class.getName());
			interfaceConfig.checkStubAndLocal(Greeting.class);
			ConfigValidationUtils.checkMock(Greeting.class, interfaceConfig);
		});
	}

	@Test
	public void testLocal() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setLocal((Boolean) null);
		Assertions.assertNull(interfaceConfig.getLocal());
		interfaceConfig.setLocal(true);
		Assertions.assertEquals("true", interfaceConfig.getLocal());
		interfaceConfig.setLocal("GreetingMock");
		Assertions.assertEquals("GreetingMock", interfaceConfig.getLocal());
	}

	@Test
	public void testStub() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setStub((Boolean) null);
		Assertions.assertNull(interfaceConfig.getStub());
		interfaceConfig.setStub(true);
		Assertions.assertEquals("true", interfaceConfig.getStub());
		interfaceConfig.setStub("GreetingMock");
		Assertions.assertEquals("GreetingMock", interfaceConfig.getStub());
	}

	@Test
	public void testCluster() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setCluster("mockcluster");
		Assertions.assertEquals("mockcluster", interfaceConfig.getCluster());
	}

	@Test
	public void testProxy() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setProxy("mockproxyfactory");
		Assertions.assertEquals("mockproxyfactory", interfaceConfig.getProxy());
	}

	@Test
	public void testConnections() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setConnections(1);
		Assertions.assertEquals(1, interfaceConfig.getConnections().intValue());
	}

	@Test
	public void testFilter() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setFilter("mockfilter");
		Assertions.assertEquals("mockfilter", interfaceConfig.getFilter());
	}

	@Test
	public void testListener() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setListener("mockinvokerlistener");
		Assertions.assertEquals("mockinvokerlistener", interfaceConfig.getListener());
	}

	@Test
	public void testLayer() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setLayer("layer");
		Assertions.assertEquals("layer", interfaceConfig.getLayer());
	}

	@Test
	public void testApplication() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		ApplicationConfig applicationConfig = new ApplicationConfig();
		interfaceConfig.setApplication(applicationConfig);
		Assertions.assertSame(applicationConfig, interfaceConfig.getApplication());
	}

	@Test
	public void testModule() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		ModuleConfig moduleConfig = new ModuleConfig();
		interfaceConfig.setModule(moduleConfig);
		Assertions.assertSame(moduleConfig, interfaceConfig.getModule());
	}

	@Test
	public void testRegistry() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		RegistryConfig registryConfig = new RegistryConfig();
		interfaceConfig.setRegistry(registryConfig);
		Assertions.assertSame(registryConfig, interfaceConfig.getRegistry());
	}

	@Test
	public void testRegistries() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		RegistryConfig registryConfig = new RegistryConfig();
		interfaceConfig.setRegistries(Collections.singletonList(registryConfig));
		Assertions.assertEquals(1, interfaceConfig.getRegistries().size());
		Assertions.assertSame(registryConfig, interfaceConfig.getRegistries().get(0));
	}

	@Test
	public void testMonitor() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setMonitor("monitor-addr");
		Assertions.assertEquals("monitor-addr", interfaceConfig.getMonitor().getAddress());
		MonitorConfig monitorConfig = new MonitorConfig();
		interfaceConfig.setMonitor(monitorConfig);
		Assertions.assertSame(monitorConfig, interfaceConfig.getMonitor());
	}

	@Test
	public void testOwner() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setOwner("owner");
		Assertions.assertEquals("owner", interfaceConfig.getOwner());
	}

	@Test
	public void testCallbacks() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setCallbacks(2);
		Assertions.assertEquals(2, interfaceConfig.getCallbacks().intValue());
	}

	@Test
	public void testOnconnect() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setOnconnect("onConnect");
		Assertions.assertEquals("onConnect", interfaceConfig.getOnconnect());
	}

	@Test
	public void testOndisconnect() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setOndisconnect("onDisconnect");
		Assertions.assertEquals("onDisconnect", interfaceConfig.getOndisconnect());
	}

	@Test
	public void testScope() {
		AbstractInterfaceConfig interfaceConfig = AbstractInterfaceConfigTest.mockAbstractInterfaceConfig1();
		interfaceConfig.setScope("scope");
		Assertions.assertEquals("scope", interfaceConfig.getScope());
	}
}
