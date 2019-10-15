/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.inspect.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.InspectAllLeadingContext;
import io.proleap.cobol.Cobol85Parser.InspectAllLeadingsContext;
import io.proleap.cobol.Cobol85Parser.InspectBeforeAfterContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.procedure.inspect.AllLeading;
import io.proleap.cobol.asg.metamodel.procedure.inspect.AllLeadingPhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class AllLeadingPhraseImpl extends InspectPhraseImpl implements AllLeadingPhrase {

	protected List<AllLeading> allLeadings = new ArrayList<AllLeading>();

	protected AllLeadingsType allLeadingsType;

	protected final InspectAllLeadingsContext ctx;

	private final Producer producer;
	
	public AllLeadingPhraseImpl(final ProgramUnit programUnit, final InspectAllLeadingsContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public AllLeading addAllLeading(final InspectAllLeadingContext ctx) {
		AllLeading result = (AllLeading) getASGElement(ctx);

		if (result == null) {
			result = new AllLeadingImpl(programUnit, ctx, producer);

			// pattern
			final ValueStmt patterndDataItemValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setPatternDataItemValueStmt(patterndDataItemValueStmt);

			// before / after
			for (final InspectBeforeAfterContext inspectBeforeAfterContext : ctx.inspectBeforeAfter()) {
				result.addBeforeAfterPhrase(inspectBeforeAfterContext);
			}

			allLeadings.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<AllLeading> getAllLeadings() {
		return allLeadings;
	}

	@Override
	public AllLeadingsType getAllLeadingsType() {
		return allLeadingsType;
	}

	@Override
	public void setAllLeadingsType(final AllLeadingsType allLeadingsType) {
		this.allLeadingsType = allLeadingsType;
	}

}
