## Automated web tests using Serenity, Cucumber and Maven

A simple example of some BDD-style automated acceptance criteria, running against http://etsy.com. 

Run the tests like this:

```
gradle clean test aggregate
```

By default, the tests run with Chrome, so you will need this installed. Otherwise, if you prefer Explorer, modify the serenity.properties file:
```
webdriver.driver = chrome
webdriver.driver = iexplorer
webdriver.chrome.driver = C:\\tools\\chromedriver.exe
```

The reports will be generated in `target/site/serenity`.
