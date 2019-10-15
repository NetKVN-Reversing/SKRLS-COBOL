/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.communication.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SymbolicDestinationClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.data.communication.SymbolicDestinationClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class SymbolicDestinationClauseImpl extends CobolDivisionElementImpl implements SymbolicDestinationClause {

	protected SymbolicDestinationClauseContext ctx;

	protected Call dataDescCall;

	public SymbolicDestinationClauseImpl(final ProgramUnit programUnit, final SymbolicDestinationClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getDataDescCall() {
		return dataDescCall;
	}

	@Override
	public void setDataDescCall(final Call dataDescCall) {
		this.dataDescCall = dataDescCall;
	}

}
