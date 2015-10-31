//
//  Cocktail.swift
//  Cocktails
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import Foundation

func == (lhs : Cocktail , rhs : Cocktail) -> Bool {
    return lhs.name == rhs.name && lhs.ingredients == rhs.ingredients && lhs.directions == rhs.directions
}

struct Cocktail : Equatable {
    static let KEY_NAME = "name"
    static let KEY_INGREDIENTS = "ingredients"
    static let KEY_DIRECTIONS = "directions"
    
    let name, ingredients, directions : String
}