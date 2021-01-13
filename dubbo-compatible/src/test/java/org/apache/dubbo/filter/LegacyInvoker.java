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
package org.apache.dubbo.filter;

import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.service.DemoService;
import org.mockito.Mockito;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;

public class LegacyInvoker {

	static public Invoker<FilterTest> mockInvoker1(URL url) {
		URL[] mockFieldVariableUrl = new URL[1];
		Class<FilterTest>[] mockFieldVariableType = new Class[1];
		boolean mockFieldVariableHasException = false;
		Invoker<FilterTest> mockInstance = Mockito.spy(Invoker.class);
		mockFieldVariableUrl[0] = url;
		mockFieldVariableType[0] = (Class) DemoService.class;
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableType[0];
			}).when(mockInstance).getInterface();
			Mockito.doAnswer((stubInvo) -> {
				return false;
			}).when(mockInstance).isAvailable();
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableUrl[0];
			}).when(mockInstance).getUrl();
			Mockito.doAnswer((stubInvo) -> {
				AppResponse result = new AppResponse();
				if (!mockFieldVariableHasException) {
					result.setValue("alibaba");
				} else {
					result.setException(new RuntimeException("mocked exception"));
				}
				return new Result.CompatibleResult(result);
			}).when(mockInstance).invoke(Mockito.any(Invocation.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}

}
