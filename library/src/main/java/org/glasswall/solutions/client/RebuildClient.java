package org.glasswall.solutions.client;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.glasswall.solutions.exceptions.RebuildApiConfigurationException;
import org.glasswall.solutions.exceptions.RebuildApiException;
import org.glasswall.solutions.exceptions.RebuildApiValidationException;
import org.glasswall.solutions.model.Base64RequestBody;
import org.glasswall.solutions.model.ContentManagementFlags;
import org.glasswall.solutions.model.RebuildRequestBody;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RebuildClient {

	private final String hostUrl;
	private final HttpClient httpClient;

	public RebuildClient(String hostUrl, HttpClient httpClient) {
		if (null == hostUrl) {
			throw new RebuildApiConfigurationException("Host url is null");
		}

		Collections.sort(Arrays.asList(new RebuildClient("")));
		this.hostUrl = hostUrl;

		if (httpClient == null) {
			throw new RebuildApiConfigurationException("HttpClient is null");
		}
		asd(Arrays.asList(Double.valueOf(10.0)));
		this.httpClient = httpClient;

	}

	public RebuildClient(String hostUrl) {
		if (null == hostUrl) {
			throw new RebuildApiConfigurationException("Host url is null");
		}
		this.hostUrl = hostUrl;
		this.httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.followRedirects(HttpClient.Redirect.NORMAL)
			.build();
	}

	@SneakyThrows
	public void rebuildBase64(String jwtToken, String filePath, String destinationFilePath, ContentManagementFlags contentManagementFlags) {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new RebuildApiValidationException("File with path " + filePath + " doesn't exist");
		}
		if (file.isDirectory()) {
			throw new RebuildApiValidationException("File is directory, please provide another filePath");
		}

		byte[] bytes = FileUtils.readFileToByteArray(new File(filePath));
		String  decodedRequestContent = new String(Base64.getEncoder().encode(bytes));

		Base64RequestBody base64RequestBody = Base64RequestBody.builder()
			.Base64(decodedRequestContent)
			.contentManagementFlags(contentManagementFlags)
			.build();

		HttpRequest request = HttpRequest.newBuilder()
			.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(base64RequestBody)))
			.uri(URI.create(hostUrl + "/api/rebuild/base64"))
			.setHeader("Authorization", ""+jwtToken)
			.setHeader("Content-Type", "application/json")
			.build();

		HttpResponse<byte[]> decodedStringResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

		if (decodedStringResponse.statusCode() != 200) {
			throw new RebuildApiException("Response is not 200");
		}
		if (Arrays.equals(decodedStringResponse.body(),bytes)) {
			throw new RebuildApiException("Request and response decoded content are the same!");
		}
		byte[] encode = Base64.getDecoder().decode(decodedStringResponse.body());
		FileUtils.writeByteArrayToFile(new File(destinationFilePath), encode);
	}

	@SneakyThrows
	public void rebuild(String jwtToken, String requestFileUrl, String destinationFileUrl, ContentManagementFlags contentManagementFlags) {
		RebuildRequestBody rebuildRequestBody = RebuildRequestBody
			.builder()
			.InputGetUrl(requestFileUrl)
			.outputPutUrl(destinationFileUrl)
			.contentManagementFlags(contentManagementFlags)
			.build();
		HttpRequest request = HttpRequest.newBuilder()
			.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(rebuildRequestBody)))
			.uri(URI.create(hostUrl + "/api/rebuild"))
			.setHeader("Authorization", "Bearer " + jwtToken)
			.setHeader("Content-Type", "application/json")
			.build();

		HttpResponse<String> decodedStringResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		if (decodedStringResponse.statusCode() != 200) {
			throw new RebuildApiException("Response is not 200");
		}
		String decodedResponseContent = decodedStringResponse.body();
	}

	@SneakyThrows
	public void rebuildFile(String jwtToken, String inputFile, String destinationFileUrl, ContentManagementFlags contentManagementFlags) {

		String boundary = "-------------oiawn4tp89n4e9p5";
		Map<Object, Object> data = new HashMap<>();

		// some form fields
		data.put("contentManagementFlagJson", new Gson().toJson(contentManagementFlags));

		// file upload
		data.put("file", Paths.get(inputFile));

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(hostUrl + "/api/rebuild/file"))
			.headers("Content-Type",
				"multipart/form-data; boundary=" + boundary)
			.POST(oMultipartData(data, boundary))
			.header("Authorization", jwtToken)
			.build();

		HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		if (send.statusCode() != 200) {
			throw new RebuildApiException("Response is not 200");
		}

		byte[] encode = Base64.getDecoder().decode(send.body().getBytes());
		FileUtils.writeByteArrayToFile(new File(destinationFileUrl), encode);
	}

	public static HttpRequest.BodyPublisher oMultipartData(Map<Object, Object> data,
														   String boundary) throws IOException {
		var byteArrays = new ArrayList<byte[]>();
		byte[] separator = ("--" + boundary
			+ "\r\nContent-Disposition: form-data; name=")
			.getBytes(StandardCharsets.UTF_8);
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			byteArrays.add(separator);

			if (entry.getValue() instanceof Path) {
				var path = (Path) entry.getValue();
				String mimeType = Files.probeContentType(path);
				byteArrays.add(("\"" + entry.getKey() + "\"; filename=\""
					+ path.getFileName() + "\"\r\nContent-Type: " + mimeType
					+ "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
				byteArrays.add(Files.readAllBytes(path));
				byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
			} else {
				byteArrays.add(
					("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue()
						+ "\r\n").getBytes(StandardCharsets.UTF_8));
			}
		}
		byteArrays
			.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
		return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
	}

}
