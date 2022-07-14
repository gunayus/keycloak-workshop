package com.layermark.springmeetup.keycloak.ldapserver.controller;

import java.util.List;

import com.layermark.springmeetup.keycloak.ldapserver.model.Person;
import com.layermark.springmeetup.keycloak.ldapserver.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/ldap")
public class LdapController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/get-user-names")
    public ResponseEntity<List<String>> getLdapUserNames() {
        return new ResponseEntity<>(personRepository.getAllPersonNames(), HttpStatus.OK);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<Person>> getLdapUsers() {
        return new ResponseEntity<>(personRepository.getAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<Person> findLdapPerson(@RequestParam(name = "user-id") String userId) {
        return new ResponseEntity<>(personRepository.getPersonNamesByUid(userId), HttpStatus.OK);
    }
}
