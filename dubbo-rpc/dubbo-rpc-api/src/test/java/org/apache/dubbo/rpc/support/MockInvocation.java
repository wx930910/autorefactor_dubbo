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
package org.apache.dubbo.rpc.support;

import static org.apache.dubbo.common.constants.CommonConstants.DUBBO_VERSION_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PATH_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.TIMEOUT_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;
import static org.apache.dubbo.rpc.Constants.TOKEN_KEY;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.rpc.AttachmentsAdapter;
import org.apache.dubbo.rpc.Invocation;
import org.mockito.Mockito;

/**
 * MockInvocation.java
 */
public class MockInvocation {

	static public Invocation mockInvocation1() {
		Map<String, Object>[] mockFieldVariableAttachments = new Map[1];
		Invocation mockInstance = Mockito.spy(Invocation.class);
		mockFieldVariableAttachments[0] = new HashMap<>();
		mockFieldVariableAttachments[0].put(PATH_KEY, "dubbo");
		mockFieldVariableAttachments[0].put(GROUP_KEY, "dubbo");
		mockFieldVariableAttachments[0].put(VERSION_KEY, "1.0.0");
		mockFieldVariableAttachments[0].put(DUBBO_VERSION_KEY, "1.0.0");
		mockFieldVariableAttachments[0].put(TOKEN_KEY, "sfag");
		mockFieldVariableAttachments[0].put(TIMEOUT_KEY, "1000");
		try {
			Mockito.doAnswer((stubInvo) -> {
				return mockFieldVariableAttachments[0];
			}).when(mockInstance).getObjectAttachments();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				Object value = stubInvo.getArgument(1);
				mockInstance.setObjectAttachment(key, value);
				return null;
			}).when(mockInstance).setAttachment(Mockito.any(String.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return new Class[] { String.class };
			}).when(mockInstance).getParameterTypes();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				Object value = stubInvo.getArgument(1);
				mockInstance.setObjectAttachmentIfAbsent(key, value);
				return null;
			}).when(mockInstance).setAttachmentIfAbsent(Mockito.any(String.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				Object value = stubInvo.getArgument(1);
				mockFieldVariableAttachments[0].put(key, value);
				return null;
			}).when(mockInstance).setObjectAttachmentIfAbsent(Mockito.any(String.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).put(Mockito.any(Object.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				String value = stubInvo.getArgument(1);
				mockInstance.setObjectAttachmentIfAbsent(key, value);
				return null;
			}).when(mockInstance).setAttachmentIfAbsent(Mockito.any(String.class), Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return new AttachmentsAdapter.ObjectToStringMap(mockFieldVariableAttachments[0]);
			}).when(mockInstance).getAttachments();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				String value = stubInvo.getArgument(1);
				mockInstance.setObjectAttachment(key, value);
				return null;
			}).when(mockInstance).setAttachment(Mockito.any(String.class), Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return "echo";
			}).when(mockInstance).getMethodName();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return (String) mockInstance.getObjectAttachments().get(key);
			}).when(mockInstance).getAttachment(Mockito.any(String.class), Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return mockFieldVariableAttachments[0].get(key);
			}).when(mockInstance).getObjectAttachment(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getInvoker();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				Object defaultValue = stubInvo.getArgument(1);
				Object result = mockFieldVariableAttachments[0].get(key);
				if (result == null) {
					return defaultValue;
				}
				return result;
			}).when(mockInstance).getObjectAttachment(Mockito.any(String.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				return (String) mockInstance.getObjectAttachments().get(key);
			}).when(mockInstance).getAttachment(Mockito.any(String.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).get(Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getAttributes();
			Mockito.doAnswer((stubInvo) -> {
				return new Object[] { "aa" };
			}).when(mockInstance).getArguments();
			Mockito.doAnswer((stubInvo) -> {
				String key = stubInvo.getArgument(0);
				Object value = stubInvo.getArgument(1);
				mockFieldVariableAttachments[0].put(key, value);
				return null;
			}).when(mockInstance).setObjectAttachment(Mockito.any(String.class), Mockito.any(Object.class));
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getTargetServiceUniqueName();
			Mockito.doAnswer((stubInvo) -> {
				return null;
			}).when(mockInstance).getProtocolServiceKey();
			Mockito.doAnswer((stubInvo) -> {
				return "DemoService";
			}).when(mockInstance).getServiceName();
		} catch (Exception exception) {
		}
		return mockInstance;
	}

}