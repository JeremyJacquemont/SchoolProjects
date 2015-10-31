//
//  CategoryManager.swift
//  TP-BDD-1
//
//  Created by JeremyJacquemont on 08/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import CoreData
import Foundation

class CategoryManager: DataManager {
    
    //MARK: - Singleton
    override class var sharedManager: CategoryManager {
        struct Singleton {
            static let instance = CategoryManager()
        }
        
        return Singleton.instance
    }
    
    //MARK: - Functions
    func createCategoryForName(name : String?) -> Category? {
        if let nameValue = name {
            let entity = NSEntityDescription.entityForName(Category.TABLE_NAME, inManagedObjectContext: DataManager.managedObjectContext)
            let category = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: DataManager.managedObjectContext) as Category
            
            category.name = nameValue
            
            var error: NSError? = nil
            DataManager.managedObjectContext.save(&error)
            
            if error != nil {
                println("Could not save context : \(error), \(error?.description)")
            }
            
            return category
            
        }
        
        return nil
    }
    
    func fetchCategory(predicate: NSPredicate) -> Category? {
        if let categories = fetchCategories(predicate, sortDescriptors: nil) {
            return categories[0]
        }
        
        return nil
    }
    
    func fetchCategories(predicate: NSPredicate, sortDescriptors: [NSSortDescriptor]?) -> [Category]? {
        let fetchRequest = NSFetchRequest(entityName: Category.TABLE_NAME)
        fetchRequest.sortDescriptors = sortDescriptors
        fetchRequest.predicate = predicate
        
        var error: NSError? = nil
        let results = DataManager.managedObjectContext.executeFetchRequest(fetchRequest, error: &error) as [Category]
        if results.count > 0 {
            return results
        }
        
        return nil
    }
    
    func fetchCategories() -> [Category]? {
        let fetchRequest = NSFetchRequest(entityName: Category.TABLE_NAME)
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: Category.FIELD_NAME, ascending: true)]
        
        var error: NSError? = nil
        if let results = DataManager.managedObjectContext.executeFetchRequest(fetchRequest, error: &error) as? [Category] {
            return results
        }
        
        if error != nil {
            println("Could not fetch data : \(error), \(error?.description)")
        }
        
        return nil
    }
    
    func deleteCategory(category: Category?) -> Bool? {
        if let categoryToDelete = category {
            DataManager.managedObjectContext.deleteObject(categoryToDelete)
            DataManager.managedObjectContext.save(nil)
            return true
        }
        
        return nil
    }
    
}