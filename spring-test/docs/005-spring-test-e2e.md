# Spring e2e tests

To test the application end to end, it is necessary to start it and `@SpringBootTest` annotation helps to do that.
Actually, there is nothing new from what was used before apart from different ways of running the application.

* `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)` - in this case the app will be started
  of a fixed port and the port might be set by using `properties = {"server.port=8082"}` parameter of this annotation.
* `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)` - in this case the app will be started
  on a random port and it can be injected by using `@LocalServerPort` annotation as a property. As a
  simplification, `TestRestTemplate` might be used - it is nothing but an ordinary `RestTemplate` that is configured to
  send requests to the running application. 
* `@SpringBootTest` - this is a default option and no port will be exposed, the app will be available by
  using `MockMvc`.