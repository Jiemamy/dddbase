/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2010/05/19
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Iterator;

import org.junit.Test;

import org.jiemamy.dddbase.ValueObjectBuilder.BuilderConfigurator;
import org.jiemamy.dddbase.sample.SampleValueObject;
import org.jiemamy.dddbase.sample.SampleValueObjectBuilder;

/**
 * {@link ValueObjectBuilder}のテストクラス。
 * 
 * @version $Id$
 * @author Keisuke.K
 */
public class ValueObjectBuilderTest {
	
	/**
	 * Test method for {@link org.jiemamy.dddbase.ValueObjectBuilder#addConfigurator(org.jiemamy.dddbase.ValueObjectBuilder.BuilderConfigurator)}.
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_addConfigurator() throws Exception {
		BuilderMock builder = new BuilderMock();
		
		@SuppressWarnings("unchecked")
		BuilderConfigurator<BuilderMock> configurator1 = mock(BuilderConfigurator.class);
		@SuppressWarnings("unchecked")
		BuilderConfigurator<BuilderMock> configurator2 = mock(BuilderConfigurator.class);
		
		builder.addConfigurator(configurator1);
		
		assertThat(builder.configurators.size(), is(1));
		assertThat(builder.configurators.iterator().next(), is(configurator1));
		
		builder.addConfigurator(configurator2);
		
		assertThat(builder.configurators.size(), is(2));
		
		Iterator<BuilderConfigurator<BuilderMock>> iterator = builder.configurators.iterator();
		assertThat(iterator.next(), is(configurator1));
		assertThat(iterator.next(), is(configurator2));
	}
	
	/**
	 * Test method for {@link org.jiemamy.dddbase.ValueObjectBuilder#build()}.
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_build() throws Exception {
		BuilderMock builder = new BuilderMock() {
			
			@Override
			protected ValueObject createValueObject() {
				return new ValueObject() {
					
					@Override
					public String toString() {
						return "success";
					}
				};
			}
		};
		
		@SuppressWarnings("unchecked")
		BuilderConfigurator<BuilderMock> configurator = mock(BuilderConfigurator.class);
		builder.addConfigurator(configurator);
		
		ValueObject vo = builder.build();
		
		verify(configurator).configure(builder);
		assertThat(vo, is(notNullValue()));
		assertThat(vo.toString(), is("success"));
	}
	
	/**
	 * Test method for {@link org.jiemamy.dddbase.ValueObjectBuilder#build()}.
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_build2() throws Exception {
		SampleValueObject foo10 = new SampleValueObjectBuilder().setString("foo").setNumber(10).build();
		SampleValueObject foo20 = new SampleValueObjectBuilder().setString("foo").setNumber(20).build();
		SampleValueObject bar10 = new SampleValueObjectBuilder().setString("bar").setNumber(10).build();
		SampleValueObject bar20a = new SampleValueObjectBuilder().setString("bar").setNumber(20).build();
		SampleValueObject bar20b = new SampleValueObjectBuilder().setString("bar").setNumber(20).build();
		SampleValueObject null10 = new SampleValueObjectBuilder().setNumber(10).build();
		SampleValueObject null20a = new SampleValueObjectBuilder().setNumber(20).build();
		SampleValueObject null20b = new SampleValueObjectBuilder().setNumber(20).build();
		
		assertThat(foo10.equals(foo10), is(true));
		assertThat(foo10.equals(foo20), is(false));
		assertThat(foo10.equals(bar10), is(false));
		assertThat(foo10.equals(bar20a), is(false));
		assertThat(foo10.equals(null20a), is(false));
		assertThat(null20a.equals(foo20), is(false));
		assertThat(null10.equals(null20a), is(false));
		assertThat(null20a.equals(null20b), is(true));
		assertThat(bar20a.equals(bar20b), is(true));
		assertThat(foo10.equals(new Object()), is(false));
		assertThat(foo10.equals(null), is(false));
		
		assertThat(foo10.hashCode(), is(foo10.hashCode()));
		assertThat(foo10.hashCode(), is(not(null20a.hashCode())));
		assertThat(bar20a.hashCode(), is(bar20b.hashCode()));
	}
	
	/**
	 * {@link ValueObjectBuilder#apply(ValueObject)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_apply() throws Exception {
		SampleValueObject foo10 = new SampleValueObjectBuilder().setString("foo").setNumber(10).build();
		SampleValueObject applied = new SampleValueObjectBuilder().setString("bar").apply(foo10);
		
		assertThat(applied.getNumber(), is(10));
		assertThat(applied.getString(), is("bar"));
	}
	
	
	// テスト用ビルダークラス
	static class BuilderMock extends ValueObjectBuilder<ValueObject, BuilderMock> {
		
		@Override
		protected void apply(ValueObject vo, BuilderMock builder) {
		}
		
		@Override
		protected ValueObject createValueObject() {
			return null;
		}
		
		@Override
		protected BuilderMock getThis() {
			return this;
		}
		
		@Override
		protected BuilderMock newInstance() {
			return new BuilderMock();
		}
		
	}
}
