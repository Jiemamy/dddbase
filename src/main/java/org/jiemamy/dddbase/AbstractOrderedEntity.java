/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2011/01/18
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

/**
 * {@link OrderedEntity}の骨格実装クラス。
 * 
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.2.0
 */
public abstract class AbstractOrderedEntity<ID extends Serializable> extends AbstractEntity<ID> implements
		OrderedEntity<ID> {
	
	private int index = -1;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.2.0
	 */
	public AbstractOrderedEntity(ID id) {
		super(id);
	}
	
	@Override
	public AbstractOrderedEntity<ID> clone() {
		return (AbstractOrderedEntity<ID>) super.clone();
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public EntityRef<? extends AbstractOrderedEntity<ID>, ID> toReference() {
		return new DefaultEntityRef<AbstractOrderedEntity<ID>, ID>(this);
	}
	
	@Override
	public String toString() {
		return super.toString() + "/idx=" + index;
	}
}
