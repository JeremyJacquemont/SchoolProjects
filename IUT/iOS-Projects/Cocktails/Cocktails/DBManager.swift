//
//  DBManager.swift
//  Cocktails
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation

class DBManager {
    
    // MARK: - Variables
    let filename = "DrinkDirections"
    let fileExtension = "plist"
    var path : String!
    var content: [[String : String]]?
    var INSTANCE: DBManager?
    
    // MARK: - Singleton
    class var sharedInstance: DBManager {
        struct Static {
            static var instance: DBManager?
            static var token: dispatch_once_t = 0
        }
        
        dispatch_once(&Static.token) {
            Static.instance = DBManager()
            Static.instance?.initializer()
        }
        
        return Static.instance!
    }
    
    // MARK: - Functions
    private func initializer(){
        if let basePath = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true) {
            if basePath.count > 0 {
                if let filePath = basePath[0] as? String {
                    path = filePath.stringByAppendingPathComponent(filename + "." + fileExtension)
                    let fileManager = NSFileManager.defaultManager()
                    if !fileManager.fileExistsAtPath(path) {
                        let sourcePathOptional = NSBundle.mainBundle().pathForResource(filename, ofType: fileExtension)
                        if let sourcePath = sourcePathOptional {
                            fileManager.copyItemAtPath(sourcePath, toPath: path, error: nil)
                        }
                    }
                    content = NSArray(contentsOfFile: path) as? [[String : String]]
                }
            }
        }
    }
    internal func getAllCocktails() -> [Cocktail]{
        var cocktails = [Cocktail]()
        
        if let contentArray = content {
            for dict in contentArray {
                let name = dict[Cocktail.KEY_NAME]! as String
                let ingredients = dict[Cocktail.KEY_INGREDIENTS]! as String
                let directions = dict[Cocktail.KEY_DIRECTIONS]! as String
                cocktails.append(Cocktail(name: name, ingredients: ingredients, directions: directions))
            }
        }
        
        return cocktails
    }
    internal func addCocktail(cocktail: Cocktail){
        var arrayCocktail = [String : String]()
        
        arrayCocktail[Cocktail.KEY_NAME] = cocktail.name
        arrayCocktail[Cocktail.KEY_INGREDIENTS] = cocktail.ingredients
        arrayCocktail[Cocktail.KEY_DIRECTIONS] = cocktail.directions
        content?.append(arrayCocktail)
        content?.sort{c1,c2 in c1[Cocktail.KEY_NAME]?.lowercaseString < c2[Cocktail.KEY_NAME]?.lowercaseString}
        
        writeFile()
    }
    internal func getCocktail(name: String) -> Cocktail?{
        var indexCocktail = -1
        if let contentArray = content {
            for (index, element) in enumerate(contentArray) {
                if element[Cocktail.KEY_NAME] == name {
                    indexCocktail = index
                }
            }
            
            if indexCocktail != -1 {
                return Cocktail(name: contentArray[indexCocktail][Cocktail.KEY_NAME]!, ingredients: contentArray[indexCocktail][Cocktail.KEY_INGREDIENTS]!, directions: contentArray[indexCocktail][Cocktail.KEY_DIRECTIONS]!)
            }
        }
        
        return nil;
    }
    internal func editCocktail(cocktail: Cocktail){
        var indexCocktail = -1
        if let contentArray = content {
            for (index, element) in enumerate(contentArray) {
                if element[Cocktail.KEY_NAME] == cocktail.name {
                    indexCocktail = index
                }
            }
            
            if indexCocktail != -1 {
                content?[indexCocktail].updateValue(cocktail.ingredients, forKey: Cocktail.KEY_INGREDIENTS)
                content?[indexCocktail].updateValue(cocktail.directions, forKey: Cocktail.KEY_DIRECTIONS)
                writeFile()
            }
        }
    }
    internal func deleteCocktail(index: Int){
        content?.removeAtIndex(index)
        writeFile()
    }
    private func writeFile(){
        let array : NSArray = content!
        array.writeToFile(path, atomically: true)
    }    
}