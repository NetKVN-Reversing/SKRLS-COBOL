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

import io.proleap.cobol.Cobol85Parser.InspectForContext;
import io.proleap.cobol.Cobol85Parser.InspectTallyingPhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.procedure.inspect.For;
import io.proleap.cobol.asg.metamodel.procedure.inspect.Tallying;

public class TallyingImpl extends InspectPhraseImpl implements Tallying {

	protected final InspectTallyingPhraseContext ctx;

	protected List<For> fors = new ArrayList<For>();

	public TallyingImpl(final ProgramUnit programUnit, final InspectTallyingPhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public For addFor(final InspectForContext ctx) {
		For result = (For) getASGElement(ctx);

		if (result == null) {
			result = createFor(ctx);
			fors.add(result);
		}

		return result;
	}

	@Override
	public List<For> getFors() {
		return fors;
	}

}
