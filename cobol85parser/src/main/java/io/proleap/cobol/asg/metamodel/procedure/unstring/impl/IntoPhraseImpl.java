/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.unstring.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.UnstringIntoContext;
import io.proleap.cobol.Cobol85Parser.UnstringIntoPhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.unstring.Into;
import io.proleap.cobol.asg.metamodel.procedure.unstring.IntoPhrase;

public class IntoPhraseImpl extends CobolDivisionElementImpl implements IntoPhrase {

	protected final UnstringIntoPhraseContext ctx;

	protected List<Into> intos = new ArrayList<Into>();
	
	private final Producer producer;

	public IntoPhraseImpl(final ProgramUnit programUnit, final UnstringIntoPhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Into addInto(final UnstringIntoContext ctx) {
		Into result = (Into) getASGElement(ctx);

		if (result == null) {
			result = new IntoImpl(programUnit, ctx, producer);

			// call
			if (ctx.identifier() != null) {
				final Call intoCall = createCall(ctx.identifier());
				result.setIntoCall(intoCall);
			}

			// delimiter in
			if (ctx.unstringDelimiterIn() != null) {
				result.addDelimiterIn(ctx.unstringDelimiterIn());
			}

			// count in
			if (ctx.unstringCountIn() != null) {
				result.addCountIn(ctx.unstringCountIn());
			}

			intos.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<Into> getIntos() {
		return intos;
	}

}
