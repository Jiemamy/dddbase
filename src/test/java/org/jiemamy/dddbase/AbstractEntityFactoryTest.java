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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import com.google.common.collect.Iterables;

import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleMainEntity;
import org.jiemamy.dddbase.sample.SampleMainEntityFactory;
import org.jiemamy.dddbase.sample.SampleSubEntity;

/**
 * {@link UUIDEntityFactory}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class AbstractEntityFactoryTest {
	
	private static final UUID ID1 = UUID.randomUUID();
	
	private static final UUID ID2 = UUID.randomUUID();
	
	private static final UUID ID3 = UUID.randomUUID();
	
	
	/**
	 * 生成したインスタンスの挙動テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_生成したインスタンスの挙動テスト() throws Exception {
		SampleMainEntity e1a = new SampleMainEntityFactory().setString("foo").build(ID1);
		SampleMainEntity e1b = new SampleMainEntityFactory().setString("foo").build(ID1);
		SampleMainEntity e2 = new SampleMainEntityFactory().setString("foo").build(ID2);
		SampleMainEntity e3 = new SampleMainEntityFactory().setString("foo").build();
		SampleMainEntity e4 = new SampleMainEntityFactory().setString("foo").build();
		SampleMainEntity e5 = new SampleMainEntityFactory().addValue("foo").build();
		SampleMainEntity e6 = new SampleMainEntityFactory("bar").addChild(new SampleSubEntity(ID3)).build();
		
		assertThat(e1a.equals(e1b), is(true));
		assertThat(e1a.equals(e2), is(false));
		assertThat(e1a.equals(e3), is(false));
		assertThat(e1a.equals(e4), is(false));
		assertThat(e1a.equals(e5), is(false));
		assertThat(e3.equals(e4), is(false));
		
		assertThat(e1a.getId(), is(ID1));
		assertThat(e1a.getString(), is("foo"));
		assertThat(e1a.getValues().size(), is(0));
		assertThat(e1a.getSubEntities().size(), is(0));
		
		assertThat(e1b.getId(), is(ID1));
		assertThat(e1b.getString(), is("foo"));
		assertThat(e1b.getValues().size(), is(0));
		assertThat(e1b.getSubEntities().size(), is(0));
		
		assertThat(e2.getId(), is(ID2));
		assertThat(e2.getString(), is("foo"));
		assertThat(e2.getValues().size(), is(0));
		assertThat(e2.getSubEntities().size(), is(0));
		
		assertThat(e3.getString(), is("foo"));
		assertThat(e3.getValues().size(), is(0));
		assertThat(e3.getSubEntities().size(), is(0));
		
		assertThat(e4.getString(), is("foo"));
		assertThat(e4.getValues().size(), is(0));
		assertThat(e4.getSubEntities().size(), is(0));
		
		assertThat(e5.getString(), is(nullValue()));
		assertThat(e5.getValues().size(), is(1));
		assertThat(Iterables.getOnlyElement(e5.getValues()), is("foo"));
		assertThat(e5.getSubEntities().size(), is(0));
		
		assertThat(e6.getString(), is("bar"));
		assertThat(e6.getValues().size(), is(0));
		assertThat(e6.getSubEntities().size(), is(1));
		SampleSubEntity sub = Iterables.getOnlyElement(e6.getSubEntities());
		assertThat(sub.getId(), is(ID3));
	}
	
}
