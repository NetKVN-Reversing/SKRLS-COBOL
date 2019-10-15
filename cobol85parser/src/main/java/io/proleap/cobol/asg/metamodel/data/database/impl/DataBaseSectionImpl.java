/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.database.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataBaseSectionContext;
import io.proleap.cobol.Cobol85Parser.DataBaseSectionEntryContext;
import io.proleap.cobol.asg.metamodel.IntegerLiteral;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.database.DataBaseSection;
import io.proleap.cobol.asg.metamodel.data.database.DataBaseSectionEntry;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.LiteralValueStmt;

public class DataBaseSectionImpl extends CobolDivisionElementImpl implements DataBaseSection {

	protected final DataBaseSectionContext ctx;

	protected List<DataBaseSectionEntry> dataBaseSectionEntries = new ArrayList<DataBaseSectionEntry>();
	
	private final Producer producer;

	public DataBaseSectionImpl(final ProgramUnit programUnit, final DataBaseSectionContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public DataBaseSectionEntry addDataBaseSectionEntry(final DataBaseSectionEntryContext ctx) {
		DataBaseSectionEntry result = (DataBaseSectionEntry) getASGElement(ctx);

		if (result == null) {
			result = new DataBaseSectionEntryImpl(programUnit, ctx, producer);

			if (ctx.integerLiteral() != null) {
				final IntegerLiteral integerLiteral = createIntegerLiteral(ctx.integerLiteral());
				result.setIntegerLiteral(integerLiteral);
			}

			final LiteralValueStmt literalValueStmt1 = createLiteralValueStmt(ctx.literal(0));
			result.setValueStmt1(literalValueStmt1);

			final LiteralValueStmt literalValueStmt2 = createLiteralValueStmt(ctx.literal(1));
			result.setValueStmt2(literalValueStmt2);

			dataBaseSectionEntries.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<DataBaseSectionEntry> getDataBaseSectionEntries() {
		return dataBaseSectionEntries;
	}

}
