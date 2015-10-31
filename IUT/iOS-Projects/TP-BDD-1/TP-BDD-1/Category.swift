//
//  Category.swift
//  TP-BDD-1
//
//  Created by iem on 08/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation
import CoreData

class Category: NSManagedObject {
    class var TABLE_NAME: String { return "Category" }
    class var FIELD_NAME: String { return "name" }
    class var FIELD_TASKS: String { return "tasks" }
    class var ALL_FIELDS: [String] { return [Category.FIELD_NAME, Category.FIELD_TASKS] }
    
    @NSManaged var name: String
    @NSManaged var tasks: NSSet

}
