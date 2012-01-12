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

import org.jiemamy.dddbase.ValueObjectBuilder;

/**
 * {@link SampleValueObject}用のビルダー実装。
 * 
 * @version $Id$
 * @author daisuke
 */
public class SampleValueObjectBuilder extends ValueObjectBuilder<SampleValueObject, SampleValueObjectBuilder> {
	
	private String string;
	
	private int number;
	
	
	/**
	 * 数値を設定する。
	 * 
	 * @param number 数値
	 * @return this
	 */
	public SampleValueObjectBuilder setNumber(final int number) {
		addConfigurator(new BuilderConfigurator<SampleValueObjectBuilder>() {
			
			public void configure(SampleValueObjectBuilder builder) {
				builder.number = number;
			}
		});
		return getThis();
	}
	
	/**
	 * 文字列を設定する。
	 * 
	 * @param string 文字列
	 * @return this
	 */
	public SampleValueObjectBuilder setString(final String string) {
		addConfigurator(new BuilderConfigurator<SampleValueObjectBuilder>() {
			
			public void configure(SampleValueObjectBuilder builder) {
				builder.string = string;
			}
		});
		return getThis();
	}
	
	@Override
	protected void apply(SampleValueObject vo, SampleValueObjectBuilder builder) {
		builder.setNumber(vo.getNumber());
		builder.setString(vo.getString());
	}
	
	@Override
	protected SampleValueObject createValueObject() {
		return new SampleValueObject(number, string);
	}
	
	@Override
	protected SampleValueObjectBuilder getThis() {
		return this;
	}
	
	@Override
	protected SampleValueObjectBuilder newInstance() {
		return new SampleValueObjectBuilder();
	}
	
}
