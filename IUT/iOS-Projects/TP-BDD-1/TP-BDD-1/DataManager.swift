//
//  DataManager.swift
//  TP-BDD-1
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation
import CoreData

class DataManager{

    //MARK: - Constants
    class var NAME_BDD: String { return "ToDo" }
    class var EXTENSION_BDD_MOMD: String { return "momd" }
    class var EXTENSION_BDD_SQLITE: String { return "sqlite" }
    
    //MARK: - Variables
    class var managedObjectContext: NSManagedObjectContext {
        struct Singleton {
            static let persistentStoreCoordinator: NSPersistentStoreCoordinator? = {
                var coordinator: NSPersistentStoreCoordinator? = NSPersistentStoreCoordinator(managedObjectModel: managedObjectModel)
                let storeURL = applicationDocumentsDirectory.URLByAppendingPathComponent("\(DataManager.NAME_BDD).\(DataManager.EXTENSION_BDD_SQLITE)")
                let options = [
                    NSMigratePersistentStoresAutomaticallyOption: true,
                    NSInferMappingModelAutomaticallyOption: true
                ]
                
                var error: NSError? = nil
                if coordinator!.addPersistentStoreWithType(NSSQLiteStoreType,
                    configuration: nil,
                    URL: storeURL,
                    options: options,
                    error: &error) == nil {
                        println("Error adding persistance store: \(error)")
                        abort()
                }
                
                return coordinator
            }()
            
            static var applicationDocumentsDirectory: NSURL = {
                let fileManager = NSFileManager.defaultManager()
                let urls = fileManager.URLsForDirectory(NSSearchPathDirectory.DocumentDirectory, inDomains: NSSearchPathDomainMask.UserDomainMask)
                return urls[0] as NSURL
            }()
            
            static var managedObjectModel: NSManagedObjectModel = {
                let modelUrl = NSBundle.mainBundle().URLForResource(DataManager.NAME_BDD, withExtension: DataManager.EXTENSION_BDD_MOMD)!
                return NSManagedObjectModel(contentsOfURL: modelUrl)!
            }()
            
            static let instance: NSManagedObjectContext? = {
                let coordinator = persistentStoreCoordinator
                if coordinator == nil {
                    return nil
                }
                var managedObjectContext = NSManagedObjectContext()
                managedObjectContext.persistentStoreCoordinator = coordinator
                return managedObjectContext
            }()
        }
        
        return Singleton.instance!
    }
    
    //MARK: - Singleton
    class var sharedManager: DataManager {
        struct Singleton {
            static let instance = DataManager()
        }
        
        return Singleton.instance
    }
    
    //MARK: - Functions
    class func saveContext() {
        var error : NSError? = nil
        
        if DataManager.managedObjectContext.hasChanges && !DataManager.managedObjectContext.save(&error) {
            println("Failed to save context: \(error), \(error?.userInfo)")
        }
    }
}