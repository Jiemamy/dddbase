/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jiemamy.dddbase.spec;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * {@link Specification}の抽象実装クラス。
 * 
 * <p>{@link #and(Specification)}, {@link #or(Specification)}, {@link #not()}を
 * 実装済みである。</p>
 * 
 * @param <T> {@link AbstractSpecification}が判定対象とするオブジェクトの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public abstract class AbstractSpecification<T> implements Specification<T> {
	
	public Specification<T> and(Specification<T> specification) {
		return new AndSpecification<T>(this, specification);
	}
	
	public Specification<T> not() {
		return new NotSpecification<T>(this);
	}
	
	public Specification<T> or(final Specification<T> specification) {
		return new OrSpecification<T>(this, specification);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
