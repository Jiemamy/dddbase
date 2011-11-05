/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2010/12/17
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
package org.jiemamy.dddbase.sample;

import java.util.Collection;
import java.util.UUID;

import com.google.common.collect.Lists;

import org.jiemamy.dddbase.AbstractEntity;
import org.jiemamy.dddbase.DefaultEntityRef;
import org.jiemamy.dddbase.EntityRef;
import org.jiemamy.dddbase.HierarchicalEntity;
import org.jiemamy.dddbase.OnMemoryRepository;
import org.jiemamy.dddbase.utils.CloneUtil;

/**
 * テスト用メインエンティティ実装。
 * 
 * @version $Id$
 * @author daisuke
 */
public class SampleMainEntity extends AbstractEntity<UUID> implements HierarchicalEntity<UUID> {
	
	private String string;
	
	private Collection<String> values = Lists.newArrayList();
	
	private OnMemoryRepository<SampleSubEntity, UUID> children = new OnMemoryRepository<SampleSubEntity, UUID>();
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 */
	public SampleMainEntity(UUID id) {
		super(id);
	}
	
	/**
	 * 子エンティティを追加する。
	 * 
	 * @param child 子エンティティ
	 */
	public void addChild(SampleSubEntity child) {
		children.store(child);
	}
	
	@Override
	public SampleMainEntity clone() {
		SampleMainEntity clone = (SampleMainEntity) super.clone();
		clone.values = CloneUtil.cloneValueArrayList(values);
		clone.children = children.clone();
		return clone;
	}
	
	/**
	 * 文字列を取得する。
	 * 
	 * @return 文字列
	 */
	public String getString() {
		return string;
	}
	
	public Collection<? extends SampleSubEntity> getSubEntities() {
		return children.getEntitiesAsSet();
	}
	
	/**
	 * 値の集合を取得する。
	 * 
	 * @return 値の集合
	 */
	public Collection<String> getValues() {
		return values;
	}
	
	/**
	 * 文字列を設定する。
	 * 
	 * @param string 文字列
	 */
	public void setString(String string) {
		this.string = string;
	}
	
	@Override
	public EntityRef<? extends SampleMainEntity, UUID> toReference() {
		return new DefaultEntityRef<SampleMainEntity, UUID>(this);
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + string + "]";
	}
}
