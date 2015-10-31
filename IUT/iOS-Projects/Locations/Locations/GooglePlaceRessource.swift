//
//  GooglePlaceRessource.swift
//  Locations
//
//  Created by iem on 13/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation

//MARK: - GooglePlaceRessourceUrl
struct GooglePlaceRessourceUrl {
    static let SEARCH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
    static let DETAIL = "https://maps.googleapis.com/maps/api/place/details/json"
    static let PHOTO = "https://maps.googleapis.com/maps/api/place/photo"
}

//MARK: - GooglePlaceRessourceKeysApi
struct GooglePlaceRessourceKeysApi {
    static let KEY = "key"
    static let PLACE_ID = "placeid"
    static let LOCATION = "location"
    static let RADIUS = "radius"
    static let TYPES = "types"
    static let PHOTO_REFERENCE = "photoreference"
    static let MAX_WIDTH = "maxwidth"
}

//MARK: - GooglePlaceRessourceKeysJson
struct GooglePlaceRessourceKeysJson {
    static let ERROR = "error"
    static let RESULT = "result"
    static let RESULTS = "results"
    
    static let ID = "id"
    static let PLACE_ID = "place_id"
    static let NAME = "name"
    static let GEOMETRY = "geometry"
    static let LOCATION = "location"
    static let LATITUE = "lat"
    static let LONGITUDE = "lng"
    static let TYPES = "types"
    static let FORMATED_ADRESS = "formatted_address"
    static let INTERNATIONAL_PHONE_NUMBER = "international_phone_number"
    static let RATING = "rating"
    static let REVIEWS = "reviews"
    static let PHOTOS = "photos"
    static let PHOTO_REFERENCE = "photo_reference"
}

