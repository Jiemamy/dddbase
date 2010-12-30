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

/**
 * 標準的な仕様群。
 * 
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public final class Specifications {
	
	/**
	 * 常に{@code false}を返す、{@link Specification}のインスタンスを返す。
	 * 
	 * @param <T> 生成する仕様の型
	 * @return 生成したインスタンス
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <T>Specification<T> alwaysFalse() {
		return (Specification<T>) AlwaysFalseSpecification.INSTANCE;
	}
	
	/**
	 * 常に{@code true}を返す、{@link Specification}のインスタンスを返す。
	 * 
	 * @param <T> 生成する仕様の型
	 * @return 生成したインスタンス
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <T>Specification<T> alwaysTrue() {
		return (Specification<T>) AlwaysTrueSpecification.INSTANCE;
	}
	
	private Specifications() {
	}
	

	static class AlwaysFalseSpecification extends AbstractSpecification<Object> {
		
		static final AlwaysFalseSpecification INSTANCE = new AlwaysFalseSpecification();
		

		public boolean isSatisfiedBy(Object t) {
			return false;
		}
		
	}
	
	static class AlwaysTrueSpecification extends AbstractSpecification<Object> {
		
		static final AlwaysTrueSpecification INSTANCE = new AlwaysTrueSpecification();
		

		public boolean isSatisfiedBy(Object t) {
			return true;
		}
		
	}
}
