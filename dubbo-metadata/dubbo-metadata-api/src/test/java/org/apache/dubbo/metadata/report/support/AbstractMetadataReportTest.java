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

package org.apache.dubbo.metadata.report.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.metadata.report.MetadataReport;
import org.apache.dubbo.metadata.report.identifier.KeyTypeEnum;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test {@link MetadataReport#saveExportedURLs(String, String, String)} method
 *
 * @since 2.7.8
 *        <p>
 *        Test {@link MetadataReport#getExportedURLs(String, String)} method
 * @since 2.7.8
 *        <p>
 *        Test {@link MetadataReport#getExportedURLsContent(String, String)}
 *        method
 * @since 2.7.8
 */

public class AbstractMetadataReportTest {

	Map<String, String> abstractMetadataReportStore = new ConcurrentHashMap<>();

	private AbstractMetadataReport abstractMetadataReport;

	@BeforeEach
	public void before() {
		URL url = URL.valueOf("zookeeper://" + NetUtils.getLocalAddress().getHostName()
				+ ":4444/org.apache.dubbo.TestService?version=1.0.0&application=vic");
		abstractMetadataReport = Mockito.mock(AbstractMetadataReport.class,
				Mockito.withSettings().useConstructor(url).defaultAnswer(Mockito.CALLS_REAL_METHODS));
		try {
			Mockito.doAnswer((stubInvo) -> {
				throw new UnsupportedOperationException(
						"This extension does not support working as a remote metadata center.");
			}).when(abstractMetadataReport).doGetExportedURLs(Mockito.any());
			Mockito.doThrow(new UnsupportedOperationException(
					"This extension does not support working as a remote metadata center."))
					.when(abstractMetadataReport).doRemoveMetadata(Mockito.any());
			Mockito.doThrow(new UnsupportedOperationException(
					"This extension does not support working as a remote metadata center."))
					.when(abstractMetadataReport).doGetSubscribedURLs(Mockito.any());
			Mockito.doThrow(new UnsupportedOperationException(
					"This extension does not support working as a remote metadata center."))
					.when(abstractMetadataReport).getServiceDefinition(Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				MetadataIdentifier consumerMetadataIdentifier = stubInvo.getArgument(0);
				String serviceParameterString = stubInvo.getArgument(1);
				abstractMetadataReportStore.put(consumerMetadataIdentifier.getUniqueKey(KeyTypeEnum.UNIQUE_KEY),
						serviceParameterString);
				return null;
			}).when(abstractMetadataReport).doStoreConsumerMetadata(Mockito.any(), Mockito.any());
			Mockito.doAnswer((stubInvo) -> {
				MetadataIdentifier providerMetadataIdentifier = stubInvo.getArgument(0);
				String serviceDefinitions = stubInvo.getArgument(1);
				abstractMetadataReportStore.put(providerMetadataIdentifier.getUniqueKey(KeyTypeEnum.UNIQUE_KEY),
						serviceDefinitions);
				return null;
			}).when(abstractMetadataReport).doStoreProviderMetadata(Mockito.any(), Mockito.any());
			Mockito.doThrow(new UnsupportedOperationException(
					"This extension does not support working as a remote metadata center."))
					.when(abstractMetadataReport).doSaveMetadata(Mockito.any(), Mockito.any());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		// set the simple name of current class as the application name
		ApplicationModel.getConfigManager().setApplication(new ApplicationConfig(getClass().getSimpleName()));
	}

	@AfterEach
	public void reset() {
		// reset
		ApplicationModel.reset();
	}

	@Test
	public void testGetProtocol() {
		URL url = URL.valueOf("dubbo://" + NetUtils.getLocalAddress().getHostName()
				+ ":4444/org.apache.dubbo.TestService?version=1.0.0&application=vic&side=provider");
		String protocol = abstractMetadataReport.getProtocol(url);
		assertEquals(protocol, "provider");

		URL url2 = URL.valueOf("consumer://" + NetUtils.getLocalAddress().getHostName()
				+ ":4444/org.apache.dubbo.TestService?version=1.0.0&application=vic");
		String protocol2 = abstractMetadataReport.getProtocol(url2);
		assertEquals(protocol2, "consumer");
	}

}
