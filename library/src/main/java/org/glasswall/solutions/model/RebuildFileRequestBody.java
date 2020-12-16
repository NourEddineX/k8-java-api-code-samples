package org.glasswall.solutions.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RebuildFileRequestBody {
	private String InputGetUrl, outputPutUrl;
	private ContentManagementFlags contentManagementFlags;
}
