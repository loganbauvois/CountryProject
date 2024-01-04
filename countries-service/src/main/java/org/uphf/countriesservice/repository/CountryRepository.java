package org.uphf.countriesservice.repository;

import org.uphf.countriesservice.entities.Country;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface CountryRepository extends MongoRepository<Country, ObjectId> {

}
