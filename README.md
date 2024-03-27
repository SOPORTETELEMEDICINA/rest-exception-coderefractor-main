# REST libaries

## Installation
    1.- Add repository in maven pom.xml
        <repository>
            <id>central</id>
            <name>Amentum</name>
            <url>http://192.168.100.233/libs-release-local</url>
        </repository>
    2.- Add maven dependency to your project
        <dependency>
            <groupId>net.amentum</groupId>
            <artifactId>rest-exception</artifactId>
            <version>1.0.0-RELEASE</version>
        </dependency>

        be careful with the version of dependency

## Usage
    1.- BaseController - Class to manage exceptions extend your rest controller using this class
    2.- GenericException - Super class for exceptions
    3.- RequestFilter - To add uuid in logs - add a bean configuration to your project, example

        @Configuration
        public class LogFilterConfiguration {
            @Bean
            public FilterRegistrationBean getFilter(){
                FilterRegistrationBean filter = new FilterRegistrationBean();
                filter.setFilter(new RequestFilter());
                filter.addUrlPatterns("/*");
                filter.setName("requestFilter");
                return filter;
            }
        }

## Contributing
    1. Fork it!
    2. Create your feature branch: `git checkout -b my-new-feature`
    3. Commit your changes: `git commit -am 'Add some feature'`
    4. Push to the branch: `git push origin my-new-feature`
    5. Submit a pull request :D
## History
    1.- First commit with implementations for BaseController, GenericException and RequestFilter
## Credits
    1.- Victor de la Cruz
## License
    Private for Amentum IT Services