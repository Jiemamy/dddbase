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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.Validate;

import org.jiemamy.dddbase.utils.CloneUtil;

/**
 * {@link EntityResolver}のオンメモリ実装抽象クラス。
 * 
 * @param <E> このリゾルバが保持する主たる{@link Entity}の型
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.1.3
 */
public abstract class OnMemoryEntityResolver<E extends Entity<ID>, ID extends Serializable> implements
		EntityResolver<ID>, Cloneable {
	
	private static <ID extends Serializable>Map<ID, Entity<ID>> collectAll(Entity<ID> entity,
			Map<ID, Entity<ID>> collector) {
		collector.put(entity.getId(), entity);
		if (entity instanceof HierarchicalEntity) {
			for (Entity<ID> e : ((HierarchicalEntity<ID>) entity).getSubEntities()) {
				collectAll(e, collector);
			}
		}
		return collector;
	}
	
	private static <ID extends Serializable>Map<ID, Entity<ID>> getAll(Iterable<? extends Entity<ID>> entities,
			Map<ID, Entity<ID>> collector) {
		for (Entity<ID> entity : entities) {
			collectAll(entity, collector);
		}
		return collector;
	}
	
	
	private Map<ID, E> storage = new ConcurrentHashMap<ID, E>();
	
	
	@Override
	public OnMemoryEntityResolver<E, ID> clone() {
		try {
			@SuppressWarnings("unchecked")
			OnMemoryEntityResolver<E, ID> clone = (OnMemoryEntityResolver<E, ID>) super.clone();
			clone.storage = CloneUtil.cloneEntityConcurrentHashMap(storage);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error("clone not supported", e);
		}
	}
	
	public boolean contains(EntityRef<?, ID> ref) {
		Validate.notNull(ref);
		return contains(ref.getReferentId());
	}
	
	public boolean contains(ID id) {
		Validate.notNull(id);
		return getAll(storage.values(), new HashMap<ID, Entity<ID>>()).containsKey(id);
	}
	
	@SuppressWarnings("unchecked")
	public <E2 extends Entity<ID>>E2 resolve(EntityRef<E2, ID> ref) {
		Validate.notNull(ref);
		return (E2) resolve(ref.getReferentId());
	}
	
	public Entity<ID> resolve(ID id) {
		Validate.notNull(id);
		Map<ID, Entity<ID>> map =
				getAll(CloneUtil.cloneEntityArrayList(storage.values()), new HashMap<ID, Entity<ID>>());
		if (map.containsKey(id) == false) {
			throw new EntityNotFoundException("id=" + id);
		}
		return map.get(id);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassUtils.getShortClassName(getClass()));
		sb.append("@ih=").append(Integer.toHexString(System.identityHashCode(this)));
		sb.append(storage.values().toString());
		return sb.toString();
	}
	
	/**
	 * メモリ上のストレージとして利用する{@link Map}を取得する。
	 * 
	 * <p>ストレージに対して値を格納・削除するのは具象実装クラスの責任である。その際、
	 * この {@link Map} のキーは、対応する値の {@link Entity} が持つ ENTITY ID でなければならない。</p>
	 * 
	 * @return ストレージ
	 * @since 1.1.3
	 */
	protected Map<ID, E> getStorage() {
		return storage;
	}
}
