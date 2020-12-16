package org.glasswall.solutions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContentManagementFlags{

	@JsonProperty("PowerPointContentManagement")
	private PowerPointContentManagement powerPointContentManagement;

	@JsonProperty("PdfContentManagement")
	private PdfContentManagement pdfContentManagement;

	@JsonProperty("ExcelContentManagement")
	private ExcelContentManagement excelContentManagement;

	@JsonProperty("WordContentManagement")
	private WordContentManagement wordContentManagement;
}