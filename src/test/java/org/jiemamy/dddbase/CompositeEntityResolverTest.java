/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2011/01/17
 *
 * This file is part of Jiemamy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.jiemamy.dddbase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleMainEntity;

/**
 * {@link CompositeEntityResolver}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class CompositeEntityResolverTest {
	
	static final UUID ID0 = UUID.randomUUID();
	
	static final UUID ID1 = UUID.randomUUID();
	
	static final UUID ID2 = UUID.randomUUID();
	
	static final Entity E0 = new SampleMainEntity(ID0);
	
	static final Entity E1 = new SampleMainEntity(ID1);
	
	static final Entity E2 = new SampleMainEntity(ID2);
	

	/**
	 * {@link CompositeEntityResolver#contains}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_contains() throws Exception {
		EntityResolver res1 = mock(EntityResolver.class);
		EntityResolver res2 = mock(EntityResolver.class);
		
		when(res1.contains(ID0)).thenReturn(false);
		when(res1.contains(ID1)).thenReturn(true);
		when(res1.contains(ID2)).thenReturn(false);
		when(res2.contains(ID0)).thenReturn(false);
		when(res2.contains(ID1)).thenReturn(false);
		when(res2.contains(ID2)).thenReturn(true);
		when(res1.contains(DefaultEntityRef.of(ID0))).thenReturn(false);
		when(res1.contains(DefaultEntityRef.of(ID1))).thenReturn(true);
		when(res1.contains(DefaultEntityRef.of(ID2))).thenReturn(false);
		when(res2.contains(DefaultEntityRef.of(ID0))).thenReturn(false);
		when(res2.contains(DefaultEntityRef.of(ID1))).thenReturn(false);
		when(res2.contains(DefaultEntityRef.of(ID2))).thenReturn(true);
		
		CompositeEntityResolver com = new CompositeEntityResolver(res1, res2);
		
		assertThat(com.contains(ID0), is(false));
		assertThat(com.contains(ID1), is(true));
		assertThat(com.contains(ID2), is(true));
		assertThat(com.contains(DefaultEntityRef.of(ID0)), is(false));
		assertThat(com.contains(DefaultEntityRef.of(ID1)), is(true));
		assertThat(com.contains(DefaultEntityRef.of(ID2)), is(true));
	}
	
	/**
	 * {@link CompositeEntityResolver#resolve}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_resolve() throws Exception {
		EntityResolver res1 = mock(EntityResolver.class);
		EntityResolver res2 = mock(EntityResolver.class);
		
		when(res1.resolve(ID0)).thenThrow(new EntityNotFoundException("id=" + ID0));
		when(res1.resolve(ID1)).thenReturn(E1);
		when(res1.resolve(ID2)).thenThrow(new EntityNotFoundException("id=" + ID2));
		when(res2.resolve(ID0)).thenThrow(new EntityNotFoundException("id=" + ID0));
		when(res2.resolve(ID1)).thenThrow(new EntityNotFoundException("id=" + ID1));
		when(res2.resolve(ID2)).thenReturn(E2);
		when(res1.resolve(DefaultEntityRef.of(ID0))).thenThrow(new EntityNotFoundException("id=" + ID0));
		when(res1.resolve(DefaultEntityRef.of(ID1))).thenReturn(E1);
		when(res1.resolve(DefaultEntityRef.of(ID2))).thenThrow(new EntityNotFoundException("id=" + ID2));
		when(res2.resolve(DefaultEntityRef.of(ID0))).thenThrow(new EntityNotFoundException("id=" + ID0));
		when(res2.resolve(DefaultEntityRef.of(ID1))).thenThrow(new EntityNotFoundException("id=" + ID1));
		when(res2.resolve(DefaultEntityRef.of(ID2))).thenReturn(E2);
		
		CompositeEntityResolver com = new CompositeEntityResolver(res1, res2);
		
		try {
			com.resolve(ID0);
			fail();
		} catch (EntityNotFoundException e) {
			assertThat(e.getMessage(), is("id=" + ID0));
		}
		
		assertThat(com.resolve(ID1), is(E1));
		assertThat(com.resolve(ID2), is(E2));
		
		try {
			com.resolve(DefaultEntityRef.of(ID0));
			fail();
		} catch (EntityNotFoundException e) {
			assertThat(e.getMessage(), is("id=" + ID0));
		}
		
		assertThat(com.resolve(DefaultEntityRef.of(ID1)), is(E1));
		assertThat(com.resolve(DefaultEntityRef.of(ID2)), is(E2));
	}
}
