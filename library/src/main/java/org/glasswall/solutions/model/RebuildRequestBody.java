package org.glasswall.solutions.model;

import lombok.Builder;

@Builder
public class RebuildRequestBody {
	private String InputGetUrl, outputPutUrl;
	private ContentManagementFlags contentManagementFlags;
}
