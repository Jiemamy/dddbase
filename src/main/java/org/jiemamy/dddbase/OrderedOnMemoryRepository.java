/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/01/12
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
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.commons.lang.ClassUtils;

import org.jiemamy.dddbase.utils.CloneUtil;

/**
 * {@link OrderedRepository}のオンメモリ実装クラス。
 * 
 * @param <E> 管理するエンティティの型
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.2.0
 */
public class OrderedOnMemoryRepository<E extends OrderedEntity<ID>, ID extends Serializable> extends
		OnMemoryRepository<E, ID> implements OrderedRepository<E, ID> {
	
	private List<E> list = Lists.newArrayList();
	
	
	@Override
	public synchronized OrderedOnMemoryRepository<E, ID> clone() {
		OrderedOnMemoryRepository<E, ID> clone = (OrderedOnMemoryRepository<E, ID>) super.clone();
		synchronized (clone) {
			clone.list = CloneUtil.cloneEntityArrayList(this.list);
		}
		return clone;
	}
	
	@Override
	public synchronized E delete(EntityRef<? extends E, ID> ref) {
		E deleted = super.delete(ref);
		list.remove(deleted);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIndex(i);
		}
		return deleted;
	}
	
	public synchronized List<E> getEntitiesAsList() {
		return CloneUtil.cloneEntityArrayList(list);
	}
	
	@Override
	public synchronized E store(E entity) {
		E old;
		if (list.contains(entity)) {
			int index = list.indexOf(entity);
			old = super.store(entity);
			entity.setIndex(index);
			@SuppressWarnings("unchecked")
			E clone = (E) entity.clone();
			list.set(index, clone);
			for (int i = index + 1; i < list.size(); i++) {
				list.get(i).setIndex(i);
			}
		} else {
			E target;
			if (entity.getIndex() < 0 || entity.getIndex() >= list.size()) {
				entity.setIndex(list.size());
				@SuppressWarnings("unchecked")
				E clone = (E) entity.clone();
				target = clone;
			} else {
				@SuppressWarnings("unchecked")
				E clone = (E) entity.clone();
				target = clone;
				for (int i = clone.getIndex(); i < list.size(); i++) {
					super.store(target);
					target = list.set(i, target);
					target.setIndex(i + 1);
				}
			}
			old = super.store(target);
			list.add(target);
		}
		return old;
	}
	
	public synchronized void swap(int index1, int index2) {
		Collections.swap(list, index1, index2);
		list.get(index1).setIndex(index1);
		list.get(index2).setIndex(index2);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassUtils.getShortClassName(getClass()));
		sb.append("@ih=").append(Integer.toHexString(System.identityHashCode(this)));
		sb.append(list.toString());
		return sb.toString();
	}
	
}
