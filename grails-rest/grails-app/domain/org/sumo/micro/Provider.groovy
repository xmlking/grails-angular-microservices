package org.sumo.micro

//TODO Provider children : physician , hospital

import groovy.transform.ToString
import org.bson.types.ObjectId

@ToString(includeNames = true, includeFields = true)
class Provider {

    ObjectId id

    String      firstName
    String      middleName
    String      lastName
    GenderType  gender

    Date dateOfBirth
    Date effectiveDate
    Date cancellationDate

    String  npi
    String  image
    int     rating
    ProviderType type

    List<Contact> contacts

    static embedded = ['socialMedias']
    static hasMany = [specialties: Specialty, socialMedias: SocialMedia, contacts: Contact]

    static constraints = {
        npi         nullable: false, blank: false,  maxSize:12
        firstName   nullable: true, blank: true
        middleName  nullable: true, blank: true
        image       blank: true, nullable: true
        gender      nullable: true
        dateOfBirth nullable: true

    }
    static mapping = {
        cache true
        specialties cache: true

        npi index: true
    }
}




