//
//  GooglePlacesHelper.swift
//  Locations
//
//  Created by iem on 12/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

//MARK: - GooglePlacesType
enum GooglePlacesType {
    case SEARCH, DETAIL, IMAGE
}

//MARK: - Extension Alamofire (Request)
extension Alamofire.Request {
    
    //MARK: - Serializer Part
    class func responseImage() -> Serializer {
        return { request, response, data in
            if data == nil {
                return (nil, nil)
            }
            
            let image = UIImage(data: data!, scale: UIScreen.mainScreen().scale)
            
            return (image, nil)
        }
    }
    
    //MARK: - Function
    func responseImage(completionHandler: (NSURLRequest, NSHTTPURLResponse?, UIImage?, NSError?) -> Void) -> Self {
        return response(serializer: Request.responseImage(), completionHandler: { (request, response, image, error) in
            completionHandler(request, response, image as? UIImage, error)
        })
    }
}

//MARK: - GooglePlacesHelper
class GooglePlacesHelper {
    
    //MARK: - Constants
    class var TYPES : [String] { return ["restaurant", "bar", "museum", "park"] }
    class var API_KEY : String { return "AIzaSyDKM0DTyGkocydBgbIatEpiAWwSFCawyTk" }
    class var MAX_WIDTH : String { return "250" }
    
    //MARK: - sharedManager
    class var sharedManager: GooglePlacesHelper {
        struct Singleton {
            static let instance = GooglePlacesHelper()
        }
        
        return Singleton.instance
    }
    
    //MARK: - Functions
    func getPlace(place: GooglePlace?, callback:(NSError?, GooglePlace?) -> Void) -> Void {
        //Create params
        var params = createParams(GooglePlacesType.DETAIL, params: place)
        
        //Execute request & return response
        Alamofire.request(.GET, GooglePlaceRessourceUrl.DETAIL, parameters: params)
            .responseJSON { (request, response, data, error) in
                if(error != nil) {
                    println("Error getPlace: \(error)")
                    callback(error, nil)
                }
                else {
                    var json = JSON(data!)
                    
                    if json[GooglePlaceRessourceKeysJson.ERROR].string == nil {
                        if let currentPlace = place {
                            let result = json[GooglePlaceRessourceKeysJson.RESULT]
                            currentPlace.setDescription(result)
                            self.setImage(place, callback)
                        }
                        else {
                            callback(error, place)
                        }
                    }
                    else {
                        callback(error, place)
                    }
                }
        }
    }
    
    func setImage(place: GooglePlace?, callback:(NSError?, GooglePlace?) -> Void) -> Void {
        //Create params
        var params = createParams(GooglePlacesType.IMAGE, params: place)
        
        //Execute request & set image
        Alamofire.request(.GET, GooglePlaceRessourceUrl.PHOTO, parameters: params)
            .validate(contentType: ["image/*"])
            .responseImage() {
                (request, _, image, error) in
                if error == nil && image != nil {
                    place?.image = image
                    callback(error, place)
                }
                else {
                    println("Error setImage: \(error)")
                    callback(error, nil)
                }
        }
    }
    
    func searchPlaces(location: Location, callback:(NSError?, [GooglePlace]?) -> Void) -> Void {
        //Create params
        var params = createParams(GooglePlacesType.SEARCH, params: location)
        
        //Execute request & return response
        Alamofire.request(.GET, GooglePlaceRessourceUrl.SEARCH, parameters: params)
            .responseJSON { (request, response, data, error) in
                if(error != nil) {
                    println("Error searchPlaces: \(error)")
                    callback(error, nil)
                }
                else {
                    var json = JSON(data!)
                    var places = [GooglePlace]()
                    
                    if json[GooglePlaceRessourceKeysJson.ERROR].string == nil {
                        if let results = json[GooglePlaceRessourceKeysJson.RESULTS].array {
                            for element in results {
                                places.append(GooglePlace(json: element))
                            }
                        }
                    }
                    
                    callback(error, places)
                }
        }
    }
    
    private func createParams(type: GooglePlacesType, params: AnyObject?) -> [String: String]? {
        //Get All types
        var returnValue: [String: String]?
        var types = "|".join(GooglePlacesHelper.TYPES)
        
        //Create good parameters for type
        switch(type) {
        case .DETAIL :
            if let place = params as? GooglePlace {
                if let placeId = place.placeId {
                    returnValue = [
                        GooglePlaceRessourceKeysApi.PLACE_ID: "\(placeId)",
                        GooglePlaceRessourceKeysApi.KEY: "\(GooglePlacesHelper.API_KEY)"
                    ]
                }
            }
            break
        case .SEARCH:
            if let location = params as? Location {
                returnValue = [
                    GooglePlaceRessourceKeysApi.LOCATION: "\(location.latitude),\(location.longitude)",
                    GooglePlaceRessourceKeysApi.RADIUS: "\(location.radius)",
                    GooglePlaceRessourceKeysApi.TYPES: "\(types)",
                    GooglePlaceRessourceKeysApi.KEY: "\(GooglePlacesHelper.API_KEY)"
                ]
            }
            break
        case .IMAGE:
            if let place = params as? GooglePlace {
                if let photos = place.photos {
                    if photos.count > 0 {
                        let photo = photos[0]
                        let photoReference = photo[GooglePlaceRessourceKeysJson.PHOTO_REFERENCE]
                        returnValue = [
                            GooglePlaceRessourceKeysApi.PHOTO_REFERENCE: "\(photoReference)",
                            GooglePlaceRessourceKeysApi.KEY: "\(GooglePlacesHelper.API_KEY)",
                            GooglePlaceRessourceKeysApi.MAX_WIDTH: "\(GooglePlacesHelper.MAX_WIDTH)"
                        ]
                    }
                }
            }
        default:
            break
        }
        
        return returnValue
    }
}