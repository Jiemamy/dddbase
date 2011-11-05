/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2010/05/11
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

import java.util.UUID;

/**
 * {@link EntityFactory}の抽象実装クラス。
 * 
 * @param <E> 生成する {@link Entity}の型
 * @version $Id$
 * @author daisuke
 * @since 1.4.0
 */
public abstract class UUIDEntityFactory<E extends Entity<UUID>> implements EntityFactory<E, UUID> {
	
	public E build() {
		return build(UUID.randomUUID());
	}
	
}
