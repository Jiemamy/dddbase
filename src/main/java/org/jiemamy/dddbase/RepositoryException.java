/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 * Created on 2010/12/16
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
 * リポジトリに問題がある場合にスローされる例外。
 * 
 * @version $Id$
 * @author daisuke
	 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class RepositoryException extends Exception {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @since 1.0.0
	 */
	public RepositoryException() {
		super();
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param message 例外メッセージ
	 * @since 1.0.2
	 */
	public RepositoryException(String message) {
		super(message);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param message 例外メッセージ
	 * @param cause 起因例外
	 * @since 1.0.2
	 */
	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param cause 起因例外
	 * @since 1.0.2
	 */
	public RepositoryException(Throwable cause) {
		super(cause);
	}
}
