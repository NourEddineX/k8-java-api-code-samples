package org.glasswall.solutions.client;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.glasswall.solutions.exceptions.RebuildApiConfigurationException;
import org.glasswall.solutions.exceptions.RebuildApiException;
import org.glasswall.solutions.exceptions.RebuildApiValidationException;
import org.glasswall.solutions.model.Base64RequestBody;
import org.glasswall.solutions.model.ContentManagementFlags;
import org.glasswall.solutions.model.RebuildRequestBody;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class RebuildClientTest {


	@Test(expected = RebuildApiConfigurationException.class)
	public void testConstructorHostUrlIsNull() {
		new RebuildClient(null, null);
	}

	@Test(expected = RebuildApiConfigurationException.class)
	public void testConstructorHttpClientPresentNotNull() {
		RebuildClient rebuildClient = new RebuildClient("host", null);
	}

	@Test(expected = RebuildApiConfigurationException.class)
	public void testconstructorWithHostOnlyHostIsNull() {
		new RebuildClient(null);
	}

	@Test
	public void testconstructorWithHostOnly() {
		RebuildClient rebuildClient = new RebuildClient("null");
	}

	@Test
	public void testConstructorHttpClientPresent() {
		HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.followRedirects(HttpClient.Redirect.NORMAL)
			.build();
		RebuildClient rebuildClient = new RebuildClient("host", httpClient);
		Assert.assertEquals(httpClient, rebuildClient.getHttpClient());
	}

// 	@SneakyThrows
// 	@Test
// 	public void testRebuild() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);
// 		ContentManagementFlags contentManagementFlags = new ContentManagementFlags();
// 		RebuildRequestBody rebuildRequestBody = RebuildRequestBody
// 			.builder()
// 			.InputGetUrl("requestFileUrl")
// 			.outputPutUrl("destinationFileUrl")
// 			.contentManagementFlags(contentManagementFlags)
// 			.build();
// 		HttpRequest request = HttpRequest.newBuilder()
// 			.PUT(HttpRequest.BodyPublishers.ofString(new Gson().toJson(rebuildRequestBody)))
// 			.uri(URI.create("http://localhost:8080" + "/api/rebuild"))
// 			.setHeader("Authorization", "jwtToken")
// 			.setHeader("Content-Type", "application/json")
// 			.build();
// 		Mockito.when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(Mockito.mock(HttpResponse.class));
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuild("jwtToken", "requestFileUrl", "destinationFileUrl", contentManagementFlags);
// 	}

// 	@Test(expected = RebuildApiValidationException.class)
// 	public void testRebuildBase64FileDoesntExist() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);
// 		ContentManagementFlags contentManagementFlags = new ContentManagementFlags();
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildBase64("jwtToken", "src/test/resources/testfile.txtt", "destinationFileUrl", contentManagementFlags);
// 	}

// 	@Test(expected = RebuildApiValidationException.class)
// 	public void testRebuildBase64FileIsDirectory() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);
// 		ContentManagementFlags contentManagementFlags = new ContentManagementFlags();
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildBase64("jwtToken", "src/test/resources/testfolder", "destinationFileUrl", contentManagementFlags);
// 	}

// 	@SneakyThrows
// 	@Test(expected = RebuildApiException.class)
// 	public void testRebuildBase64UnSuccessResponse() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);
// 		ContentManagementFlags contentManagementFlags = new ContentManagementFlags();
// 		String fileContent = Files.readString(Path.of("src/test/resources/testfile.txt"), StandardCharsets.UTF_8);
// 		String decodedRequestContent = new String(Base64.getDecoder().decode(fileContent));

// 		Base64RequestBody base64RequestBody = Base64RequestBody.builder()
// 			.Base64(decodedRequestContent)
// 			.contentManagementFlags(contentManagementFlags)
// 			.build();

// 		HttpRequest request = HttpRequest.newBuilder()
// 			.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(base64RequestBody)))
// 			.uri(URI.create("http://localhost:8080" + "/api/rebuild/base64"))
// 			.setHeader("Authorization", "jwtToken")
// 			.setHeader("Content-Type", "application/json")
// 			.build();

// 		HttpResponse mock = Mockito.mock(HttpResponse.class);

// 		Mockito.when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mock);
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildBase64("jwtToken", "src/test/resources/testfile.txt", "destinationFileUrl", contentManagementFlags);
// 	}

// 	@SneakyThrows
// 	@Test(expected = RebuildApiException.class)
// 	public void testRebuildBase64ContentAreTheSame() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);
// 		ContentManagementFlags contentManagementFlags = new ContentManagementFlags();
// 		String fileContent = Files.readString(Path.of("src/test/resources/testfile.txt"), StandardCharsets.UTF_8);
// 		String decodedRequestContent = new String(Base64.getDecoder().decode(fileContent));

// 		Base64RequestBody base64RequestBody = Base64RequestBody.builder()
// 			.Base64(decodedRequestContent)
// 			.contentManagementFlags(contentManagementFlags)
// 			.build();

// 		HttpRequest request = HttpRequest.newBuilder()
// 			.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(base64RequestBody)))
// 			.uri(URI.create("http://localhost:8080" + "/api/rebuild/base64"))
// 			.setHeader("Authorization", "jwtToken")
// 			.setHeader("Content-Type", "application/json")
// 			.build();

// 		HttpResponse mock = Mockito.mock(HttpResponse.class);
// 		Mockito.when(mock.statusCode()).thenReturn(200);
// 		Mockito.when(mock.body()).thenReturn("r��z{");

// 		Mockito.when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mock);
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildBase64("jwtToken", "src/test/resources/testfile.txt", "destinationFileUrl", contentManagementFlags);
// 	}

// 	@SneakyThrows
// 	@Test
// 	public void testRebuildBase64() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);
// 		ContentManagementFlags contentManagementFlags = new ContentManagementFlags();
// 		String fileContent = Files.readString(Path.of("src/test/resources/testfile.txt"), StandardCharsets.UTF_8);
// 		String decodedRequestContent = new String(Base64.getDecoder().decode(fileContent));

// 		Base64RequestBody base64RequestBody = Base64RequestBody.builder()
// 			.Base64(decodedRequestContent)
// 			.contentManagementFlags(contentManagementFlags)
// 			.build();

// 		HttpRequest request = HttpRequest.newBuilder()
// 			.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(base64RequestBody)))
// 			.uri(URI.create("http://localhost:8080" + "/api/rebuild/base64"))
// 			.setHeader("Authorization", "jwtToken")
// 			.setHeader("Content-Type", "application/json")
// 			.build();

// 		HttpResponse mock = Mockito.mock(HttpResponse.class);
// 		Mockito.when(mock.statusCode()).thenReturn(200);
// 		Mockito.when(mock.body()).thenReturn("nV");

// 		Mockito.when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(mock);
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildBase64("jwtToken", "src/test/resources/testfile.txt", "src/test/resources/testfile_output.txt", contentManagementFlags);
// 		Files.delete(Path.of("src/test/resources/testfile_output.txt"));
// 	}

// 	@Test
// 	@SneakyThrows
// 	public void testRebuildFile() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);


// 		HttpResponse mock = Mockito.mock(HttpResponse.class);
// 		Mockito.when(mock.statusCode()).thenReturn(200);
// 		Mockito.when(mock.body()).thenReturn("nV");

// 		Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), Mockito.any())).thenReturn(mock);
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildFile("jwtToken", "src/test/resources/testfile.txt", "src/test/resources/testfile_output.txt", new ContentManagementFlags());
// 		Files.delete(Path.of("src/test/resources/testfile_output.txt"));
// 	}

// 	@Test(expected = RebuildApiException.class)
// 	@SneakyThrows
// 	public void testRebuildFileResponseIs500() {
// 		HttpClient httpClient = Mockito.mock(HttpClient.class);


// 		HttpResponse mock = Mockito.mock(HttpResponse.class);
// 		Mockito.when(mock.statusCode()).thenReturn(500);
// 		Mockito.when(mock.body()).thenReturn("nV");

// 		Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), Mockito.any())).thenReturn(mock);
// 		RebuildClient rebuildClient = new RebuildClient("http://localhost:8080", httpClient);
// 		rebuildClient.rebuildFile("jwtToken", "src/test/resources/testfile.txt", "src/test/resources/testfile_output.txt", new ContentManagementFlags());
// 		Files.delete(Path.of("src/test/resources/testfile_output.txt"));
// 	}

}
