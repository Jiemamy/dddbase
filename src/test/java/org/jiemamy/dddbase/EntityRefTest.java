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
package org.jiemamy.dddbase;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleMainEntity;

/**
 * {@link EntityRef}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class EntityRefTest {
	
	private static final UUID ID1 = UUID.randomUUID();
	
	private static final UUID ID2 = UUID.randomUUID();
	
	
	/**
	 * equalsの挙動テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_equals() throws Exception {
		EntityRef<Entity> ref1 = new EntityRef<Entity>(ID1);
		EntityRef<Entity> ref2a = new EntityRef<Entity>(ID2);
		EntityRef<Entity> ref2b = EntityRef.of(ID2);
		
		assertThat(ref1.equals(ref1), is(true));
		assertThat(ref1.hashCode(), is(ref1.hashCode()));
		
		assertThat(ref1.equals(ref2a), is(false));
		assertThat(ref1.hashCode(), is(not(ref2a.hashCode())));
		
		assertThat(ref1.equals(ref2b), is(false));
		assertThat(ref1.hashCode(), is(not(ref2b.hashCode())));
		
		assertThat(ref2a.equals(ref1), is(false));
		assertThat(ref2a.hashCode(), is(not(ref1.hashCode())));
		
		assertThat(ref2a.equals(ref2a), is(true));
		assertThat(ref2a.hashCode(), is(ref2a.hashCode()));
		
		assertThat(ref2a.equals(ref2b), is(true));
		assertThat(ref2a.hashCode(), is(ref2b.hashCode()));
		
		assertThat(ref1.equals(new Object()), is(false));
		assertThat(ref1.equals(null), is(false));
	}
	
	/**
	 * {@link EntityRef#isReferenceOf(Entity)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_isReferenceOf() throws Exception {
		EntityRef<Entity> ref1 = new EntityRef<Entity>(ID1);
		EntityRef<Entity> ref2a = new EntityRef<Entity>(ID2);
		EntityRef<Entity> ref2b = EntityRef.of(ID2);
		
		Entity e1 = new SampleMainEntity(ID1);
		Entity e2 = new SampleMainEntity(ID2);
		
		assertThat(ref1.isReferenceOf(e1), is(true));
		assertThat(ref1.isReferenceOf(e2), is(false));
		assertThat(ref2a.isReferenceOf(e1), is(false));
		assertThat(ref2a.isReferenceOf(e2), is(true));
		assertThat(ref2b.isReferenceOf(e1), is(false));
		assertThat(ref2b.isReferenceOf(e2), is(true));
	}
}
