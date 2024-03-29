/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/01/16
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

import java.util.Collection;
import java.util.UUID;

/**
 * 複数の{@link OnMemoryEntityResolver}に対するクエリを1つに束ねるクラス。
 * 
 * @param <E> エンティティの型
 * @version $Id$
 * @author daisuke
 * @since 1.1.3
 */
public class OnMemoryCompositeEntityResolver<E extends Entity> extends CompositeEntityResolver {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param resolvers リゾルバ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.4.0
	 */
	public OnMemoryCompositeEntityResolver(Collection<OnMemoryEntityResolver<? extends E>> resolvers) {
		super(resolvers);
	}
	
	@Override
	public boolean contains(EntityRef<?> ref) {
		try {
			return super.contains(ref);
		} catch (RepositoryException e) {
			throw new AssertionError(e);
		}
	}
	
	@Override
	public boolean contains(UUID id) {
		try {
			return super.contains(id);
		} catch (RepositoryException e) {
			throw new AssertionError(e);
		}
	}
	
	@Override
	public <E2 extends Entity>E2 resolve(EntityRef<E2> ref) {
		try {
			return super.resolve(ref);
		} catch (RepositoryException e) {
			throw new AssertionError(e);
		}
	}
	
	@Override
	public Entity resolve(UUID id) {
		try {
			return super.resolve(id);
		} catch (RepositoryException e) {
			throw new AssertionError(e);
		}
	}
}
