/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.workingstorage.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.WorkingStorageSectionContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.impl.DataDescriptionEntryContainerImpl;
import io.proleap.cobol.asg.metamodel.data.workingstorage.WorkingStorageSection;

public class WorkingStorageSectionImpl extends DataDescriptionEntryContainerImpl implements WorkingStorageSection {

	protected final DataDescriptionEntryContainerType containerType = DataDescriptionEntryContainerType.WORKING_STORAGE_SECTION;

	protected final WorkingStorageSectionContext ctx;

	public WorkingStorageSectionImpl(final ProgramUnit programUnit, final WorkingStorageSectionContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public DataDescriptionEntryContainerType getContainerType() {
		return containerType;
	}
}
