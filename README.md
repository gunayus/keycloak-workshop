# keycloak-workshop
Keycloak workshop @JBCNConf'2022

## pre-requisites
+ JDK 11+
+ Keycloak 18 : download and extract keycloak binary from https://github.com/keycloak/keycloak/releases/download/18.0.2/keycloak-18.0.2.zip
+ Postman

in the github repository following modules are provided

+ ldap-server : embedded ldap server module, used to start an ldap server instance
+ backend-spring-boot : spring boot backend module, used to secure REST APIs 
+ frontend-vuejs : vue.js frontend module, used to secure UI application

## embedded ldap server
ldap-server module is provided to start an ldap server for demonstration purposes. in the scope of this workshop we will use ldap server for authenticating users.

follow the steps to start and verify the ldap server
+ open ldap-server module as a maven project
+ run LdapServerApplication.java as a Spring boot application

there are couple of ways to run LdapServerApplication.java as a Spring boot application
#### run from your IDE

![](doc/screen_shot_01-run-ldap-server.png)

#### run with maven wrapper 
run following command from the ldap-server folder
```
cd ldap-server
./mvnw spring-boot:run
```

### verify ldap server
verify that the ldap server is running by requesting following URL
+ http://localhost:8090/ldap/get-users

```
[
	{
		"userId": "bwilson",
		"fullName": "Bruce",
		"lastName": "Wilson",
		"description": null
	},
	{
		"userId": "jbrown",
		"fullName": "James",
		"lastName": "Brown",
		"description": null
	}
]
```

### details about the ldap server 
#### pom.xml
following dependencies are important for booting the embedded ldap server

```
        <dependency>
            <groupId>com.unboundid</groupId>
            <artifactId>unboundid-ldapsdk</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-ldap</artifactId>
        </dependency>
```

#### application.properties
embedded ldap server is configured via the configuration items in the application.properties file e.g. bind username/password, ldap server port, .ldif file as the source of directory repository 
```
spring.ldap.embedded.base-dn=dc=keycloak,dc=org
spring.ldap.embedded.credential.username=uid=admin,ou=system
spring.ldap.embedded.credential.password=secret
spring.ldap.embedded.ldif=classpath:ldap-example-users.ldif
spring.ldap.embedded.port=10389
spring.ldap.embedded.validation.enabled=false
```

#### ldap-example-users.ldif
this file is used to specify 
+ organization structure
+ groups
+ users
+ etc. 

a person (user) is defined as follows
```
dn: uid=jbrown,ou=People,dc=keycloak,dc=org
objectclass: top
objectclass: person
objectclass: organizationalPerson
objectclass: inetOrgPerson
uid: jbrown
cn: James
sn: Brown
mail: jbrown@keycloak.org
postalCode: 88441
userPassword: password
```

a user group and its members are defined as follows
```
dn: cn=normalUsers,ou=UserRoles,dc=keycloak,dc=org
objectclass: top
objectclass: groupOfNames
cn: normalUsers
member: uid=jbrown,ou=People,dc=keycloak,dc=org
```

