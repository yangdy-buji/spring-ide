/*******************************************************************************
 * Copyright (c) 2015 Pivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.dash.livexp;

import org.springsource.ide.eclipse.commons.livexp.core.LiveExpression;
import org.springsource.ide.eclipse.commons.livexp.core.ValueListener;

import com.google.common.collect.ImmutableSet;

/**
 * This is like LiveSet, but it can only be 'observed' not mutated.
 */
public abstract class ObservableSet<T> extends LiveExpression<ImmutableSet<T>> {

	//TODO: this class should replace 'LiveSet' and 'LiveSet' should be renamed to 'LiveSetVariable'

	public ObservableSet() {
		this(ImmutableSet.<T>of());
	}

	public ObservableSet(ImmutableSet<T> initialValue) {
		super(initialValue);
	}

	public static <T> ObservableSet<T> constant(ImmutableSet<T> value) {
		return new ObservableSet<T>(value) {
			@Override
			protected ImmutableSet<T> compute() {
				return value;
			}

			@Override
			public void addListener(ValueListener<ImmutableSet<T>> l) {
				l.gotValue(this, value);
				//Beyond the initial notification ... we ignore listeners... we will never notify again since
				//constants can't change
			}
			@Override
			public void removeListener(ValueListener<ImmutableSet<T>> l) {
				//Ignore all listeners we will never notify anyone since
				//constants can't change
			}

			@Override
			public void refresh() {
				//Ignore all refreshes... no need to refresh anything since
				//constants can't change
			}
		};
	}

	public ImmutableSet<T> getValues() {
		return getValue();
	}


}
