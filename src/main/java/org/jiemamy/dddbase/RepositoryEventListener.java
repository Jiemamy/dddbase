/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2009/02/09
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
 * データがストアされたイベントの通知を受け取るリスナ。
 * 
 * @since 1.3.0
 * @author shin1ogawa
 */
public interface RepositoryEventListener {
	
	/**
	 * データがストアされたことを通知するcallbackメソッド。
	 * 
	 * <p>監視対象に変更があった場合に{@link Repository}によってcallbackされる。</p>
	 * 
	 * @param event イベント
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	void repositoryUpdated(RepositoryEvent<?> event);
	
}
