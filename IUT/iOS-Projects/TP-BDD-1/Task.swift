//
//  Task.swift
//  TP-BDD-1
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation
import CoreData

func == (lhs : Task , rhs : Task) -> Bool {
    return lhs.name == rhs.name && lhs.text == rhs.text && lhs.date == rhs.date && lhs.photo == rhs.photo
}

class Task: NSManagedObject {
    class var TABLE_NAME: String { return "Task" }
    class var FIELD_NAME: String { return "name" }
    class var FIELD_TEXT: String { return "text" }
    class var FIELD_DATE: String { return "date" }
    class var FIELD_PHOTO: String { return "photo" }
    class var FIELD_CATEGORY: String { return "category" }
    class var ALL_FIELDS: [String] { return [Task.FIELD_NAME, Task.FIELD_TEXT, Task.FIELD_DATE, Task.FIELD_PHOTO, Task.FIELD_CATEGORY] }
    
    @NSManaged var name: String
    @NSManaged var text: String?
    @NSManaged var date: NSDate?
    @NSManaged var photo: NSData
    @NSManaged var category: Category
    
}
