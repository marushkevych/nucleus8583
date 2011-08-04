package org.nucleus8583.core.field.type;

import org.nucleus8583.core.xml.FieldAlignments;
import org.nucleus8583.core.xml.FieldDefinition;

public final class UnicodeLLLVarStringFieldType extends UnicodeVarStringFieldType {
	private static final long serialVersionUID = -5615324004502124085L;

	public UnicodeLLLVarStringFieldType(FieldDefinition def, FieldAlignments defaultAlign,
			String defaultPadWith, String defaultEmptyValue) {
		super(def, defaultAlign, defaultPadWith, defaultEmptyValue, 3, 999);
	}
}