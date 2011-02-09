/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2010/05/01
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

/**
 * DDD における VALUE OBJECT を表すインターフェイス。
 * 
 * <p>このインターフェイスの実装クラスは、不変(immutable)オブジェクトであるべきである。</p>
 * 
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public interface ValueObject {
	
	/**
	 * プロパティ全ての等価性(equals)を以て、等価性を判断する。
	 * 
	 * @param obj 比較対象オブジェクト
	 * @return 等価の場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0.0
	 */
	boolean equals(Object obj);
}
