/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.programlibrary.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.LibraryEntryProcedureUsingClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.programlibrary.UsingClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class UsingClauseImpl extends CobolDivisionElementImpl implements UsingClause {

	protected LibraryEntryProcedureUsingClauseContext ctx;

	protected List<ValueStmt> usingValueStmts = new ArrayList<ValueStmt>();

	public UsingClauseImpl(final ProgramUnit programUnit, final LibraryEntryProcedureUsingClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public void addUsingValueStmt(final ValueStmt usingValueStmt) {
		usingValueStmts.add(usingValueStmt);
	}

	@Override
	public List<ValueStmt> getUsingValueStmts() {
		return usingValueStmts;
	}

}
