/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
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
package org.jiemamy.dddbase;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleMainEntity;

/**
 * {@link Entity}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class EntityTest {
	
	private static final UUID ID1 = UUID.randomUUID();
	
	private static final UUID ID2 = UUID.randomUUID();
	
	
	/**
	 * IDを指定しないエンティティは作れない。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void test01_IDを指定しないエンティティは作れない() throws Exception {
		new SampleMainEntity(null);
	}
	
	/**
	 * {@link Entity#equals(Object)}と{@link Entity#hashCode()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_equals_hashCode() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleMainEntity e2a = new SampleMainEntity(ID2);
		SampleMainEntity e2b = new SampleMainEntity(ID2);
		
		assertThat(e1.equals(e1), is(true));
		assertThat(e1.hashCode(), is(e1.hashCode()));
		
		assertThat(e1.equals(e2a), is(false));
		assertThat(e1.hashCode(), is(not(e2a.hashCode())));
		
		assertThat(e1.equals(e2b), is(false));
		assertThat(e1.hashCode(), is(not(e2b.hashCode())));
		
		assertThat(e2a.equals(e1), is(false));
		assertThat(e2a.hashCode(), is(not(e1.hashCode())));
		
		assertThat(e2a.equals(e2a), is(true));
		assertThat(e2a.hashCode(), is(e2a.hashCode()));
		
		assertThat(e2a.equals(e2b), is(true));
		assertThat(e2a.hashCode(), is(e2b.hashCode()));
		
		assertThat(e1.equals(new Object()), is(false));
		assertThat(e1.equals(null), is(false));
	}
}
