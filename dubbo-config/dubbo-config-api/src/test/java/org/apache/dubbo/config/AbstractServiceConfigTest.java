/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.config;

import static org.apache.dubbo.common.constants.CommonConstants.EXPORTER_LISTENER_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.SERVICE_FILTER_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AbstractServiceConfigTest {
	static public AbstractServiceConfig mockAbstractServiceConfig1() {
		AbstractServiceConfig mockInstance = Mockito.spy(AbstractServiceConfig.class);
		try {
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	@Test
	public void testVersion() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setVersion("version");
		assertThat(serviceConfig.getVersion(), equalTo("version"));
	}

	@Test
	public void testGroup() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setGroup("group");
		assertThat(serviceConfig.getGroup(), equalTo("group"));
	}

	@Test
	public void testDelay() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setDelay(1000);
		assertThat(serviceConfig.getDelay(), equalTo(1000));
	}

	@Test
	public void testExport() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setExport(true);
		assertThat(serviceConfig.getExport(), is(true));
	}

	@Test
	public void testWeight() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setWeight(500);
		assertThat(serviceConfig.getWeight(), equalTo(500));
	}

	@Test
	public void testDocument() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setDocument("http://dubbo.apache.org");
		assertThat(serviceConfig.getDocument(), equalTo("http://dubbo.apache.org"));
		Map<String, String> parameters = new HashMap<String, String>();
		AbstractServiceConfig.appendParameters(parameters, serviceConfig);
		assertThat(parameters, hasEntry("document", "http%3A%2F%2Fdubbo.apache.org"));
	}

	@Test
	public void testToken() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setToken("token");
		assertThat(serviceConfig.getToken(), equalTo("token"));
		serviceConfig.setToken((Boolean) null);
		assertThat(serviceConfig.getToken(), nullValue());
		serviceConfig.setToken(true);
		assertThat(serviceConfig.getToken(), is("true"));
	}

	@Test
	public void testDeprecated() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setDeprecated(true);
		assertThat(serviceConfig.isDeprecated(), is(true));
	}

	@Test
	public void testDynamic() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setDynamic(true);
		assertThat(serviceConfig.isDynamic(), is(true));
	}

	@Test
	public void testProtocol() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		assertThat(serviceConfig.getProtocol(), nullValue());
		serviceConfig.setProtocol(new ProtocolConfig());
		assertThat(serviceConfig.getProtocol(), notNullValue());
		serviceConfig.setProtocols(new ArrayList<>(Collections.singletonList(new ProtocolConfig())));
		assertThat(serviceConfig.getProtocols(), hasSize(1));
	}

	@Test
	public void testAccesslog() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setAccesslog("access.log");
		assertThat(serviceConfig.getAccesslog(), equalTo("access.log"));
		serviceConfig.setAccesslog((Boolean) null);
		assertThat(serviceConfig.getAccesslog(), nullValue());
		serviceConfig.setAccesslog(true);
		assertThat(serviceConfig.getAccesslog(), equalTo("true"));
	}

	@Test
	public void testExecutes() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setExecutes(10);
		assertThat(serviceConfig.getExecutes(), equalTo(10));
	}

	@Test
	public void testFilter() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setFilter("mockfilter");
		assertThat(serviceConfig.getFilter(), equalTo("mockfilter"));
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SERVICE_FILTER_KEY, "prefilter");
		AbstractServiceConfig.appendParameters(parameters, serviceConfig);
		assertThat(parameters, hasEntry(SERVICE_FILTER_KEY, "prefilter,mockfilter"));
	}

	@Test
	public void testListener() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setListener("mockexporterlistener");
		assertThat(serviceConfig.getListener(), equalTo("mockexporterlistener"));
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(EXPORTER_LISTENER_KEY, "prelistener");
		AbstractServiceConfig.appendParameters(parameters, serviceConfig);
		assertThat(parameters, hasEntry(EXPORTER_LISTENER_KEY, "prelistener,mockexporterlistener"));
	}

	@Test
	public void testRegister() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setRegister(true);
		assertThat(serviceConfig.isRegister(), is(true));
	}

	@Test
	public void testWarmup() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setWarmup(100);
		assertThat(serviceConfig.getWarmup(), equalTo(100));
	}

	@Test
	public void testSerialization() throws Exception {
		AbstractServiceConfig serviceConfig = AbstractServiceConfigTest.mockAbstractServiceConfig1();
		serviceConfig.setSerialization("serialization");
		assertThat(serviceConfig.getSerialization(), equalTo("serialization"));
	}
}
