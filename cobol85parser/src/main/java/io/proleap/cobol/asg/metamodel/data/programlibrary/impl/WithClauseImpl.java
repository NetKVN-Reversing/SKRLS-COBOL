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

import io.proleap.cobol.Cobol85Parser.LibraryEntryProcedureWithClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.programlibrary.WithClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class WithClauseImpl extends CobolDivisionElementImpl implements WithClause {

	protected LibraryEntryProcedureWithClauseContext ctx;

	protected List<ValueStmt> withValueStmts = new ArrayList<ValueStmt>();

	public WithClauseImpl(final ProgramUnit programUnit, final LibraryEntryProcedureWithClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public void addWithValueStmt(final ValueStmt withValueStmt) {
		withValueStmts.add(withValueStmt);
	}

	@Override
	public List<ValueStmt> getWithValueStmts() {
		return withValueStmts;
	}

}
