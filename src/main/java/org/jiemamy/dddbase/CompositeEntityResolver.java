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

import java.io.Serializable;
import java.util.Collection;

import com.google.common.collect.Lists;

import org.apache.commons.lang.Validate;

/**
 * 複数の{@link EntityResolver}に対するクエリを1つに束ねるクラス。
 * 
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.1.3
 */
public class CompositeEntityResolver<ID extends Serializable> implements EntityResolver<ID> {
	
	private final Collection<EntityResolver<ID>> resolvers;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param resolvers リゾルバ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.4.0
	 */
	public CompositeEntityResolver(Collection<? extends EntityResolver<ID>> resolvers) {
		Validate.noNullElements(resolvers);
		this.resolvers = Lists.newArrayList(resolvers);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param resolvers リゾルバ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.1.3
	 */
	@Deprecated
	public CompositeEntityResolver(EntityResolver<ID>[] resolvers) {
		Validate.noNullElements(resolvers);
		this.resolvers = Lists.newArrayList(resolvers);
	}
	
	public boolean contains(EntityRef<?, ID> ref) throws RepositoryException {
		for (EntityResolver<ID> resolver : resolvers) {
			if (resolver.contains(ref)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(ID id) throws RepositoryException {
		for (EntityResolver<ID> resolver : resolvers) {
			if (resolver.contains(id)) {
				return true;
			}
		}
		return false;
	}
	
	public <E extends Entity<ID>>E resolve(EntityRef<E, ID> ref) throws RepositoryException {
		for (EntityResolver<ID> resolver : resolvers) {
			try {
				return resolver.resolve(ref);
			} catch (EntityNotFoundException e) {
				// ignore
			}
		}
		throw new EntityNotFoundException("id=" + ref.getReferentId());
	}
	
	public Entity<ID> resolve(ID id) throws RepositoryException {
		for (EntityResolver<ID> resolver : resolvers) {
			try {
				return resolver.resolve(id);
			} catch (EntityNotFoundException e) {
				// ignore
			}
		}
		throw new EntityNotFoundException("id=" + id);
	}
	
}
