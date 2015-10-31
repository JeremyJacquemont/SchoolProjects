//
//  RatingManager.swift
//  Locations
//
//  Created by iem on 13/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import CoreData
import Foundation

class RatingManager: EntityManager {
    
    //MARK: - Override sharedManager
    override class var sharedManager: RatingManager {
        struct Singleton {
            static let instance = RatingManager()
        }
        
        return Singleton.instance
    }
    
    //Functions
    func isExist(id: String) -> Bool {
        if let element = self.getOne(id) {
            return true
        }
        else {
            return false
        }
    }
    
    //MARK: - Override Abstract functions
    override func add(informations: Dictionary<String, AnyObject>) -> NSManagedObject? {
        var dict = informations as NSDictionary
        
        let entity = NSEntityDescription.entityForName(Rating.TABLE_NAME, inManagedObjectContext: RatingManager.managedObjectContext)
        let rating = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: RatingManager.managedObjectContext) as Rating
        
        rating.id = dict.valueForKey(Rating.FIELD_ID) as String
        rating.note = dict.valueForKey(Rating.FIELD_NOTE) as NSNumber
        
        var error: NSError? = nil
        RatingManager.managedObjectContext.save(&error)
        
        if error != nil {
            println("Could not save context : \(error), \(error?.description)")
            return nil
        }
        else {
            return rating
        }
    }
    override func update(entity: NSManagedObject?, informations: Dictionary<String, AnyObject>) -> NSManagedObject? {
        var rating = entity as Rating
        var dict = informations as NSDictionary
        
        rating.note = dict.valueForKey(Rating.FIELD_NOTE) as NSNumber
        
        var error: NSError? = nil
        RatingManager.managedObjectContext.save(&error)
        
        if error != nil {
            println("Could not save context : \(error), \(error?.description)")
            return nil
        }
        else {
            return rating
        }

    }
    override func delete(entity: NSManagedObject?) {
        if let entityDelete = entity {
            RatingManager.managedObjectContext.deleteObject(entityDelete)
            RatingManager.managedObjectContext.save(nil)
        }
    }
    override func getOne(key: String?) -> NSManagedObject? {
        if key != nil {
            let predicate = NSPredicate(format: "id==%@", key!)
            let fetchRequest = NSFetchRequest(entityName: Rating.TABLE_NAME)
            fetchRequest.predicate = predicate
        
            var error: NSError? = nil
            let results = RatingManager.managedObjectContext.executeFetchRequest(fetchRequest, error: &error) as [Rating]
            if results.count > 0 {
                return results[0]
            }
        
            return nil
        }
        
        return nil
    }
    
}