/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.programlibrary.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.LibraryEntryProcedureClauseFormat1Context;
import io.proleap.cobol.Cobol85Parser.LibraryEntryProcedureForClauseContext;
import io.proleap.cobol.asg.metamodel.Literal;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.data.programlibrary.ExportEntryProcedure;
import io.proleap.cobol.asg.metamodel.data.programlibrary.ForClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class ExportEntryProcedureImpl extends CobolDivisionElementImpl implements ExportEntryProcedure {

	protected LibraryEntryProcedureClauseFormat1Context ctx;

	protected ForClause forClause;

	protected Call programCall;
	
	private final Producer producer;

	public ExportEntryProcedureImpl(final ProgramUnit programUnit,
			final LibraryEntryProcedureClauseFormat1Context ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public ForClause addForClause(final LibraryEntryProcedureForClauseContext ctx) {
		ForClause result = (ForClause) getASGElement(ctx);

		if (result == null) {
			result = new ForClauseImpl(programUnit, ctx, producer);

			if (ctx.literal() != null) {
				final Literal forLiteral = createLiteral(ctx.literal());
				result.setForLiteral(forLiteral);
			}

			forClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ForClause getForClause() {
		return forClause;
	}

	@Override
	public Call getProgramCall() {
		return programCall;
	}

	@Override
	public void setProgramCall(final Call programCall) {
		this.programCall = programCall;
	}

}
