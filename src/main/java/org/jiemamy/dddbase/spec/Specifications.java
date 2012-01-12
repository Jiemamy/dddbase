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
	
	/**
	 * 2つの仕様の論理積(OR)をとる仕様を生成する。
	 * 
	 * @param <T> 仕様の検証対象オブジェクトの型
	 * @param s1 仕様1
	 * @param s2 仕様2
	 * @return 論理積仕様
	 * @since 1.1.2
	 */
	public static <T>Specification<? super T> and(Specification<T> s1, Specification<T> s2) {
		return new AndSpecification<T>(s1, s2);
	}
	
	/**
	 * 否定(NOT)をとる仕様を生成する。
	 * 
	 * @param <T> 仕様の検証対象オブジェクトの型
	 * @param s 仕様
	 * @return 否定仕様
	 * @since 1.1.2
	 */
	public static <T>Specification<? super T> not(Specification<T> s) {
		return new NotSpecification<T>(s);
	}
	
	/**
	 * 2つの仕様の論理和(AND)をとる仕様を生成する。
	 * 
	 * @param <T> 仕様の検証対象オブジェクトの型
	 * @param s1 仕様1
	 * @param s2 仕様2
	 * @return 論理和仕様
	 * @since 1.1.2
	 */
	public static <T>Specification<? super T> or(Specification<T> s1, Specification<T> s2) {
		return new OrSpecification<T>(s1, s2);
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
