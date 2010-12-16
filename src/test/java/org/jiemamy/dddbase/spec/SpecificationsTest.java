/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
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
package org.jiemamy.dddbase.spec;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link Specifications}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class SpecificationsTest {
	
	/**
	 * {@link Specifications#alwaysFalse()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_alwaysFalse() throws Exception {
		assertThat(Specifications.alwaysFalse().isSatisfiedBy(null), is(false));
		assertThat(Specifications.alwaysFalse().isSatisfiedBy(1), is(false));
		assertThat(Specifications.alwaysFalse().isSatisfiedBy(""), is(false));
		assertThat(Specifications.alwaysFalse().isSatisfiedBy(new Object()), is(false));
	}
	
	/**
	 * {@link Specifications#alwaysTrue()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test_alwaysTrue() throws Exception {
		assertThat(Specifications.alwaysTrue().isSatisfiedBy(null), is(true));
		assertThat(Specifications.alwaysTrue().isSatisfiedBy(1), is(true));
		assertThat(Specifications.alwaysTrue().isSatisfiedBy(""), is(true));
		assertThat(Specifications.alwaysTrue().isSatisfiedBy(new Object()), is(true));
	}
}
