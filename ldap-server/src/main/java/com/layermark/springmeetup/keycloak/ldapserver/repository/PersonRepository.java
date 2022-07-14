package com.layermark.springmeetup.keycloak.ldapserver.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import com.layermark.springmeetup.keycloak.ldapserver.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonRepository {
    private final LdapTemplate ldapTemplate;

    public List<String> getAllPersonNames() {
        List<String> list = ldapTemplate.search(query().where("objectclass").is("person"),
                new PersonNameAttributesMapper());
        return list;
    }
    public List<Person> getAllPersons() {
        return ldapTemplate.search(query().where("objectclass").is("person"), new PersonAttributesMapper());
    }
    public Person getPersonNamesByUid(String userId) {
        List<Person> people = ldapTemplate.search(query().where("uid").is(userId), new PersonAttributesMapper());
        return ((null != people && !people.isEmpty()) ? people.get(0) : null);
    }
    private class PersonAttributesMapper implements AttributesMapper<Person> {
        public Person mapFromAttributes(Attributes attrs) throws NamingException {
            Person person = new Person();
            person.setUserId(null != attrs.get("uid") ? (String) attrs.get("uid").get() : null);
            person.setFullName((String) attrs.get("cn").get());
            person.setLastName((String) attrs.get("sn").get());
            person.setDescription(null != attrs.get("description") ? (String) attrs.get("description").get() : null);
            return person;
        }
    }
    private class PersonNameAttributesMapper implements AttributesMapper<String> {
        public String mapFromAttributes(Attributes attrs) throws NamingException {
            return String.format("%s - %s, %s",
                    attrs.get("uid").get().toString(),
                    attrs.get("sn").get().toString(),
                    attrs.get("cn").get().toString());
        }
    }
}
