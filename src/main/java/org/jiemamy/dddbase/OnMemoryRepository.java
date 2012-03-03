/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.lang.Validate;

import org.jiemamy.dddbase.utils.CloneUtil;
import org.jiemamy.dddbase.utils.MutationMonitor;

/**
 * {@link Repository}のオンメモリ実装クラス。
 * 
 * @param <E> 管理するエンティティの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public class OnMemoryRepository<E extends Entity> extends OnMemoryEntityResolver<E> implements Repository<E> {
	
	private Map<RepositoryEventListener, DispatchStrategy> listeners = Maps.newHashMap();
	
	private DispatchStrategy defaultStrategy = new DispatchStrategy() {
		
		public boolean judgeIftargetOf(RepositoryEventListener listener, RepositoryEvent<?> event) {
			return true;
		}
	};
	
	
	public void addListener(RepositoryEventListener listener) {
		Validate.notNull(listener);
		addListener(listener, null);
	}
	
	public void addListener(RepositoryEventListener listener, DispatchStrategy strategy) {
		Validate.notNull(listener);
		listeners.put(listener, strategy);
	}
	
	@Override
	public OnMemoryRepository<E> clone() {
		OnMemoryRepository<E> clone = (OnMemoryRepository<E>) super.clone();
		return clone;
	}
	
	public E delete(EntityRef<? extends E> ref) {
		Validate.notNull(ref);
		Map<UUID, E> storage = getStorage();
		if (storage.containsKey(ref.getReferentId()) == false) {
			throw new EntityNotFoundException("id=" + ref.getReferentId());
		}
		E deleted = getStorage().remove(ref.getReferentId());
		fireEvent(new RepositoryEvent<E>(this, deleted, null));
		return deleted;
	}
	
	public void fireEvent(RepositoryEvent<?> event) {
		for (Map.Entry<RepositoryEventListener, DispatchStrategy> entry : listeners.entrySet()) {
			RepositoryEventListener listener = entry.getKey();
			DispatchStrategy strategy = entry.getValue();
			if (strategy == null) {
				strategy = defaultStrategy;
			}
			if (strategy.judgeIftargetOf(listener, event)) {
				listener.repositoryUpdated(event);
			}
		}
	}
	
	public Set<E> getEntitiesAsSet() {
		return MutationMonitor.monitor(CloneUtil.cloneEntityHashSet(getStorage().values()));
	}
	
	public void removeListener(RepositoryEventListener listener) {
		Validate.notNull(listener);
		listeners.remove(listener);
	}
	
	public void setDefaultStrategy(DispatchStrategy defaultStrategy) {
		Validate.notNull(defaultStrategy);
		this.defaultStrategy = defaultStrategy;
	}
	
	public E store(E entity) {
		Validate.notNull(entity);
		chechConsistency(entity);
		
		@SuppressWarnings("unchecked")
		E clone = (E) entity.clone();
		
		E old = getStorage().put(clone.getId(), clone);
		fireEvent(new RepositoryEvent<E>(this, old, entity));
		return old;
	}
	
	int managedAllEntityCount() {
		Set<UUID> collector = Sets.newHashSet();
		for (E entity : getStorage().values()) {
			collectAllId(entity, collector);
		}
		return collector.size();
	}
	
	int managedMainEntityCount() {
		return getStorage().size();
	}
	
	private void chechConsistency(E entityToAdd) {
		// create copy for check
		Map<UUID, E> copy = Maps.newHashMap(getStorage());
		copy.remove(entityToAdd.getId());
		Set<UUID> idsToAdd = collectAllId(entityToAdd, new HashSet<UUID>());
		
		Set<UUID> base = Sets.newHashSet();
		for (E entity : copy.values()) {
			collectAllId(entity, base);
		}
		
		base.retainAll(idsToAdd);
		if (base.size() > 0) {
			throw new IllegalArgumentException("collision " + base);
		}
	}
	
	private Set<UUID> collectAllId(Entity entity, Set<UUID> collector) {
		collector.add(entity.getId());
		if (entity instanceof HierarchicalEntity) {
			HierarchicalEntity he = (HierarchicalEntity) entity;
			for (Entity e : he.getSubEntities()) {
				collectAllId(e, collector);
			}
		}
		return collector;
	}
}
