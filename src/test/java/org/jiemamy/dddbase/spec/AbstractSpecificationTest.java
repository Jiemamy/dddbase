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
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
public class AbstractSpecificationTest {
	
	static final Specification<Number> OVER3 = new GreaterThanOrEqualsSpec(3);
	
	static final Specification<Number> OVER9 = new GreaterThanOrEqualsSpec(9);
	
	static final Specification<Number> UNDER5 = new LessThanOrEqualsSpec(5);
	

	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_and() throws Exception {
		Specification<Number> and = OVER3.and(OVER9);
		assertThat(and.isSatisfiedBy(0), is(false));
		assertThat(and.isSatisfiedBy(5), is(false));
		assertThat(and.isSatisfiedBy(10), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_or() throws Exception {
		Specification<Number> or = UNDER5.or(OVER9);
		assertThat(or.isSatisfiedBy(0), is(true));
		assertThat(or.isSatisfiedBy(4), is(true));
		assertThat(or.isSatisfiedBy(7), is(false));
		assertThat(or.isSatisfiedBy(10), is(true));
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_not() throws Exception {
		Specification<Number> not = OVER9.not();
		assertThat(not.isSatisfiedBy(0), is(true));
		assertThat(not.isSatisfiedBy(4), is(true));
		assertThat(not.isSatisfiedBy(7), is(true));
		assertThat(not.isSatisfiedBy(9), is(false));
		assertThat(not.isSatisfiedBy(10), is(false));
		
		Specification<Number> not2 = UNDER5.not().not();
		assertThat(not2.isSatisfiedBy(0), is(true));
		assertThat(not2.isSatisfiedBy(4), is(true));
		assertThat(not2.isSatisfiedBy(5), is(true));
		assertThat(not2.isSatisfiedBy(7), is(false));
	}
}
