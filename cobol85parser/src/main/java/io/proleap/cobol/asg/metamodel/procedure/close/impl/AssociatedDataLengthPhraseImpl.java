/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.close.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ClosePortFileIOUsingAssociatedDataLengthContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.close.AssociatedDataLengthPhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class AssociatedDataLengthPhraseImpl extends CobolDivisionElementImpl implements AssociatedDataLengthPhrase {

	protected ClosePortFileIOUsingAssociatedDataLengthContext ctx;

	protected ValueStmt dataLengthValueStmt;

	public AssociatedDataLengthPhraseImpl(final ProgramUnit programUnit,
			final ClosePortFileIOUsingAssociatedDataLengthContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public ValueStmt getDataLengthValueStmt() {
		return dataLengthValueStmt;
	}

	@Override
	public void setDataLengthValueStmt(final ValueStmt dataLengthValueStmt) {
		this.dataLengthValueStmt = dataLengthValueStmt;
	}

}
