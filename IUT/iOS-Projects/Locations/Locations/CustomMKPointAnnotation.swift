//
//  CustomMKPointAnnotation.swift
//  Locations
//
//  Created by iem on 13/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIkit
import MapKit

//MARK: - CustomMKPointAnnotationType
enum CustomMKPointAnnotationType{
    case VISITED, NORMAL
}

//MARK: - CustomMKPointAnnotation
class CustomMKPointAnnotation: MKPointAnnotation {
    //MARK: - Variables
    var visited: Bool = false
    
    //Functions
    func getAnnotationColor() -> MKPinAnnotationColor {
        return self.visited ? MKPinAnnotationColor.Green : MKPinAnnotationColor.Red
    }
    
}
