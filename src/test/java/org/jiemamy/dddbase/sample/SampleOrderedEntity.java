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
package org.jiemamy.dddbase.sample;

import java.util.UUID;

import org.jiemamy.dddbase.AbstractOrderedEntity;
import org.jiemamy.dddbase.EntityRef;
import org.jiemamy.dddbase.OrderedEntity;

/**
 * {@link OrderedEntity}のサンプル実装クラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class SampleOrderedEntity extends AbstractOrderedEntity {
	
	private String string;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 */
	public SampleOrderedEntity(UUID id) {
		super(id);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 * @param string 文字列
	 */
	public SampleOrderedEntity(UUID id, String string) {
		this(id);
		this.string = string;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 * @param string 文字列
	 * @param index エンティティインデックス
	 */
	public SampleOrderedEntity(UUID id, String string, int index) {
		this(id, string);
		setIndex(index);
	}
	
	@Override
	public SampleOrderedEntity clone() {
		SampleOrderedEntity clone = (SampleOrderedEntity) super.clone();
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
	
	@Override
	public EntityRef<? extends SampleOrderedEntity> toReference() {
		return new EntityRef<SampleOrderedEntity>(this);
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + string + "]";
	}
}
