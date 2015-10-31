//
//  Place.swift
//  Locations
//
//  Created by iem on 12/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit
import MapKit
import SwiftyJSON

class GooglePlace {
    //MARK: - Variables
    var id: String?
    var placeId: String?
    var name: String?
    var location: CLLocationCoordinate2D?
    var types: [String]?
    var photos: [JSON]?
    var image: UIImage?
    var description: String?
    var rating: Rating?
    
    //MARK: - Constructor
    init(json: JSON?) {
        if let jsonEntity = json {
            
            //Set id
            if let id = jsonEntity[GooglePlaceRessourceKeysJson.ID].string {
                self.id = id
            }
            
            //Set place id
            if let placeId = jsonEntity[GooglePlaceRessourceKeysJson.PLACE_ID].string {
                self.placeId = placeId
            }
            
            //Set Name
            if let name = jsonEntity[GooglePlaceRessourceKeysJson.NAME].string {
                self.name = name
            }
            
            //Set location
            let geometry = jsonEntity[GooglePlaceRessourceKeysJson.GEOMETRY]
            let location = geometry[GooglePlaceRessourceKeysJson.LOCATION]
            self.location =
                CLLocationCoordinate2D(
                    latitude: location[GooglePlaceRessourceKeysJson.LATITUE].doubleValue,
                    longitude: location[GooglePlaceRessourceKeysJson.LONGITUDE].doubleValue
                )
            
            //Set types 
            if let types = jsonEntity[GooglePlaceRessourceKeysJson.TYPES].arrayObject as? [String] {
                self.types = types
            }
            
            //Set Rating
            if self.id != nil && RatingManager.sharedManager.isExist(self.id!) {
                self.rating = RatingManager.sharedManager.getOne(self.id!) as? Rating
            }
        }
    }
    
    //Functions
    func setDescription(informations: JSON?) {
        var adresse = String()
        var tel = String()
        var rating = Int()
        var nbReviews = Int()
        
        if let json = informations {
            if let jsonAdresse = json[GooglePlaceRessourceKeysJson.FORMATED_ADRESS].string {
                adresse = jsonAdresse
            }
            
            if let jsonTel = json[GooglePlaceRessourceKeysJson.INTERNATIONAL_PHONE_NUMBER].string {
                tel = jsonTel
            }
            
            if let jsonRating = json[GooglePlaceRessourceKeysJson.RATING].int {
                rating = jsonRating
            }
            
            if let jsonReviews = json[GooglePlaceRessourceKeysJson.REVIEWS].array {
                nbReviews = jsonReviews.count
            }
            
            if let photos = json[GooglePlaceRessourceKeysJson.PHOTOS].array {
                self.photos = photos
            }
        }
        self.description = "Adresse: \(adresse) \n\nTel: \(tel) \n\nRating (Google): \(rating)\n\nNumbers of Review (Google): \(nbReviews)"
    }
    
}