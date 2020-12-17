


### Samples how to initialize Java API client for Rebuild API

Example how to initialize call for /api/rebuild endpoint
```		String jwt = "jwtToken";
   		String rebuildAPIURL = "https://gzlhbtpvk2.execute-api.eu-west-1.amazonaws.com/Prod";
   		RebuildClient rebuildClient = new RebuildClient(rebuildAPIURL);
   		String requestFilePath = "https://localhost:8080/requestFilePath.pdf";
   		String destinationFilePath = "/destination/file/path";
   		rebuildClient.rebuild(jwt, requestFilePath, destinationFilePath, new ContentManagementFlags());
```

Example of http call 
``curl --location --request POST 'http://localhost:8080/api/rebuild?source=/Users/nikita/Documents/glasswall-api/src/main/resources/JS_Siemens.pdf&destination=/Users/nikita/Documents/glasswall-api/src/main/resources/JS_Siemens1.pdf' \
  --header 'Authorization: jwt token'``

Example how to initialize call for /api/rebuild/file endpoint
```		
		String jwt = "jwtToken";
		String rebuildAPIURL = "https://gzlhbtpvk2.execute-api.eu-west-1.amazonaws.com/Prod";
		RebuildClient rebuildClient = new RebuildClient(rebuildAPIURL);
		String requestFilePath = "https://localhost:8080/requestFilePath.pdf";
		String destinationFilePath = "https://localhost:8080/destinationFilePath.pdf";
		rebuildClient.rebuildFile(jwt, requestFilePath, destinationFilePath, new ContentManagementFlags());
```

Example of http call 
``curl --location --request POST 'http://localhost:8080/api/rebuild/file?source=/Users/nikita/Documents/glasswall-api/src/main/resources/JS_Siemens.pdf&destination=/Users/nikita/Documents/glasswall-api/src/main/resources/JS_Siemens1.pdf' \
  --header 'Authorization: jwt token'``

Example how to initialize call for /api/rebuild/base64 endpoint
```
		String jwt = "jwtToken";
		String rebuildAPIURL = "https://gzlhbtpvk2.execute-api.eu-west-1.amazonaws.com/Prod";
		RebuildClient rebuildClient = new RebuildClient(rebuildAPIURL);
		String requestFilePath = "/request/file/path/file.pdf";
		String destinationFilePath =  "/request/file/path/file.pdf";;
		rebuildClient.rebuildBase64(jwt, requestFilePath, destinationFilePath, new ContentManagementFlags());
```

Example of http call 
``curl --location --request POST 'http://localhost:8080/api/rebuild/base64?source=/Users/nikita/Documents/glasswall-api/src/main/resources/JS_Siemens.pdf&destination=/Users/nikita/Documents/glasswall-api/src/main/resources/JS_Siemens1.pdf' \
  --header 'Authorization: jwt token'``
