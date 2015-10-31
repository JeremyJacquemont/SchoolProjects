//
//  LocationManager.swift
//  Locations
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import CoreData
import Foundation

class LocationManager: EntityManager {
    
    //MARK: - Override sharedManager
    override class var sharedManager: LocationManager {
        struct Singleton {
            static let instance = LocationManager()
        }
        
        return Singleton.instance
    }
    
    //MARK: - Override Abstract functions
    override func add(informations: Dictionary<String, AnyObject>) -> NSManagedObject? {
        var dict = informations as NSDictionary
        
        let entity = NSEntityDescription.entityForName(Location.TABLE_NAME, inManagedObjectContext: LocationManager.managedObjectContext)
        let location = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: LocationManager.managedObjectContext) as Location
        
        location.name = dict.valueForKey(Location.FIELD_NAME)! as String
        location.latitude = dict.valueForKey(Location.FIELD_LATITUDE)! as NSNumber
        location.longitude = dict.valueForKey(Location.FIELD_LONGITUDE)! as NSNumber
        location.radius = dict.valueForKey(Location.FIELD_RADIUS)! as NSNumber
        
        var error : NSError?
        LocationManager.managedObjectContext.save(&error)
        
        if error != nil {
            println("Not save Location: \(error?.description)")
        }
        
        return location
    }
    override func update(entity: NSManagedObject?, informations: Dictionary<String, AnyObject>) -> NSManagedObject? {
        var dict = informations as NSDictionary
        let location = entity as Location
        
        location.name = dict.valueForKey(Location.FIELD_NAME)! as String
        location.latitude = dict.valueForKey(Location.FIELD_LATITUDE)! as NSNumber
        location.longitude = dict.valueForKey(Location.FIELD_LONGITUDE)! as NSNumber
        location.radius = dict.valueForKey(Location.FIELD_RADIUS)! as NSNumber
        
        var error : NSError?
        LocationManager.managedObjectContext.save(&error)
        
        if error != nil {
            println("Not update Location: \(error?.description)")
        }
        
        return location
    }
    override func delete(entity: NSManagedObject?) {
        if let entityDelete = entity {
            EntityManager.managedObjectContext.deleteObject(entityDelete)
            EntityManager.managedObjectContext.save(nil)
        }
    }
    override func getAll() -> [NSManagedObject]? {
        var error: NSError?
        var request = NSFetchRequest(entityName: Location.TABLE_NAME)
        
        if let objects = LocationManager.managedObjectContext.executeFetchRequest(request, error: &error) as? [Location] {
            return objects
        }
        
        return nil
    }
    
}