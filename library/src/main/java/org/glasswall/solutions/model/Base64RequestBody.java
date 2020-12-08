package org.glasswall.solutions.model;

import lombok.Builder;

@Builder
public class Base64RequestBody {
	private String Base64;
	private ContentManagementFlags contentManagementFlags;
}
