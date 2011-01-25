/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 * Created on 2010/12/10
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

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.Validate;

/**
 * {@link Entity}の骨格実装クラス。
 * 
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public abstract class AbstractEntity implements Entity {
	
	final UUID id;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param id ENTITY ID
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	public AbstractEntity(UUID id) {
		Validate.notNull(id);
		this.id = id;
	}
	
	@Override
	public AbstractEntity clone() {
		try {
			return (AbstractEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("clone not supported");
		}
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Entity == false) {
			return false;
		}
		return id.equals(((Entity) obj).getId());
	}
	
	public final UUID getId() {
		return id;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>デフォルト実装として、空のリストを返す。必要に応じてオーバーライドすべきである。</p>
	 * 
	 * @return 空のリスト
	 * @since 1.0.0
	 */
	public Collection<? extends Entity> getSubEntities() {
		return Collections.emptyList();
	}
	
	@Override
	public final int hashCode() {
		return id.hashCode();
	}
	
	public EntityRef<? extends AbstractEntity> toReference() {
		return new DefaultEntityRef<AbstractEntity>(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassUtils.getShortClassName(getClass()));
		sb.append("@ih=").append(Integer.toHexString(System.identityHashCode(this)));
		sb.append("/id=").append(id.toString().substring(0, 8));
		return sb.toString();
	}
}
