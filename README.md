
### Samples how to initialize Java API client for Rebuild API

Example how to initialize call for /api/rebuild endpoint
```		String jwt = "jwtToken";
   		String rebuildAPIURL = "https://gzlhbtpvk2.execute-api.eu-west-1.amazonaws.com/Prod";
   		RebuildClient rebuildClient = new RebuildClient(rebuildAPIURL);
   		String requestFilePath = "https://localhost:8080/requestFilePath.pdf";
   		String destinationFilePath = "/destination/file/path";
   		rebuildClient.rebuild(jwt, requestFilePath, destinationFilePath, new ContentManagementFlags());
```

Example how to initialize call for /api/rebuild/file endpoint
```		
		String jwt = "jwtToken";
		String rebuildAPIURL = "https://gzlhbtpvk2.execute-api.eu-west-1.amazonaws.com/Prod";
		RebuildClient rebuildClient = new RebuildClient(rebuildAPIURL);
		String requestFilePath = "https://localhost:8080/requestFilePath.pdf";
		String destinationFilePath = "https://localhost:8080/destinationFilePath.pdf";
		rebuildClient.rebuildFile(jwt, requestFilePath, destinationFilePath, new ContentManagementFlags());
```

Example how to initialize call for /api/rebuild/base64 endpoint
```
		String jwt = "jwtToken";
		String rebuildAPIURL = "https://gzlhbtpvk2.execute-api.eu-west-1.amazonaws.com/Prod";
		RebuildClient rebuildClient = new RebuildClient(rebuildAPIURL);
		String requestFilePath = "/request/file/path/file.pdf";
		String destinationFilePath =  "/request/file/path/file.pdf";;
		rebuildClient.rebuildBase64(jwt, requestFilePath, destinationFilePath, new ContentManagementFlags());
```

Instructions how to build and run docker container 

```
docker build --tag web-service .
docker run --publish 8000:8080 --detach --name bb web-service
```

and it will run application on 8000 port