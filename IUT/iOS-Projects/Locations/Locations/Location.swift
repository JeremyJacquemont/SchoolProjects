//
//  Location.swift
//  Locations
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation
import CoreData

class Location: NSManagedObject {
    
    //MARK: - Constants
    class var TABLE_NAME: String { return "Location" }
    class var FIELD_NAME: String { return "name" }
    class var FIELD_RADIUS: String { return "radius" }
    class var FIELD_LATITUDE: String { return "latitude" }
    class var FIELD_LONGITUDE: String { return "longitude" }

    //MARK: - Variables
    @NSManaged var name: String
    @NSManaged var radius: NSNumber
    @NSManaged var latitude: NSNumber
    @NSManaged var longitude: NSNumber

}
