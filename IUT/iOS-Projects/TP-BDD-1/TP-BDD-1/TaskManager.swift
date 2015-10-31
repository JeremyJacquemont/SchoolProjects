//
//  TaskManager.swift
//  TP-BDD-1
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import CoreData
import Foundation

class TaskManager: DataManager {
    
    //MARK: - Singleton
    override class var sharedManager: TaskManager {
        struct Singleton {
            static let instance = TaskManager()
        }
        
        return Singleton.instance
    }
    
    
    //MARK: - Functions
    func createTaskForName(name : String?) -> Task? {
        if let nameValue = name {
            let entity = NSEntityDescription.entityForName(Task.TABLE_NAME, inManagedObjectContext: DataManager.managedObjectContext)
            let task = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: DataManager.managedObjectContext) as Task
                
            task.name = nameValue
                
            var error: NSError? = nil
            DataManager.managedObjectContext.save(&error)
                
            if error != nil {
                println("Could not save context : \(error), \(error?.description)")
            }
                
            return task
        }
        
        return nil
    }
    
    func fetchTask(predicate: NSPredicate?) -> Task? {
        if let tasks = fetchTasks(predicate, sortDescriptors: nil) {
            return tasks[0]
        }
        
        return nil
    }
    
    func fetchTasks() -> [Task]? {
        if let tasks = fetchTasks(nil, sortDescriptors: nil) {
            return tasks
        }
        return nil
    }
    
    func fetchTasks(predicate: NSPredicate?, sortDescriptors: [NSSortDescriptor]?) -> [Task]? {
        let fetchRequest = NSFetchRequest(entityName: Task.TABLE_NAME)
        fetchRequest.sortDescriptors = sortDescriptors
        fetchRequest.predicate = predicate
        
        var error: NSError? = nil
        let results = DataManager.managedObjectContext.executeFetchRequest(fetchRequest, error: &error) as [Task]
        if results.count > 0 {
            return results
        }
        
        return nil
    }
    
    func searchTasksWithCategory(nameCategory: String?) -> [Task]? {
        if nameCategory != nil {
            let predicate = NSPredicate(format: "\(Task.FIELD_CATEGORY).\(Category.FIELD_NAME) CONTAINS[cd] %@", nameCategory!)!
            if let tasks = fetchTasks(predicate, sortDescriptors: nil) {
                return tasks
            }
            else {
                return []
            }
        }
        
        return nil
    }
    
    func searchTasksWithName(name: String?) -> [Task]? {
        if name != nil {
            let predicate = NSPredicate(format: "\(Task.FIELD_NAME) CONTAINS[cd] %@", name!)!
            if let tasks = fetchTasks(predicate, sortDescriptors: nil) {
                return tasks
            }
            else {
                return []
            }
        }
        
        return nil
    }
    
    func searchTaskWithName(name: String?) -> Task? {
        if name != nil {
            let predicate = NSPredicate(format: "\(Task.FIELD_NAME) = %@", name!)!
            return fetchTask(predicate)
        }
        
        return nil
    }
    
    func deleteTask(task: Task?) -> Bool? {
        if let taskToDelete = task {
            DataManager.managedObjectContext.deleteObject(taskToDelete)
            DataManager.managedObjectContext.save(nil)
            return true
        }
        
        return nil
    }
}