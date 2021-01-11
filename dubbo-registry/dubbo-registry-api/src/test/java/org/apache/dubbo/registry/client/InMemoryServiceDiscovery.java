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
package org.apache.dubbo.registry.client;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.DefaultPage;
import org.apache.dubbo.event.EventDispatcher;
import org.mockito.Mockito;

/**
 * In-Memory {@link ServiceDiscovery} implementation
 *
 * @since 2.7.5
 */
public class InMemoryServiceDiscovery {

	static public ServiceDiscovery mockServiceDiscovery1() {
		URL[] mockFieldVariableRegistryURL = new URL[1];
		EventDispatcher mockFieldVariableDispatcher = EventDispatcher.getDefaultExtension();
		Map<String, List<ServiceInstance>> mockFieldVariableRepository = new HashMap<>();
		ServiceInstance[] mockFieldVariableServiceInstance = new ServiceInstance[1];
		ServiceDiscovery mockInstance = Mockito.spy(ServiceDiscovery.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				ServiceInstance serviceInstance = stubInvo.getArgument(0);
				String serviceName = serviceInstance.getServiceName();
				List<ServiceInstance> serviceInstances = mockFieldVariableRepository.computeIfAbsent(serviceName,
						s -> new LinkedList<>());
				serviceInstances.remove(serviceInstance);
				return null;
			}).when(mockInstance).unregister(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				URL registryURL = stubInvo.getArgument(0);
				mockFieldVariableRegistryURL[0] = registryURL;
				return null;
			}).when(mockInstance).initialize(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableRepository.keySet();
			}).when(mockInstance).getServices();
			Mockito.doAnswer((stubInvo) -> {
				ServiceInstance serviceInstance = stubInvo.getArgument(0);
				mockFieldVariableServiceInstance[0] = serviceInstance;
				String serviceName = serviceInstance.getServiceName();
				List<ServiceInstance> serviceInstances = mockFieldVariableRepository.computeIfAbsent(serviceName,
						s -> new LinkedList<>());
				if (!serviceInstances.contains(serviceInstance)) {
					serviceInstances.add(serviceInstance);
				}
				return null;
			}).when(mockInstance).register(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return "InMemoryServiceDiscovery";
			}).when(mockInstance).toString();
			Mockito.doNothing().when(mockInstance).destroy();
			Mockito.doAnswer((stubInvo) -> {
				String serviceName = stubInvo.getArgument(0);
				int offset = stubInvo.getArgument(1);
				int pageSize = stubInvo.getArgument(2);
				boolean healthyOnly = stubInvo.getArgument(3);
				List<ServiceInstance> instances = new ArrayList<>(
						mockFieldVariableRepository.computeIfAbsent(serviceName, s -> new LinkedList<>()));
				int totalSize = instances.size();
				List<ServiceInstance> data = emptyList();
				if (offset < totalSize) {
					int toIndex = offset + pageSize > totalSize - 1 ? totalSize : offset + pageSize;
					data = instances.subList(offset, toIndex);
				}
				if (healthyOnly) {
					Iterator<ServiceInstance> iterator = data.iterator();
					while (iterator.hasNext()) {
						ServiceInstance instance = iterator.next();
						if (!instance.isHealthy()) {
							iterator.remove();
						}
					}
				}
				return new DefaultPage<>(offset, pageSize, data, totalSize);
			}).when(mockInstance).getInstances(Mockito.any(String.class), Mockito.anyInt(), Mockito.anyInt(),
					Mockito.anyBoolean());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableServiceInstance[0];
			}).when(mockInstance).getLocalInstance();
			Mockito.doAnswer((stubInvo) -> {
				ServiceInstance serviceInstance = stubInvo.getArgument(0);
				mockInstance.unregister(serviceInstance);
				mockInstance.register(serviceInstance);
				return null;
			}).when(mockInstance).update(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableRegistryURL[0];
			}).when(mockInstance).getUrl();
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
