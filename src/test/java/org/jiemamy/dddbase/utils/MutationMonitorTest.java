/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2010/12/14
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.lang.CharEncoding;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link MutationMonitor}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class MutationMonitorTest {
	
	private PrintStream savedStream;
	
	private PrintStream mockStream;
	
	private ByteArrayOutputStream baos;
	
	private boolean savedDebugStatus;
	
	
	/**
	 * テストを初期化する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Before
	public void setUp() throws Exception {
		savedStream = System.out;
		baos = new ByteArrayOutputStream();
		mockStream = new PrintStream(baos);
		System.setOut(mockStream);
		
		savedDebugStatus = MutationMonitor.isDebug();
	}
	
	/**
	 * テストの情報を破棄する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@After
	public void tearDown() throws Exception {
		MutationMonitor.setDebug(savedDebugStatus);
		
		System.setOut(savedStream);
		mockStream.close();
	}
	
	/**
	 * モニタ付きListを操作した場合にログが出力されること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_デバッグ時にモニタ対象Listを操作した場合にログが出力されること() throws Exception {
		MutationMonitor.setDebug(true);
		List<Integer> list = Lists.newArrayList();
		list.add(1);
		list.add(2);
		List<Integer> wraped = MutationMonitor.monitor(list);
		
		assertThat(wraped, is(not(sameInstance(list))));
		assertThat(list.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
		
		wraped.add(3);
		
		assertThat(list.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), endsWith("[main] WARN  o.j.dddbase.utils.MutationMonitor -"
				+ " public abstract boolean java.util.List.add(java.lang.Object) on [1, 2]\n"));
	}
	
	/**
	 * 非デバッグ時にモニタ対象Listを操作した場合にログが出力されないこと()。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_非デバッグ時にモニタ対象Listを操作した場合にログが出力されないこと() throws Exception {
		MutationMonitor.setDebug(false);
		List<Integer> list = Lists.newArrayList();
		list.add(1);
		list.add(2);
		List<Integer> wraped = MutationMonitor.monitor(list);
		
		assertThat(wraped, is(sameInstance(list)));
		assertThat(list.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		wraped.add(3);
		
		assertThat(list.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
	}
	
	/**
	 * モニタ付きMapを操作した場合にログが出力されること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_デバッグ時にモニタ対象Mapを操作した場合にログが出力されること() throws Exception {
		MutationMonitor.setDebug(true);
		Map<Integer, Integer> map = Maps.newHashMap();
		map.put(1, 10);
		map.put(2, 20);
		Map<Integer, Integer> wraped = MutationMonitor.monitor(map);
		
		assertThat(wraped, is(not(sameInstance(map))));
		assertThat(map.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
		
		wraped.put(3, 30);
		
		assertThat(map.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), containsString("[main] WARN  o.j.dddbase.utils.MutationMonitor -"
				+ " public abstract java.lang.Object java.util.Map.put(java.lang.Object,java.lang.Object) on "));
	}
	
	/**
	 * 非デバッグ時にモニタ対象Mapを操作した場合にログが出力されないこと()。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_非デバッグ時にモニタ対象Mapを操作した場合にログが出力されないこと() throws Exception {
		MutationMonitor.setDebug(false);
		Map<Integer, Integer> map = Maps.newHashMap();
		map.put(1, 10);
		map.put(2, 20);
		Map<Integer, Integer> wraped = MutationMonitor.monitor(map);
		
		assertThat(wraped, is(sameInstance(map)));
		assertThat(map.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		wraped.put(3, 30);
		
		assertThat(map.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
	}
	
	/**
	 * モニタ付きSetを操作した場合にログが出力されること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_デバッグ時にモニタ対象Setを操作した場合にログが出力されること() throws Exception {
		MutationMonitor.setDebug(true);
		Set<Integer> set = Sets.newHashSet();
		set.add(1);
		set.add(2);
		Set<Integer> wraped = MutationMonitor.monitor(set);
		
		assertThat(wraped, is(not(sameInstance(set))));
		assertThat(set.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
		
		wraped.add(3);
		
		assertThat(set.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), containsString("[main] WARN  o.j.dddbase.utils.MutationMonitor -"
				+ " public abstract boolean java.util.Set.add(java.lang.Object) on "));
	}
	
	/**
	 * 非デバッグ時にモニタ対象Setを操作した場合にログが出力されないこと。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_非デバッグ時にモニタ対象Setを操作した場合にログが出力されないこと() throws Exception {
		MutationMonitor.setDebug(false);
		Set<Integer> set = Sets.newHashSet();
		set.add(1);
		set.add(2);
		Set<Integer> wraped = MutationMonitor.monitor(set);
		
		assertThat(wraped, is(sameInstance(set)));
		assertThat(set.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		wraped.add(3);
		
		assertThat(set.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
	}
	
	/**
	 * モニタ付きSortedSetを操作した場合にログが出力されること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_デバッグ時にモニタ対象SortedSetを操作した場合にログが出力されること() throws Exception {
		MutationMonitor.setDebug(true);
		SortedSet<Integer> set = Sets.newTreeSet();
		set.add(1);
		set.add(2);
		SortedSet<Integer> wraped = MutationMonitor.monitor(set);
		
		assertThat(wraped, is(not(sameInstance(set))));
		assertThat(set.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
		
		wraped.add(3);
		
		assertThat(set.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), containsString("[main] WARN  o.j.dddbase.utils.MutationMonitor -"
				+ " public abstract boolean java.util.Set.add(java.lang.Object) on "));
	}
	
	/**
	 * 非デバッグ時にモニタ対象SortedSetを操作した場合にログが出力されないこと。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_非デバッグ時にモニタ対象SortedSetを操作した場合にログが出力されないこと() throws Exception {
		MutationMonitor.setDebug(false);
		SortedSet<Integer> set = Sets.newTreeSet();
		set.add(1);
		set.add(2);
		SortedSet<Integer> wraped = MutationMonitor.monitor(set);
		
		assertThat(wraped, is(sameInstance(set)));
		assertThat(set.size(), is(2));
		assertThat(wraped.size(), is(2));
		
		wraped.add(3);
		
		assertThat(set.size(), is(3));
		assertThat(wraped.size(), is(3));
		
		mockStream.flush();
		assertThat(baos.toString(CharEncoding.UTF_8), is(""));
	}
}
