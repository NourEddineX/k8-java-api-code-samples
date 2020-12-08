package org.glasswall.solutions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PowerPointContentManagement{

	@JsonProperty("ExternalHyperlinks")
	private int externalHyperlinks;

	@JsonProperty("Metadata")
	private int metadata;

	@JsonProperty("InternalHyperlinks")
	private int internalHyperlinks;

	@JsonProperty("ReviewComments")
	private int reviewComments;

	@JsonProperty("EmbeddedFiles")
	private int embeddedFiles;

	@JsonProperty("Macros")
	private int macros;

	@JsonProperty("EmbeddedImages")
	private int embeddedImages;
}