/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.evaluate.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.EvaluateAlsoConditionContext;
import io.proleap.cobol.Cobol85Parser.EvaluateWhenContext;
import io.proleap.cobol.Cobol85Parser.EvaluateWhenPhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.ScopeImpl;
import io.proleap.cobol.asg.metamodel.procedure.evaluate.When;
import io.proleap.cobol.asg.metamodel.procedure.evaluate.WhenPhrase;

public class WhenPhraseImpl extends ScopeImpl implements WhenPhrase {

	protected final EvaluateWhenPhraseContext ctx;

	protected List<When> whens = new ArrayList<When>();
	
	private final Producer producer;

	public WhenPhraseImpl(final ProgramUnit programUnit, final EvaluateWhenPhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public When addWhen(final EvaluateWhenContext ctx) {
		When result = (When) getASGElement(ctx);

		if (result == null) {
			result = new WhenImpl(programUnit, ctx, producer);

			// condition
			if (ctx.evaluateCondition() != null) {
				result.addCondition(ctx.evaluateCondition());
			}

			// also conditions
			for (final EvaluateAlsoConditionContext evaluateAlsoConditionContext : ctx.evaluateAlsoCondition()) {
				result.addAlsoCondition(evaluateAlsoConditionContext);
			}

			whens.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<When> getWhens() {
		return whens;
	}
}
