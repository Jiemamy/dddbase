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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.Validate;

import org.jiemamy.dddbase.utils.CloneUtil;

/**
 * {@link EntityResolver}のオンメモリ実装抽象クラス。
 * 
 * @param <T> このリゾルバが保持する主たる{@link Entity}の型
 * @version $Id$
 * @author daisuke
 */
public abstract class OnMemoryEntityResolver<T extends Entity> implements EntityResolver, Cloneable {
	
	private static Map<UUID, Entity> collectAll(Entity entity, Map<UUID, Entity> collector) {
		collector.put(entity.getId(), entity);
		for (Entity e : entity.getSubEntities()) {
			collectAll(e, collector);
		}
		return collector;
	}
	
	private static Map<UUID, Entity> getAll(Iterable<? extends Entity> entities, Map<UUID, Entity> collector) {
		for (Entity entity : entities) {
			collectAll(entity, collector);
		}
		return collector;
	}
	

	private Map<UUID, T> storage = Maps.newLinkedHashMap();
	

	@Override
	public OnMemoryEntityResolver<T> clone() {
		try {
			@SuppressWarnings("unchecked")
			OnMemoryEntityResolver<T> clone = (OnMemoryEntityResolver<T>) super.clone();
			clone.storage = CloneUtil.cloneEntityHashMap(storage);
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error("clone not supported", e);
		}
	}
	
	public boolean contains(EntityRef<?> ref) {
		Validate.notNull(ref);
		return contains(ref.getReferentId());
	}
	
	public boolean contains(UUID id) {
		Validate.notNull(id);
		return getAll(storage.values(), new HashMap<UUID, Entity>()).containsKey(id);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Entity>E resolve(EntityRef<E> ref) {
		Validate.notNull(ref);
		return (E) resolve(ref.getReferentId());
	}
	
	public Entity resolve(UUID id) {
		Validate.notNull(id);
		Map<UUID, Entity> map = getAll(CloneUtil.cloneEntityArrayList(storage.values()), new HashMap<UUID, Entity>());
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
	 */
	protected Map<UUID, T> getStorage() {
		return storage;
	}
}
