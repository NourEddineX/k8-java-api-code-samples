package org.glasswall.solutions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PdfContentManagement{

	@JsonProperty("ExternalHyperlinks")
	private int externalHyperlinks;

	@JsonProperty("ActionsAll")
	private int actionsAll;

	@JsonProperty("Metadata")
	private int metadata;

	@JsonProperty("Javascript")
	private int javascript;

	@JsonProperty("Acroform")
	private int acroform;

	@JsonProperty("InternalHyperlinks")
	private int internalHyperlinks;

	@JsonProperty("EmbeddedFiles")
	private int embeddedFiles;

	@JsonProperty("EmbeddedImages")
	private int embeddedImages;
}