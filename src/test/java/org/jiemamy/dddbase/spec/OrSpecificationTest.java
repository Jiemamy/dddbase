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
 * {@link OrSpecification}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class OrSpecificationTest {
	
	static final Specification<Number> OVER3 = new GreaterThanOrEqualsSpec(3);
	
	static final Specification<Number> OVER9 = new GreaterThanOrEqualsSpec(9);
	
	static final Specification<Number> UNDER5 = new LessThanOrEqualsSpec(5);
	
	
	/**
	 * 3以上または9以上。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_over3_over9() throws Exception {
		OrSpecification<Number> or = new OrSpecification<Number>(OVER3, OVER9);
		assertThat(or.isSatisfiedBy(0), is(false));
		assertThat(or.isSatisfiedBy(5), is(true));
		assertThat(or.isSatisfiedBy(10), is(true));
	}
	
	/**
	 * 3以上または5以下。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_over3_under5() throws Exception {
		OrSpecification<Number> or = new OrSpecification<Number>(OVER3, UNDER5);
		assertThat(or.isSatisfiedBy(0), is(true));
		assertThat(or.isSatisfiedBy(4), is(true));
		assertThat(or.isSatisfiedBy(7), is(true));
	}
	
	/**
	 * 5以下または9以上。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_under5_over9() throws Exception {
		OrSpecification<Number> or = new OrSpecification<Number>(UNDER5, OVER9);
		assertThat(or.isSatisfiedBy(0), is(true));
		assertThat(or.isSatisfiedBy(4), is(true));
		assertThat(or.isSatisfiedBy(7), is(false));
		assertThat(or.isSatisfiedBy(10), is(true));
	}
}
