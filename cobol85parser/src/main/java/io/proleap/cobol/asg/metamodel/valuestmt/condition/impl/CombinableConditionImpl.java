/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.condition.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.CombinableConditionContext;
import io.proleap.cobol.Cobol85Parser.SimpleConditionContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.valuestmt.condition.CombinableCondition;
import io.proleap.cobol.asg.metamodel.valuestmt.condition.SimpleCondition;
import io.proleap.cobol.asg.metamodel.valuestmt.impl.ValueStmtImpl;

public class CombinableConditionImpl extends ValueStmtImpl implements CombinableCondition {

	protected CombinableConditionContext ctx;

	protected boolean not;

	protected SimpleCondition simpleCondition;
	
	private final Producer producer;

	public CombinableConditionImpl(final ProgramUnit programUnit, final CombinableConditionContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
	}

	@Override
	public SimpleCondition addSimpleCondition(final SimpleConditionContext ctx) {
		SimpleCondition result = (SimpleCondition) getASGElement(ctx);

		if (result == null) {
			result = new SimpleConditionImpl(programUnit, ctx, producer);

			// type
			final SimpleCondition.SimpleConditionType type;

			if (ctx.condition() != null) {
				result.addCondition(ctx.condition());
				type = SimpleCondition.SimpleConditionType.CONDITION;
			} else if (ctx.relationCondition() != null) {
				result.addRelationCondition(ctx.relationCondition());
				type = SimpleCondition.SimpleConditionType.RELATION_CONDITION;
			} else if (ctx.classCondition() != null) {
				result.addClassCondition(ctx.classCondition());
				type = SimpleCondition.SimpleConditionType.CLASS_CONDITION;
			} else if (ctx.conditionNameReference() != null) {
				result.addConditionNameReference(ctx.conditionNameReference());
				type = SimpleCondition.SimpleConditionType.CONDITION_NAME_REFERENCE;
			} else {
				type = null;
			}

			result.setSimpleConditionType(type);

			simpleCondition = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SimpleCondition getSimpleCondition() {
		return simpleCondition;
	}

	@Override
	public boolean isNot() {
		return not;
	}

	@Override
	public void setNot(final boolean not) {
		this.not = not;
	}
}
