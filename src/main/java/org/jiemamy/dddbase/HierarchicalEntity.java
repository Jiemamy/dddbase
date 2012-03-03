/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/11/05
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

/**
 * 階層構造を持つ {@link Entity} インターフェイス。
 * 
 * <p>子エンティティは、このエンティティと同じIDの型を持つ。</p>
 * 
 * @version $Id$
 * @since 1.4.0
 * @author daisuke
 */
public interface HierarchicalEntity extends Entity {
	
	/**
	 * 子エンティティの集合を取得する。
	 * 
	 * <p>このエンティティが保持するエンティティであり、{@link Repository}では直接管理されない
	 * エンティティ。（間接的には {@link Repository} が自動的に管理する）</p>
	 * 
	 * @return 子エンティティの集合
	 * @since 1.0.0
	 */
	Collection<? extends Entity> getSubEntities();
}
