/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2010/12/17
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
package org.jiemamy.dddbase.utils;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleMainEntity;

/**
 * {@link CloneUtil}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class CloneUtilTest {
	
	static final SampleMainEntity E1 = new SampleMainEntity(UUID.randomUUID());
	
	static final SampleMainEntity E2 = new SampleMainEntity(UUID.randomUUID());
	
	static final SampleMainEntity E3 = new SampleMainEntity(UUID.randomUUID());
	
	static final List<String> STR_LIST = Lists.newArrayList("a", "b", "c");
	
	static final Set<String> STR_SET = Sets.newHashSet("a", "b", "c");
	
	static final Map<String, String> STR_MAP = Maps.uniqueIndex(STR_LIST, new Function<String, String>() {
		
		public String apply(String from) {
			return from.toUpperCase();
		}
	});
	
	static final List<SampleMainEntity> ENT_LIST = Lists.newArrayList(new SampleMainEntity[] {
		E1,
		E2,
		E3
	});
	
	static final Set<SampleMainEntity> ENT_SET = Sets.newHashSet(new SampleMainEntity[] {
		E1,
		E2,
		E3
	});
	
	static final Map<UUID, SampleMainEntity> ENT_MAP = Maps.uniqueIndex(ENT_LIST,
			new Function<SampleMainEntity, UUID>() {
				
				public UUID apply(SampleMainEntity from) {
					return from.getId();
				}
			});
	
	
	/**
	 * {@link CloneUtil#cloneEntityArrayList(java.util.Collection)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneEntityArrayList() throws Exception {
		List<SampleMainEntity> list = CloneUtil.cloneEntityArrayList(ENT_LIST);
		assertThat(list, is(not(sameInstance(ENT_LIST))));
		assertThat(Iterables.elementsEqual(list, ENT_LIST), is(true));
		assertThat(list.get(0), is(equalTo(ENT_LIST.get(0))));
		assertThat(list.get(0), is(not(sameInstance(ENT_LIST.get(0)))));
	}
	
	/**
	 * {@link CloneUtil#cloneEntityHashMap(Map)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneEntityHashMap() throws Exception {
		Map<UUID, SampleMainEntity> map = CloneUtil.cloneEntityHashMap(ENT_MAP);
		assertThat(Maps.difference(map, ENT_MAP).areEqual(), is(true));
		assertThat(map, is(not(sameInstance(ENT_MAP))));
		
		UUID uuid = Iterables.get(map.keySet(), 0);
		assertThat(map.get(uuid), is(equalTo(ENT_MAP.get(uuid))));
		assertThat(map.get(uuid), is(not(sameInstance(ENT_MAP.get(uuid)))));
	}
	
	/**
	 * {@link CloneUtil#cloneEntityArrayList(java.util.Collection)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneEntityHashSet() throws Exception {
		Set<SampleMainEntity> set = CloneUtil.cloneEntityHashSet(ENT_SET);
		assertThat(set.containsAll(ENT_SET), is(true));
		assertThat(ENT_SET.containsAll(set), is(true));
		
		SampleMainEntity e0 = Iterables.get(ENT_SET, 0);
		for (SampleMainEntity e : set) {
			if (e.equals(e0)) {
				assertThat(e, is(not(sameInstance(e0))));
			}
		}
	}
	
	/**
	 * {@link CloneUtil#cloneValueHashSet(java.util.Collection)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneEntityLinkedHashSet() throws Exception {
		Set<SampleMainEntity> set = CloneUtil.cloneEntityLinkedHashSet(ENT_SET);
		assertThat(Iterables.elementsEqual(set, ENT_SET), is(true));
		
		Iterator<SampleMainEntity> itrOrg = ENT_SET.iterator();
		for (SampleMainEntity ce : set) {
			SampleMainEntity oe = itrOrg.next();
			assertThat(ce, is(equalTo(oe)));
			assertThat(ce, is(not(sameInstance(oe))));
		}
	}
	
	/**
	 * {@link CloneUtil#cloneEntityArrayList(java.util.Collection)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneValueArrayList() throws Exception {
		List<String> list = CloneUtil.cloneValueArrayList(STR_LIST);
		assertThat(list, is(not(sameInstance(STR_LIST))));
		assertThat(Iterables.elementsEqual(list, STR_LIST), is(true));
		assertThat(list.get(0), is(equalTo(STR_LIST.get(0))));
		assertThat(list.get(0), is(sameInstance(STR_LIST.get(0))));
	}
	
	/**
	 * {@link CloneUtil#cloneEntityHashMap(Map)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneValueHashMap() throws Exception {
		Map<String, String> map = CloneUtil.cloneValueHashMap(STR_MAP);
		assertThat(Maps.difference(map, STR_MAP).areEqual(), is(true));
		assertThat(map, is(not(sameInstance(STR_MAP))));
		
		String key = Iterables.get(map.keySet(), 0);
		assertThat(map.get(key), is(equalTo(STR_MAP.get(key))));
		assertThat(map.get(key), is(sameInstance(STR_MAP.get(key))));
	}
	
	/**
	 * {@link CloneUtil#cloneValueHashSet(java.util.Collection)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneValueHashSet() throws Exception {
		Set<String> set = CloneUtil.cloneValueHashSet(STR_SET);
		assertThat(Iterables.elementsEqual(set, STR_SET), is(true));
		
		Iterator<String> itr = STR_SET.iterator();
		String e0 = itr.next();
		String e1 = itr.next();
		String e2 = itr.next();
		
		for (String e : set) {
			if (e.equals(e0)) {
				assertThat(e, is(sameInstance(e0)));
			} else if (e.equals(e1)) {
				assertThat(e, is(sameInstance(e1)));
			} else if (e.equals(e2)) {
				assertThat(e, is(sameInstance(e2)));
			} else {
				fail();
			}
		}
	}
	
	/**
	 * {@link CloneUtil#cloneValueHashSet(java.util.Collection)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_cloneValueLinkedHashSet() throws Exception {
		Set<String> set = CloneUtil.cloneValueLinkedHashSet(STR_SET);
		assertThat(Iterables.elementsEqual(set, STR_SET), is(true));
		
		Iterator<String> itrOrg = STR_SET.iterator();
		for (String ce : set) {
			String oe = itrOrg.next();
			assertThat(ce, is(equalTo(oe)));
			assertThat(ce, is(sameInstance(oe)));
		}
	}
}
