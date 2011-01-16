/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
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

import java.util.UUID;

import org.apache.commons.lang.Validate;

/**
 * 複数の{@link EntityResolver}に対するクエリを1つに束ねるクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class CompositeEntityResolver implements EntityResolver {
	
	private final EntityResolver[] resolvers;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param resolvers リゾルバ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public CompositeEntityResolver(EntityResolver... resolvers) {
		Validate.noNullElements(resolvers);
		this.resolvers = resolvers;
	}
	
	public boolean contains(EntityRef<?> ref) throws RepositoryException {
		for (EntityResolver resolver : resolvers) {
			if (resolver.contains(ref)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(UUID id) throws RepositoryException {
		for (EntityResolver resolver : resolvers) {
			if (resolver.contains(id)) {
				return true;
			}
		}
		return false;
	}
	
	public <E extends Entity>E resolve(EntityRef<E> ref) throws RepositoryException {
		for (EntityResolver resolver : resolvers) {
			try {
				return resolver.resolve(ref);
			} catch (EntityNotFoundException e) {
				// ignore
			}
		}
		throw new EntityNotFoundException("id=" + ref.getReferentId());
	}
	
	public Entity resolve(UUID id) throws RepositoryException {
		for (EntityResolver resolver : resolvers) {
			try {
				return resolver.resolve(id);
			} catch (EntityNotFoundException e) {
				// ignore
			}
		}
		throw new EntityNotFoundException("id=" + id);
	}
	
}
