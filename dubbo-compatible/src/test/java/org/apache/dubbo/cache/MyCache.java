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

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;

import com.alibaba.dubbo.cache.Cache;
import com.alibaba.dubbo.common.URL;

public class MyCache {

	static public Cache mockCache1(URL url) {
		Map<Object, Object> mockFieldVariableMap = new HashMap<Object, Object>();
		Cache mockInstance = Mockito.spy(Cache.class);
		try {
			Mockito.doAnswer((stubInvo) -> {
				Object key = stubInvo.getArgument(0);
				Object value = stubInvo.getArgument(1);
				mockFieldVariableMap.put(key, value);
				return null;
			}).when(mockInstance).put(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				Object key = stubInvo.getArgument(0);
				return mockFieldVariableMap.get(key);
			}).when(mockInstance).get(Mockito.any());
		} catch (Exception exception) {
		}
		return mockInstance;
	}
}
