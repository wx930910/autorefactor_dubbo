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

package org.apache.dubbo.cache;

import org.apache.dubbo.rpc.RpcInvocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.alibaba.dubbo.cache.Cache;
import com.alibaba.dubbo.cache.CacheFactory;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;

public class CacheTest {

	static public Invocation mockInvocation1() {
		Invocation mockInstance = Mockito.spy(Invocation.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttachments();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInvoker();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getMethodName();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttributes();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getTargetServiceUniqueName();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttachment(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return new Class[0];
			}).when(mockInstance).getParameterTypes();
			Mockito.doAnswer((stubInvo) -> {
				return new Object[0];
			}).when(mockInstance).getArguments();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttachment(Mockito.any(String.class), Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).get(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).put(Mockito.any(Object.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getProtocolServiceKey();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

	@Test
	public void testCacheFactory() {
		URL url = URL.valueOf("test://test:11/test?cache=jacache&.cache.write.expire=1");
		CacheFactory cacheFactory = new MyCacheFactory();
		Invocation invocation = CacheTest.mockInvocation1();
		Cache cache = cacheFactory.getCache(url, invocation);
		cache.put("testKey", "testValue");

		org.apache.dubbo.cache.CacheFactory factory = cacheFactory;
		org.apache.dubbo.common.URL u = org.apache.dubbo.common.URL
				.valueOf("test://test:11/test?cache=jacache&.cache.write.expire=1");
		org.apache.dubbo.rpc.Invocation inv = new RpcInvocation();
		org.apache.dubbo.cache.Cache c = factory.getCache(u, inv);
		String v = (String) c.get("testKey");
		Assertions.assertEquals("testValue", v);
	}
}
