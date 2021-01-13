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

import static org.apache.dubbo.common.constants.CommonConstants.DUBBO_VERSION_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PATH_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;
import static org.apache.dubbo.rpc.Constants.TOKEN_KEY;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * MockInvocation.java
 */
public class LegacyInvocation {

	static public Invocation mockInvocation1(String arg0) {
		String[] mockFieldVariableArg0 = new String[1];
		Invocation mockInstance = Mockito.spy(Invocation.class);
		mockFieldVariableArg0[0] = arg0;
		try {
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return mockInstance.getAttachments().get(key);
			}).when(mockInstance).getAttachment(Mockito.any(String.class), Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return "echo";
			}).when(mockInstance).getMethodName();
			Mockito.doAnswer((stubInvo) -> {
				Map<String, String> attachments = new HashMap<String, String>();
				attachments.put(PATH_KEY, "dubbo");
				attachments.put(GROUP_KEY, "dubbo");
				attachments.put(VERSION_KEY, "1.0.0");
				attachments.put(DUBBO_VERSION_KEY, "1.0.0");
				attachments.put(TOKEN_KEY, "sfag");
				attachments.put(TIMEOUT_KEY, "1000");
				return attachments;
			}).when(mockInstance).getAttachments();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInvoker();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getTargetServiceUniqueName();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return mockInstance.getAttachments().get(key);
			}).when(mockInstance).getAttachment(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).put(Mockito.any(Object.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getProtocolServiceKey();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttributes();
			Mockito.doAnswer((stubInvo) -> {
				return new Object[] { mockFieldVariableArg0[0] };
			}).when(mockInstance).getArguments();
			Mockito.doAnswer((stubInvo) -> {
				return new Class[] { String.class };
			}).when(mockInstance).getParameterTypes();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).get(Mockito.any(Object.class));
		} catch (Exception exception) {
		}
		return mockInstance;
	}

}
