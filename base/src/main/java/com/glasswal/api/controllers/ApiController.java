package com.glasswal.api.controllers;

import org.glasswall.solutions.client.RebuildClient;
import org.glasswall.solutions.model.ContentManagementFlags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Controller
@RestController
@RequestMapping("/api/rebuild")
public class ApiController {

	@Autowired
	private RebuildClient rebuildClient;

	@PostMapping("/base64")

	public void rebuildBase64(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String jwtToken,
							  @RequestParam("source") String filePath,
							  @RequestParam("destination") String destinationFilePath,
							  @RequestBody(required = false) ContentManagementFlags contentManagementFlags) {
		rebuildClient.rebuildBase64(jwtToken, filePath, destinationFilePath, contentManagementFlags);
	}

	@PostMapping
	public void rebuild(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
						@RequestParam("source") String filePath,
						@RequestParam("destination") String destinationFileUrl,
						@RequestBody(required = false) ContentManagementFlags contentManagementFlags) {
		rebuildClient.rebuild(jwtToken, filePath, destinationFileUrl, contentManagementFlags);
	}

	@PostMapping("/file")
	public void rebuildFile(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
							@RequestParam("source") String filePath,
							@RequestParam("destination") String destinationFilePath,
							@RequestBody(required = false) ContentManagementFlags contentManagementFlags) {
		rebuildClient.rebuildFile(jwtToken, filePath, destinationFilePath, contentManagementFlags);
	}

}
