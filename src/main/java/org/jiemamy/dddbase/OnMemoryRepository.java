/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 * Created on 2010/12/13
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * {@link Repository}のオンメモリ実装クラス。
 * 
 * @param <T> 管理するエンティティの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public class OnMemoryRepository<T extends Entity> extends OnMemoryEntityResolver<T> implements Repository<T> {
	
	@Override
	public OnMemoryRepository<T> clone() {
		OnMemoryRepository<T> clone = (OnMemoryRepository<T>) super.clone();
		return clone;
	}
	
	public T delete(EntityRef<? extends T> ref) {
		Validate.notNull(ref);
		Map<UUID, T> storage = getStorage();
		if (storage.containsKey(ref.getReferentId()) == false) {
			throw new EntityNotFoundException("id=" + ref.getReferentId());
		}
		return getStorage().remove(ref.getReferentId());
	}
	
	@Deprecated
	public List<T> getEntitiesAsList() {
		return Lists.newArrayList(getStorage().values());
	}
	
	public Set<T> getEntitiesAsSet() {
		return Sets.newHashSet(getStorage().values());
	}
	
	public T store(T entity) {
		Validate.notNull(entity);
		chechConsistency(entity);
		
		@SuppressWarnings("unchecked")
		T clone = (T) entity.clone();
		
		return getStorage().put(clone.getId(), clone);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	int managedAllEntityCount() {
		Set<UUID> collector = Sets.newHashSet();
		for (T entity : getStorage().values()) {
			collectAllId(entity, collector);
		}
		return collector.size();
	}
	
	int managedMainEntityCount() {
		return getStorage().size();
	}
	
	private void chechConsistency(T entityToAdd) {
		// create copy for check
		Map<UUID, T> copy = Maps.newHashMap(getStorage());
		copy.remove(entityToAdd.getId());
		Set<UUID> idsToAdd = collectAllId(entityToAdd, new HashSet<UUID>());
		
		Set<UUID> base = Sets.newHashSet();
		for (T entity : copy.values()) {
			collectAllId(entity, base);
		}
		
		base.retainAll(idsToAdd);
		if (base.size() > 0) {
			throw new IllegalArgumentException("collision " + base);
		}
	}
	
	private Set<UUID> collectAllId(Entity entity, Set<UUID> collector) {
		collector.add(entity.getId());
		for (Entity e : entity.getSubEntities()) {
			collectAllId(e, collector);
		}
		return collector;
	}
}
