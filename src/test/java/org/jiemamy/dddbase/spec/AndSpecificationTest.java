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
package org.jiemamy.dddbase.spec;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * {@link AndSpecification}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class AndSpecificationTest {
	
	static final Specification<Number> OVER3 = new GreaterThanOrEqualsSpec(3);
	
	static final Specification<Number> OVER9 = new GreaterThanOrEqualsSpec(9);
	
	static final Specification<Number> UNDER5 = new LessThanOrEqualsSpec(5);
	
	
	/**
	 * 3以下かつ9以下。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_over3_over9() throws Exception {
		AndSpecification<Number> and = new AndSpecification<Number>(OVER3, OVER9);
		assertThat(and.isSatisfiedBy(0), is(false));
		assertThat(and.isSatisfiedBy(5), is(false));
		assertThat(and.isSatisfiedBy(10), is(true));
	}
	
	/**
	 * 3以上かつ5以下。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_over3_under5() throws Exception {
		AndSpecification<Number> and = new AndSpecification<Number>(OVER3, UNDER5);
		assertThat(and.isSatisfiedBy(0), is(false));
		assertThat(and.isSatisfiedBy(4), is(true));
		assertThat(and.isSatisfiedBy(7), is(false));
	}
	
	/**
	 * 5以下かつ9以上。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_under5_over9() throws Exception {
		AndSpecification<Number> and = new AndSpecification<Number>(UNDER5, OVER9);
		assertThat(and.isSatisfiedBy(0), is(false));
		assertThat(and.isSatisfiedBy(4), is(false));
		assertThat(and.isSatisfiedBy(7), is(false));
		assertThat(and.isSatisfiedBy(10), is(false));
	}
}
