/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
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

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.jiemamy.dddbase.utils.CloneUtil;

/**
 * {@link OrderedRepository}のオンメモリ実装クラス。
 * 
 * @param <T> 管理するエンティティの型
 * @version $Id$
 * @author daisuke
 * @since 1.2.0
 */
public class OrderedOnMemoryRepository<T extends OrderedEntity> extends OnMemoryRepository<T> implements
		OrderedRepository<T> {
	
	private List<T> list = Lists.newArrayList();
	

	@Override
	public synchronized OrderedOnMemoryRepository<T> clone() {
		OrderedOnMemoryRepository<T> clone = (OrderedOnMemoryRepository<T>) super.clone();
		synchronized (clone) {
			clone.list = CloneUtil.cloneEntityArrayList(this.list);
		}
		return clone;
	}
	
	@Override
	public synchronized List<T> getEntitiesAsList() {
		return CloneUtil.cloneEntityArrayList(list);
	}
	
	@Override
	public synchronized T store(T entity) {
		T old;
		if (list.contains(entity)) {
			int index = list.indexOf(entity);
			old = super.store(entity);
			entity.setIndex(index);
			@SuppressWarnings("unchecked")
			T clone = (T) entity.clone();
			list.add(index, clone);
			for (int i = index + 1; i < list.size(); i++) {
				list.get(i).setIndex(i);
			}
		} else {
			old = super.store(entity);
			if (entity.getIndex() < 0 || entity.getIndex() >= list.size()) {
				entity.setIndex(list.size());
				@SuppressWarnings("unchecked")
				T clone = (T) entity.clone();
				list.add(clone);
			} else {
				@SuppressWarnings("unchecked")
				T clone = (T) entity.clone();
				T target = clone;
				for (int i = clone.getIndex(); i < list.size(); i++) {
					target = list.set(i, target);
					target.setIndex(i + 1);
				}
				list.add(target);
			}
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	@Override
	protected synchronized T deleteMain(EntityRef<? extends T> ref) {
		T deleted = super.deleteMain(ref);
		list.remove(deleted);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIndex(i);
		}
		return deleted;
	}
	
}
