/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.arithmetic.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.MultDivContext;
import io.proleap.cobol.Cobol85Parser.MultDivsContext;
import io.proleap.cobol.Cobol85Parser.PowerContext;
import io.proleap.cobol.Cobol85Parser.PowersContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.valuestmt.arithmetic.MultDiv;
import io.proleap.cobol.asg.metamodel.valuestmt.arithmetic.MultDivs;
import io.proleap.cobol.asg.metamodel.valuestmt.arithmetic.Powers;
import io.proleap.cobol.asg.metamodel.valuestmt.impl.ValueStmtImpl;

public class MultDivsImpl extends ValueStmtImpl implements MultDivs {

	protected MultDivsContext ctx;

	protected List<MultDiv> multDivs = new ArrayList<MultDiv>();

	protected Powers powers;
	
	private final Producer producer;

	public MultDivsImpl(final ProgramUnit programUnit, final MultDivsContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public MultDiv addMultDiv(final MultDivContext ctx) {
		MultDiv result = (MultDiv) getASGElement(ctx);

		if (result == null) {
			result = new MultDivImpl(programUnit, ctx, producer);

			// type
			final MultDiv.MultDivType type;

			if (ctx.ASTERISKCHAR() != null) {
				type = MultDiv.MultDivType.MULT;
			} else if (ctx.SLASHCHAR() != null) {
				type = MultDiv.MultDivType.DIV;
			} else {
				type = null;
			}

			result.setMultDivType(type);

			// powers
			result.addPowers(ctx.powers());

			multDivs.add(result);
			subValueStmts.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Powers addPowers(final PowersContext ctx) {
		Powers result = (Powers) getASGElement(ctx);

		if (result == null) {
			result = new PowersImpl(programUnit, ctx, producer);

			// type
			final Powers.PowersType type;

			if (ctx.PLUSCHAR() != null) {
				type = Powers.PowersType.PLUS;
			} else if (ctx.MINUSCHAR() != null) {
				type = Powers.PowersType.MINUS;
			} else {
				type = null;
			}

			result.setPowersType(type);

			// basis
			result.addBasis(ctx.basis());

			// power
			for (final PowerContext powerContext : ctx.power()) {
				result.addPower(powerContext);
			}

			powers = result;
			subValueStmts.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<MultDiv> getMultDivs() {
		return multDivs;
	}

	@Override
	public Powers getPowers() {
		return powers;
	}
}
