/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2012/03/03
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
 * @param <E> {@link Entity}の型
 * @version $Id$
 * @author daisuke
 */
public abstract class AbstractEntityFactory<E extends Entity> implements EntityFactory<E> {
	
	public E build() {
		return build(UUID.randomUUID());
	}
}
