/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
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

import java.util.UUID;

import org.jiemamy.dddbase.AbstractEntity;
import org.jiemamy.dddbase.DefaultEntityRef;
import org.jiemamy.dddbase.EntityRef;

/**
 * テスト用サブエンティティ実装。
 * 
 * @version $Id$
 * @author daisuke
 */
public class SampleSubEntity extends AbstractEntity<UUID> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 */
	public SampleSubEntity(UUID id) {
		super(id);
	}
	
	@Override
	public SampleSubEntity clone() {
		return (SampleSubEntity) super.clone();
	}
	
	@Override
	public EntityRef<? extends SampleSubEntity, UUID> toReference() {
		return new DefaultEntityRef<SampleSubEntity, UUID>(this);
	}
}
