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
package org.apache.dubbo.registry.support;

import static org.apache.dubbo.common.URL.valueOf;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER_SIDE;
import static org.apache.dubbo.common.constants.RegistryConstants.REGISTRY_TYPE_KEY;
import static org.apache.dubbo.common.constants.RegistryConstants.SERVICE_REGISTRY_TYPE;
import static org.apache.dubbo.common.constants.RegistryConstants.SUBSCRIBED_SERVICE_NAMES_KEY;
import static org.apache.dubbo.rpc.Constants.ID_KEY;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.metadata.WritableMetadataService;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.client.ServiceDiscoveryRegistry;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link ServiceDiscoveryRegistry} Test
 *
 * @since 2.7.5
 */
public class ServiceOrientedRegistryTest {

	private static final URL registryURL = valueOf("in-memory://localhost:12345")
			.addParameter(REGISTRY_TYPE_KEY, SERVICE_REGISTRY_TYPE)
			.addParameter(ID_KEY, "org.apache.dubbo.config.RegistryConfig#0")
			.addParameter(SUBSCRIBED_SERVICE_NAMES_KEY, "a, b , c,d,e ,");

	private static final String SERVICE_INTERFACE = "org.apache.dubbo.metadata.MetadataService";

	private static final String GROUP = "dubbo-provider";

	private static final String VERSION = "1.0.0";

	private static URL url = valueOf("dubbo://192.168.0.102:20880/" + SERVICE_INTERFACE + "?&application=" + GROUP
			+ "&interface=" + SERVICE_INTERFACE + "&group=" + GROUP + "&version=" + VERSION
			+ "&methods=getAllServiceKeys,getServiceRestMetadata,getExportedURLs,getAllExportedURLs" + "&side="
			+ PROVIDER_SIDE);

	private WritableMetadataService metadataService;

	private ServiceDiscoveryRegistry registry;

	@BeforeEach
	public void init() {
		registry = ServiceDiscoveryRegistry.create(registryURL);
		metadataService = WritableMetadataService.getDefaultExtension();
		ApplicationModel.getConfigManager().setApplication(new ApplicationConfig("Test"));
	}

	@Test
	public void testSubscribe() {

		registry.subscribe(url, new MyNotifyListener());

		SortedSet<String> urls = metadataService.getSubscribedURLs();

		assertTrue(urls.isEmpty());

	}

	private class MyNotifyListener implements NotifyListener {

		private List<URL> cache = new LinkedList<>();

		@Override
		public void notify(List<URL> urls) {
			cache.addAll(urls);
		}

		public List<URL> getURLs() {
			return cache;
		}
	}

}
