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

import org.jiemamy.dddbase.UUIDEntityFactory;

/**
 * サンプルとしての{@link SampleMainEntity}ビルダ実装。
 * 
 * @version $Id$
 * @author daisuke
 */
public class SampleMainEntityFactory extends UUIDEntityFactory<SampleMainEntity> {
	
	private String string;
	
	private Collection<String> values = Lists.newArrayList();
	
	private Collection<SampleSubEntity> children = Lists.newArrayList();
	
	
	/**
	 * インスタンスを生成する。
	 */
	public SampleMainEntityFactory() {
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param string 初期文字列
	 */
	public SampleMainEntityFactory(String string) {
		this.string = string;
	}
	
	/**
	 * 子エンティティを追加する。
	 * 
	 * @param child 子エンティティ
	 * @return this
	 */
	public SampleMainEntityFactory addChild(SampleSubEntity child) {
		children.add(child);
		return this;
	}
	
	/**
	 * {@link SampleMainEntity}に設定する値を追加する。
	 * 
	 * @param value 値
	 * @return this
	 */
	public SampleMainEntityFactory addValue(String value) {
		values.add(value);
		return this;
	}
	
	public SampleMainEntity build(UUID id) {
		SampleMainEntity sampleMainEntity = new SampleMainEntity(id);
		sampleMainEntity.setString(string);
		sampleMainEntity.getValues().addAll(values);
		for (SampleSubEntity child : children) {
			sampleMainEntity.addChild(child);
		}
		return sampleMainEntity;
	}
	
	/**
	 * 文字列を設定する。
	 * 
	 * @param string 文字列
	 * @return this
	 */
	public SampleMainEntityFactory setString(String string) {
		this.string = string;
		return this;
	}
	
}
