//
//  Rating.swift
//  Locations
//
//  Created by iem on 13/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation
import CoreData

class Rating: NSManagedObject {
    
    //MARK: - Constants
    class var TABLE_NAME: String { return "Rating" }
    class var FIELD_ID: String { return "id" }
    class var FIELD_NOTE: String { return "note" }
    
    //MARK: - Variables
    @NSManaged var id: String
    @NSManaged var note: NSNumber

}
